package com.dhht.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dhht.annotation.annotation.ViewByIdLocal;
import com.dhht.annotation.util.ResourceUtil;
import com.dhht.annotationlibrary.ViewInjector;

import java.lang.reflect.Field;

/**
 * @author dhht
 */
public class MainActivity extends Activity {

    @ViewById(value = R.id.txtView)
    TextView txtView, txtView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initAnnotation();
        ViewInjector.injectView(this);
    }


    @Click(R.id.txtView)
    void txtView() {
        Log.e("txtView", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        backgroud();
    }

    @Click
    void txtView2() {
        Log.e("txtView2", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        toast("UiThread");
    }

    void etTest() {
        Toast.makeText(MainActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
    }


    @UiThread(delay = 1000 * 5)
    void toast(String msg) {
        Log.e("toast", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Background(delay = 1000)
    void backgroud() {
        Log.e("backgroud", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        //toast("主线程");
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
