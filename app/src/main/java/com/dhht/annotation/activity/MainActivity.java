package com.dhht.annotation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dhht.annotation.Click;
import com.dhht.annotation.R;
import com.dhht.annotation.ViewById;
import com.dhht.annotation.annotation.ViewByIdLocal;
import com.dhht.annotation.util.ResourceUtil;
import com.dhht.annotationlibrary.ViewInjector;

import java.lang.reflect.Field;

/**
 * @author dhht
 */
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView txtView, txtView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initAnnotation();
        ViewInjector.injectView(this);
        txtView2.setOnClickListener(v -> Toast.makeText(MainActivity.this, "醉了2", Toast.LENGTH_SHORT).show());

    }


    @Click(R.id.txtView)
    void txtView() {
        Toast.makeText(MainActivity.this, "呵呵", Toast.LENGTH_SHORT).show();
    }

    @Click
    void etTest() {
        Toast.makeText(MainActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
    }

    /**
     * 开始获取注解进行操作
     */
    private void initAnnotation() {
        //获得成员变量
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            //允许修改反射属性
            field.setAccessible(true);
            ViewByIdLocal viewByIdLocal = field.getAnnotation(ViewByIdLocal.class);
            if (viewByIdLocal != null) {
                try {
                    //向对象的这个Field属性设置新值value
                    if (viewByIdLocal.value() == -1) {
                        field.set(MainActivity.this, findViewById(ResourceUtil.getId(MainActivity.this, field.getName())));
                    } else {
                        field.set(this, findViewById(viewByIdLocal.value()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
