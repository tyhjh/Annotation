package com.dhht.annotator;

import com.dhht.annotation.CheckBoxChange;
import com.dhht.annotation.Click;
import com.dhht.annotation.RecyclerMore;
import com.dhht.annotation.RefreshView;
import com.dhht.annotation.SwitchChange;
import com.dhht.annotation.ViewById;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @author dhht
 */
public class ProxyInfo {

    /**
     * 所在包名
     */
    private String packageName;

    /**
     * 生成的类名
     */
    private String proxyClassName;
    /**
     * 外部类
     */
    private TypeElement typeElement;

    /**
     * 保存类里面的所有注解
     */
    public List<Element> mElementList = new ArrayList<>();


    public static final String PROXY = "ViewInject";

    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        String className = ClassValidator.getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }

    /**
     * 生成代码
     *
     * @return
     */
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import android.view.View;\n");
        builder.append("import com.dhht.annotation.*;\n");
        builder.append("import android.widget.Switch;\n");
        builder.append("import android.widget.CheckBox;\n");

        builder.append("import android.support.v4.widget.SwipeRefreshLayout;\n");
        builder.append("import android.support.v7.widget.RecyclerView;\n");
        builder.append("import com.dhht.annotationlibrary.view.RecyclerViewScrollListener;\n");
        builder.append("import com.dhht.annotationlibrary.view.AvoidShake;\n");

        builder.append("import com.dhht.annotationlibrary.view.AvoidShakeClickHelper;\n");
        builder.append("import com.dhht.annotationlibrary.view.AvoidShakeListener;\n");


        builder.append("import ").append(getLibrayPath(packageName)).append(".R;\n");
        builder.append("import com.dhht.annotationlibrary.*;\n");
        builder.append('\n');
        builder.append("public class ").append(proxyClassName).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");

        //生成方法
        generateMethods(builder);


        builder.append('\n');
        builder.append("}\n");
        return builder.toString();
    }


    /**
     * 生成根据注解去生成代码
     *
     * @param builder
     */
    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("initViewById(host,source);\n ");
        builder.append("initClick(host,source);\n");
        builder.append("initRcylMore(host,source);\n");
        builder.append("initRefreshView(host,source);\n");
        builder.append("initSwitchView(host,source);\n");
        builder.append("initCheckBox(host,source);\n");
        builder.append("  }\n");


        //生成 initViewById 方法
        builder.append("public void initViewById(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        Iterator<Element> iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            ViewById annotation = element.getAnnotation(ViewById.class);
            if (annotation != null) {
                VariableElement variableElement = (VariableElement) element;
                generateViewById(variableElement, builder);
                iterator.remove();
            }
        }
        builder.append("}");


        //生成 initClick 方法
        builder.append("public void initClick(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("View view;\n");
        iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            Click annotation = element.getAnnotation(Click.class);
            if (annotation != null) {
                ExecutableElement executableElement = (ExecutableElement) element;
                generateClick(executableElement, builder);
                iterator.remove();
            }
        }
        builder.append("}");


        //生成 initRcylMore 方法
        builder.append("public void initRcylMore(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("RecyclerView view;\n");
        iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            RecyclerMore annotation = element.getAnnotation(RecyclerMore.class);
            if (annotation != null) {
                ExecutableElement executableElement = (ExecutableElement) element;
                generateRecycleMore(executableElement, builder);
                iterator.remove();
            }
        }
        builder.append("}");


        //生成RefreshView方法
        builder.append("public void initRefreshView(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("SwipeRefreshLayout view;\n");
        iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            RefreshView annotation = element.getAnnotation(RefreshView.class);
            if (annotation != null) {
                ExecutableElement executableElement = (ExecutableElement) element;
                generateRefreshView(executableElement, builder);
                iterator.remove();
            }
        }
        builder.append("}");


        //生成Switch方法
        builder.append("public void initSwitchView(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("Switch view;\n");
        iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            SwitchChange annotation = element.getAnnotation(SwitchChange.class);
            if (annotation != null) {
                ExecutableElement executableElement = (ExecutableElement) element;
                generateSwitchView(executableElement, builder, "Switch");
                iterator.remove();
            }
        }
        builder.append("}");


        //生成CheckBox方法
        builder.append("public void initCheckBox(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        builder.append("CheckBox view;\n");
        iterator = mElementList.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            CheckBoxChange annotation = element.getAnnotation(CheckBoxChange.class);
            if (annotation != null) {
                ExecutableElement executableElement = (ExecutableElement) element;
                generateCheckBox(executableElement, builder, "CheckBox");
                iterator.remove();
            }
        }
        builder.append("}");


    }

    private void generateCheckBox(ExecutableElement executableElement, StringBuilder builder, String viewType) {

        //获取注解值
        int id = executableElement.getAnnotation(CheckBoxChange.class).value();
        initView(executableElement, builder, viewType, id);

        String mothed = executableElement.getSimpleName().toString();
        List<VariableElement> variableElements = (List<VariableElement>) executableElement.getParameters();
        int size = variableElements.size();
        if (size == 1) {
            String type1 = variableElements.get(0).asType().toString();
            if (type1.equals("boolean") || type1.equals("Boolean")) {
                mothed = "host." + mothed + "(isChecked)";
            }
        } else if (size == 0) {
            mothed = "host." + mothed + "()";
        } else if (size == 2) {
            String type1 = variableElements.get(0).asType().toString();
            String type2 = variableElements.get(1).asType().toString();
            if ((type1.equals("boolean") || type1.equals("Boolean")) && type2.equals("android.widget.CompoundButton")) {
                mothed = "host." + mothed + "(isChecked,buttonView)";
            } else if ((type2.equals("boolean") || type2.equals("Boolean")) && type1.equals("android.widget.CompoundButton")) {
                mothed = "host." + mothed + "(buttonView,isChecked)";
            }
        }
        builder.append("view.setOnCheckedChangeListener(((buttonView, isChecked) ->" + mothed + "));");

    }


    /**
     * 生成ViewById方法
     */
    private void generateViewById(VariableElement variableElement, StringBuilder builder) {
        //获取注解值
        int id = variableElement.getAnnotation(ViewById.class).value();
        //获取变量类型
        String type = variableElement.asType().toString();
        //获取变量名字
        String name = variableElement.getSimpleName().toString();

        builder.append(" if(source instanceof android.app.Activity){\n");
        builder.append("host." + name).append(" = ");
        if (id == -1) {
            builder.append("(" + type + ")(((android.app.Activity)source).findViewById( " + "R.id." + name + "));\n");
        } else {
            builder.append("(" + type + ")(((android.app.Activity)source).findViewById( " + id + "));\n");
        }
        builder.append("\n}else{\n");

        builder.append("host." + name).append(" = ");
        if (id == -1) {
            builder.append("(" + type + ")(((android.view.View)source).findViewById( " + "R.id." + name + "));\n");
        } else {
            builder.append("(" + type + ")(((android.view.View)source).findViewById( " + id + "));\n");
        }
        builder.append("}\n");
    }

    /**
     * 生成Click方法
     *
     * @param builder
     */
    private void generateClick(ExecutableElement executableElement, StringBuilder builder) {

        //获取注解值
        int id = executableElement.getAnnotation(Click.class).value();

        int intervalTime = executableElement.getAnnotation(Click.class).interval();
        //获取变量名字
        String mothed = executableElement.getSimpleName().toString();
        builder.append(" if(source instanceof android.app.Activity){\n");

        if (id == -1) {
            builder.append("view=((View)(((android.app.Activity)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((View)(((android.app.Activity)source).findViewById( " + id + ")));\n");
        }
        builder.append("\n}else{\n");
        if (id == -1) {
            builder.append("view=((View)(((android.view.View)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((View)(((android.view.View)source).findViewById( " + id + ")));\n");
        }
        builder.append("  }\n");
        builder.append(" view.setOnClickListener(new AvoidShakeClickHelper(" + intervalTime + ",v->host." + mothed + "()));");
    }


    /**
     * 生成Switch方法
     *
     * @param executableElement
     * @param builder
     */
    private void generateSwitchView(ExecutableElement executableElement, StringBuilder builder, String viewType) {
        //获取注解值
        int id = executableElement.getAnnotation(SwitchChange.class).value();
        initView(executableElement, builder, viewType, id);

        String mothed = executableElement.getSimpleName().toString();
        List<VariableElement> variableElements = (List<VariableElement>) executableElement.getParameters();
        int size = variableElements.size();
        if (size == 1) {
            String type1 = variableElements.get(0).asType().toString();
            if (type1.equals("boolean") || type1.equals("Boolean")) {
                mothed = "host." + mothed + "(isChecked)";
            }
        } else if (size == 0) {
            mothed = "host." + mothed + "()";
        } else if (size == 2) {
            String type1 = variableElements.get(0).asType().toString();
            String type2 = variableElements.get(1).asType().toString();
            if ((type1.equals("boolean") || type1.equals("Boolean")) && type2.equals("android.widget.CompoundButton")) {
                mothed = "host." + mothed + "(isChecked,buttonView)";
            } else if ((type2.equals("boolean") || type2.equals("Boolean")) && type1.equals("android.widget.CompoundButton")) {
                mothed = "host." + mothed + "(buttonView,isChecked)";
            }
        }
        builder.append("view.setOnCheckedChangeListener(((buttonView, isChecked) ->" + mothed + "));");

    }


    /**
     * 初始化这个View
     *
     * @param executableElement
     * @param builder
     * @param viewType
     */
    private void initView(ExecutableElement executableElement, StringBuilder builder, String viewType, int id) {
        //获取变量名字
        String mothed = executableElement.getSimpleName().toString();
        builder.append(" if(source instanceof android.app.Activity){\n");
        if (id == -1) {
            builder.append("view=((" + viewType + ")(((android.app.Activity)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((" + viewType + ")(((android.app.Activity)source).findViewById( " + id + ")));\n");
        }
        builder.append("\n}else{\n");
        if (id == -1) {
            builder.append("view=((" + viewType + ")(((android.view.View)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((" + viewType + ")(((android.view.View)source).findViewById( " + id + ")));\n");
        }
        builder.append("  }\n");

    }


    /**
     * 生成RecycleMore方法
     *
     * @param builder
     */
    private void generateRecycleMore(ExecutableElement executableElement, StringBuilder builder) {

        //获取注解值
        int id = executableElement.getAnnotation(RecyclerMore.class).value();
        int pageSize = executableElement.getAnnotation(RecyclerMore.class).pageSize();

        //获取变量名字
        String mothed = executableElement.getSimpleName().toString();

        builder.append(" if(source instanceof android.app.Activity){\n");

        if (id == -1) {
            builder.append("view=((RecyclerView)(((android.app.Activity)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((RecyclerView)(((android.app.Activity)source).findViewById( " + id + ")));\n");
        }

        builder.append("\n}else{\n");
        if (id == -1) {
            builder.append("view=((RecyclerView)(((android.view.View)source).findViewById( " + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view=((RecyclerView)(((android.view.View)source).findViewById( " + id + ")));\n");
        }
        builder.append("  }\n");

        builder.append("view.addOnScrollListener(new RecyclerViewScrollListener() {\n" +
                "\n" +
                "            @Override\n" +
                "            public void onScrollToBottom() {\n" +

                "if(" + pageSize + "==-1){\n" +
                "host." + mothed + "();\n" +
                "                    }else if(itermsCount>=" + pageSize + "){\n" +
                "host." + mothed + "();\n" +
                "                    }" +
                "            }\n" +
                "        });");
    }


    /**
     * 生成RefreshView方法
     *
     * @param executableElement
     * @param builder
     */
    private void generateRefreshView(ExecutableElement executableElement, StringBuilder builder) {

        //获取注解值
        int id = executableElement.getAnnotation(RefreshView.class).value();
        int[] colors = executableElement.getAnnotation(RefreshView.class).colors();

        //获取变量名字
        String mothed = executableElement.getSimpleName().toString();
        builder.append("if (source instanceof android.app.Activity) {\n");
        if (id == -1) {
            builder.append("view = ((SwipeRefreshLayout) (((android.app.Activity) source).findViewById(" + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view = ((SwipeRefreshLayout) (((android.app.Activity) source).findViewById(" + id + ")));\n");
        }
        builder.append("}else{\n");
        if (id == -1) {
            builder.append("view = ((SwipeRefreshLayout) (((android.view.View) source).findViewById(" + "R.id." + mothed + ")));\n");
        } else {
            builder.append("view = ((SwipeRefreshLayout) (((android.view.View) source).findViewById(" + id + ")));\n");
        }
        builder.append("}\n");
        if (colors.length > 0) {
            builder.append("view.setColorSchemeResources(");
            for (int i = 0; i < colors.length; i++) {
                if (i != colors.length - 1) {
                    builder.append(colors[i] + ",");
                } else {
                    builder.append(colors[i]);
                }
            }
            builder.append(");\n");
        }
        builder.append("view.setOnRefreshListener(() -> host." + mothed + "());\n");

    }


    /**
     * 获取全名
     *
     * @return
     */
    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    /**
     * 获取TypeElement
     *
     * @return
     */
    public TypeElement getTypeElement() {
        return typeElement;
    }


    /**
     * 获取包名
     *
     * @param packageName
     * @return
     */
    private String getLibrayPath(String packageName) {
        try {
            return packageName.substring(0, ordinalIndexOf(packageName, ".", 3));
        } catch (Exception e) {
            return packageName;
        }
    }


    private int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1) {
            pos = str.indexOf(substr, pos + 1);
        }
        return pos;
    }

}