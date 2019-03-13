package com.dhht.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.dhht.annotation.annotation.TestAnnotation;
import com.dhht.annotation.annotation.ViewById;
import com.dhht.annotation.util.ResourceUtil;

import java.lang.reflect.Field;

/**
 * @author dhht
 */
@TestAnnotation
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView txtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAnnotation();
        txtView.setOnClickListener(v -> Toast.makeText(MainActivity.this, "醉了", Toast.LENGTH_SHORT).show());
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
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                try {
                    //向对象的这个Field属性设置新值value
                    if (viewById.value() == -1) {
                        field.set(MainActivity.this, findViewById(ResourceUtil.getId(MainActivity.this, field.getName())));
                    } else {
                        field.set(this, findViewById(viewById.value()));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
