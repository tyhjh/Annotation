# Android中注解的使用

标签（空格分隔）： java

---

注解是开发中经常使用到的，因为很久前在网上找了几篇文章，发现完全看不懂，所以觉得这个东西好像很难搞，最近耐心的看完了这篇文章[秒懂，Java 注解你可以这样学](https://blog.csdn.net/briblue/article/details/73824058)，感觉入门还是比较简单的

我为什么想要看注解呢，其实是因为看见有些注解十分方便，比如@ViewById注解可以让代码更优雅，想自己也搞几个注解用一下，比如自动运行在UI线程、子线程的注解；然后发现很多文章讲的太抽象了，而且到最后都根本没有我想要的这种例子，索性就不看了；其实大概就是因为太心急了，没有理解注解的作用；

上面的文章里面的例子很好，把注解比喻成一个标签，注解只是给你这个方法、这个类、这个变量加一个标签而已，然后你可以通过一些方法来获取这个标签，根据自己的需要做一些操作

### 新建标签
创建一个注解十分简单，和接口差不多
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationClass {
    int id() default 1;
    String name();
}
```
标签分一些类型，不同的类型给Java中不同的地方标记，类型使用`@Target`来设置，@Target 有下面的取值
>* ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
>* ElementType.CONSTRUCTOR 可以给构造方法进行注解
>* ElementType.FIELD 可以给属性进行注解
>* ElementType.LOCAL_VARIABLE 可以给局部变量进行注解
>* ElementType.METHOD 可以给方法进行注解
>* ElementType.PACKAGE 可以给一个包进行注解
>* ElementType.PARAMETER 可以给一个方法内的参数进行注解
>* ElementType.TYPE 可以给一个类型进行注解，比如类、接口、枚举

需要使用`@Retention`来控制标签的生命周期，其实就是你在什么时候去处理这个标签，处理完肯定不要了，不然占空间
>* RetentionPolicy.SOURCE 注解只在源码阶段保留，在编译器进行编译时它将被丢弃忽视。
>* RetentionPolicy.CLASS 注解只被保留到编译进行的时候，它并不会被加载到 JVM 中。
>* RetentionPolicy.RUNTIME 注解可以保留到程序运行的时候，它会被加载进入到 JVM 中，所以在程序运行时可以获取到它们。

然后里面可以设置一些属性，注解的属性也叫做成员变量。注解只有成员变量，没有方法。注解的成员变量在注解的定义中以“无形参的方法”形式来声明，其方法名定义了该成员变量的名字，其返回值定义了该成员变量的类型

### 运行时标签的使用
上面的标签定义为给类型注解，所以直接在类上面使用就好了，设置了可以在运行时使用，所以可以在程序运行的代码里面获取到标签，但是接下来，获取到标签后要干什么就取决于个人了
```java
@AnnotationClass(name = "Tyhj")
public class Main {

    public static void main(String[] args) {
        //这个类是否使用了AnnotationClass这个注解
        boolean hasAnnotation = Main.class.isAnnotationPresent(AnnotationClass.class);
        if (hasAnnotation) {
            //获取到AnnotationClass对象
            AnnotationClass annotationClass = Main.class.getAnnotation(AnnotationClass.class);
            String name = annotationClass.name();
            int id = annotationClass.id();
            System.out.println("name：" + name + "，id：" + id);
        }
    }

}
```
然后对于方法和成员变量的使用方法
AnnotationField
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationField {
    String defultValue() default "哈哈哈";
}
```
AnnotationMethod
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AnnotationMethod {

}
```
简单的获取出标签
```java
@AnnotationClass(name = "Tyhj")
public class Main {

    @AnnotationField(defultValue = "嗨，你好")
    private String msg;

    @AnnotationMethod
    private Long getTime() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        Main main = new Main();
        //判断这个类是否存在这个注解
        boolean hasAnnotation = Main.class.isAnnotationPresent(AnnotationClass.class);
        if (hasAnnotation) {
            //获取到注解实例
            AnnotationClass annotationClass = Main.class.getAnnotation(AnnotationClass.class);
            String name = annotationClass.name();
            int id = annotationClass.id();
            System.out.println("name：" + name + "，id：" + id);
        }

        //获取成员变量的注解
        try {
            Field field = Main.class.getDeclaredField("msg");
            //msg成员变量为private,故必须进行此操作
            field.setAccessible(true);
            AnnotationField check = field.getAnnotation(AnnotationField.class);
            //这里把注解的内容赋值给变量
            main.msg = check.defultValue();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //获取方法的注解
        try {
            Method method = Main.class.getDeclaredMethod("getTime");
            method.setAccessible(true);
            AnnotationMethod annotationMethod = method.getAnnotation(AnnotationMethod.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
```

都只是简单的展示获取标签，其中获取了获取成员变量的标签的值赋值给了运行的对象，通过这个就对`@ViewById(R.id.textView)`的实现有点想法了吧

### 实现运行时标签@ViewById
新建一个标签，当标签只有一个属性并且为value的时候，使用的时候可以不用写名字，用value来保存id，在运行的时候获取出来初始化控件，应该是ok的
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    int value() default -1;
}
```
具体的实现，需要找出所有的标签，一个个看一下是不是ViewById标签，是的话对之进行操作
```java
    @ViewById(R.id.txtView)
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
            //获取到标签
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                try {
                    //向对象的这个Field属性设置新值value
                    field.set(this, findViewById(viewById.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
```
甚至我们可以不写ID值使用，但是需要这个变量的名字和ID名字一样，这样我们可以通过这个变量的名字去生成ID，再进行绑定
```java
    @ViewById
    TextView txtView;
    //向对象的这个Field属性设置新值value
    if (viewById.value() == -1) {
        field.set(this,findViewById(getId(MainActivity.this, field.getName())));
    } else {
        field.set(this, findViewById(viewById.value()));
    }
    
    /**
     * 通过名字获取资源文件的id
     *
     * @param context
     * @param resName
     * @return
     */
    public static int getId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

```
这样效果就和**Butter Knife**效果差不多一样了呀，而且可以不写ID，但是这个是运行时注解，一切都是运行时自己来写的，封装一下的确也可以实现这个功能，但是运行时注解效率稍微会低一点，而且看一下Butter Knife的源码，它是`CLASS`，是编译时注解，在编译的时候生成的代码
```java
@Retention(CLASS) @Target(FIELD)
public @interface BindView {
  /** View ID to which the field will be bound. */
  @IdRes int value();
}
```

> 项目源码：https://github.com/tyhjh/Annotation.git







