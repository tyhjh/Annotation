# Android常用注解集成

### 使用方法
Step 1. Add the JitPack repository to your build file
```
dependencies {
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
        ...
    }
...

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
apply plugin: 'android-aspectjx'
...

aspectjx {
//排除所有package路径中包含`android.support`的class文件及库（jar文件）
    exclude 'android.support'
}

dependencies {
    implementation "com.github.tyhjh.Annotation:annotationlibrary:v1.0.4"
    annotationProcessor  "com.github.tyhjh.Annotation:annotator:v1.0.4"
	}
```

### 具体使用

#### @ViewById
初始化控件，在`onCreate`的`setContentView`方法后，初始化ViewInjector，然后，被注解的控件只需要变量名和ID一样就可以，在APP模块里面也可以手动添加value值，其他模块不行
```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewInjector.injectView(this);
        ...
    }

    @ViewById(value = R.id.txtView)
    TextView txtView;

    @ViewById
    TextView txtView2;
```
#### @Click
设置点击事件，也是一样，方法名和ID一样就可以，在APP模块也可以手动配置ID
```java
    @Click
    void txtView2() {
        ...
    }
    
    
    @Click(R.id.txtView)
    void txtView() {
        ...
    }
```


##### 点击防抖动
新增防抖动功能，使用了`@Click`注解的按钮默认在200ms内只能点击一次，可以通过设置全局修改，也可以修改单个点击事件的间隔
```java
//全局修改点击间隔，需要尽早设置
AvoidShake.setClickIntervalTime(1000);

//单个设置点击间隔
@Click(interval = 100)
void txtView() {
    ...
}
```


#### @UiThread
方法在主线程执行，可以添加一个延迟时间，不需要初始化ViewInjector
```java
    @UiThread(delay = 1000)
    void toast(String msg) {
        ...
    }
```

#### @Background
方法在子线程执行
```java
    @Background(delay = 1000)
    void backgroud() {
        ...
    }
```









