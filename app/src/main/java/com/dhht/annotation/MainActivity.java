package com.dhht.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dhht.annotation.annotation.ViewByIdLocal;
import com.dhht.annotation.util.ResourceUtil;
import com.dhht.annotationlibrary.ViewInjector;
import com.dhht.annotationlibrary.view.AvoidShake;

import java.lang.reflect.Field;

/**
 * @author dhht
 */
public class MainActivity extends Activity {

    public final static int color1 = R.color.colorAccent;
    public final static int color2 = R.color.colorPrimary;
    public final static int color3 = R.color.colorPrimaryDark;


    @ViewById(value = R.id.txtView)
    TextView txtView, txtView2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewInjector.injectView(this);

        AvoidShake.setClickIntervalTime(2000);
        //initAnnotation();

        toast("xxxx");

    }


    @CheckBoxChange
    void swTest(boolean isChecked, CompoundButton swTest) {
        Toast.makeText(this, "isChecked：" + swTest.isChecked(), Toast.LENGTH_SHORT).show();
    }

    @ViewById
    SwipeRefreshLayout refreshView;

    @RefreshView(colors = {color1, color2, color3})
    void refreshView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    stopRefresh();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @UiThread
    void stopRefresh() {
        refreshView.setRefreshing(false);
    }


    int x = 0;

    @Click(value = R.id.txtView)
    void txtView() {
        //Log.e("txtView", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, "txtView1：" + x, Toast.LENGTH_SHORT).show();
    }


    @Click(interval = 1000)
    void txtView2() {
        Log.e("txtView2", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, "txtView2：" + x, Toast.LENGTH_SHORT).show();
    }

    void etTest() {
        Toast.makeText(MainActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
    }


    @UiThread(delay = 1000 * 5)
    void toast(String msg) {
        Log.e("toast", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Background(delay = 3000)
    void backgroud(View view) {
        Log.e("backgroud", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        //toast("主线程");
        view.setClickable(true);
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
