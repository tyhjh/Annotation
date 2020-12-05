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
import com.dhht.annotationlibrary.CatAnnotation;
import com.dhht.annotationlibrary.bean.MethodInfo;
import com.dhht.annotationlibrary.interfaces.IExecuteListener;
import com.dhht.annotationlibrary.interfaces.IExecuteTimePrinter;
import com.dhht.annotationlibrary.utils.ExecuteManager;
import com.dhht.annotationlibrary.view.AvoidShake;

import java.lang.reflect.Field;

/**
 * @author dhht
 */
public class MainActivity extends Activity {

    private static final String TAG="MainActivity";

    public final static int color1 = R.color.colorAccent;
    public final static int color2 = R.color.colorPrimary;
    public final static int color3 = R.color.colorPrimaryDark;


    @ViewById(value = R.id.txtView)
    TextView txtView, txtView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CatAnnotation.injectView(this);

        CatAnnotation.setClickIntervalTime(5000);



        ExecuteManager.getInstance().setPrinter((executeTime, annotion, methodInfo) -> {
            Log.i(TAG,"方法耗时为： "+executeTime);
            Log.i(TAG,"方法的详情："+methodInfo.toString());
        });


        ExecuteManager.getInstance().addExecuteListener(new IExecuteListener() {
            @Override
            public void before(CustomAnnotation annotation, MethodInfo methodInfo) {
                Log.i(TAG,"before MethodInfo is "+methodInfo.toString());
            }

            @Override
            public void after(CustomAnnotation annotation, MethodInfo methodInfo) {
                Log.i(TAG,"after MethodInfo is "+methodInfo.toString());
            }
        });


        //initAnnotation();
        toast("xxxx");
       // toast(name);
    }


    @Background(delay = 3000)
    private String getName() {
        return "111";
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
        x++;
        Log.e("txtView", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, "txtView1：" + x, Toast.LENGTH_SHORT).show();
    }


    @Click(interval = 1000)
    void txtView2() {
        x++;
        Log.e("txtView2", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
        Toast.makeText(this, "txtView2：" + x, Toast.LENGTH_SHORT).show();
    }

    @CustomAnnotation
    private void etTest() {
        Toast.makeText(MainActivity.this, "哈哈哈", Toast.LENGTH_SHORT).show();
    }


    @ExecuteTime
    @CustomAnnotation
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
