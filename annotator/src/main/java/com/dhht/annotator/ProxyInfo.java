package com.dhht.annotator;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * @author dhht
 */
public class ProxyInfo {
    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;

    public Map<Integer, VariableElement> injectVariables = new HashMap<>();

    public static final String PROXY = "ViewInject";

    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        //classname
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
        builder.append("import com.dhht.annotation.*;\n");
        builder.append("import com.dhht.annotation.R;\n");
        builder.append("import com.dhht.annotationlibrary.*;\n");
        builder.append('\n');

        builder.append("public class ").append(proxyClassName).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");

        generateMethods(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();
    }


    /**
     * 生成方法
     *
     * @param builder
     */
    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object source ) {\n");
        for (int id : injectVariables.keySet()) {
            VariableElement element = injectVariables.get(id);
            String name = element.getSimpleName().toString();
            String type = element.asType().toString();
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

            builder.append("\n};");
        }
        builder.append("  }\n");


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


}