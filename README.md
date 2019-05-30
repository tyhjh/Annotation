# 集成Android常用注解
### Butterknife
[Butterknife](https://github.com/JakeWharton/butterknife)，简单用过但是不是很喜欢
```java
 @BindView(R.id.etUserName) 
 EditText etUserName;
 
 @OnClick(R.id.submit) 
 void submit() {
    // TODO call server...
  }
```

### AndroidAnnotations
最开始接触注解框架是[androidannotations](https://github.com/androidannotations/androidannotations/wiki/AvailableAnnotations)，有很多非常好用的注解：初始化控件、设置点击事件、线程切换等等，功能很强大；比较喜欢**androidannotations**的线程切换，而且点击事件和绑定控件都不需要输入控件的`id`值，只需要变量名或者方法名就可以完成，感觉很简洁，而且写代码的时候很方便，直接从布局文件复制`id`；
```java

@BindView 
EditText etUserName;

@OnClick
 void submit() {
    // TODO call server...
  }
  
@Background
void doSomething(){
    // TODO call server...
}

@UiThread
void doSomething(){
    // TODO call server...
}
```
但是有个缺点，就是太强大了导致框架比较重

### 自定义注解
在了解了注解后我开始自己实现注解，模仿`androidannotations`的使用方法；使用编译时注解，在编译时生成我们需要的代码，具体实现可以看之前的文章，其实还是很简单的
>* 使用注解实现Android线程切换：https://www.jianshu.com/p/89d7b88eb76c
>* Android编译时注解：https://www.jianshu.com/p/3052fa51ee95
>* Android中注解的使用：https://www.jianshu.com/p/de13b00042d6

#### 初始化
需要使用注解的类中需要初始化
```java
ViewInjector.injectView(this);
```


#### 控件初始化@ViewById

因为控件ID在非`app`模块就不是常量了，不能设置为注解的值，这也是Butterknife在其他模块需要使用`R2.id.xxx`的原因，所以涉及到控件ID值的都提供两种方法

* 变量名和控件ID值一样，不需要设置ID值

```java
//变量名和控件ID值一样
@ViewById
TextView tvLogin,tvName;
```

* 被注解的变量名可以和控件ID值不一样，但是需要手动设置控件ID值，这种方法只能在`app`模块使用

```java
//手动设置id
@ViewById(R.id.et_pwd)
EditText etPwd;
```

#### 点击事件@Click

```java
@Click
void tvLogin() {
    // TODO call server...
}

@Click
void tvLogin(R.id.tvLogin) {
    // TODO call server...
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

#### 线程切换@Background
`delay`值默认为0，可以不设置，集成了`rxjava`实现线程切换，但是不提供接口
```java
@Background(delay = 1000)
void backgroud() {
    Log.e("backgroud", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
}
```
#### 线程切换@UiThread
```java
@UiThread(delay = 1000 * 5)
void toast(String msg) {
    Log.e("toast", Thread.currentThread().getName() + "：" + System.currentTimeMillis());
}
```

#### 加载更多@RecyclerMore
其实是RecyclerView滑动到底部监听，可以实现下拉加载更多的功能，使用很方便，变量名就是方法名，也可以手动设置控件ID值；可以设置`pageSize`的值，如果当前加载的item数量小于`pageSize`那么就不会触发方法，默认滑动到底部监听就会触发方法
```java
@RecyclerMore(pageSize = 5)
void ryclView() {
    mList.addAll(mList2);
    mAdapter.notifyDataSetChanged();
}
```

### 集成方法

支持aspectj和jitpack，在工程的grade添加
```
...
 dependencies {
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
    }
...


//Add it in your root build.gradle at the end of repositories:
allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```




在使用的module的grade中添加依赖和注解处理器，以及支持aspectj
```
...
apply plugin: 'android-aspectjx'
...

implementation 'com.github.tyhjh.Annotation:annotationlibrary:v1.0.7'
annotationProcessor 'com.github.tyhjh.Annotation:annotator:v1.0.7'
```

需要支持lambda 表达式，在模块的build.gradle的android节点下面添加支持
```
compileOptions {
        sourceCompatibility = '1.8'
        targetCompatibility = '1.8'
    }
```



注解都在实际项目中用了一段时间，还不错；遇到需要的注解就会不断的添加进去


### 项目地址

> https://github.com/tyhjh/Annotation
















