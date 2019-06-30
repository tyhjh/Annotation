package com.dhht.annotator;

import com.dhht.annotation.CheckBoxChange;
import com.dhht.annotation.Click;
import com.dhht.annotation.RecyclerMore;
import com.dhht.annotation.RefreshView;
import com.dhht.annotation.SwitchChange;
import com.dhht.annotation.ViewById;
import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author HanPei
 * @date 2019/3/13  下午1:58
 */
@AutoService(Processor.class)
public class IocProcessor extends AbstractProcessor {

    /**
     * 生成代码用的
     */
    private Filer mFileUtils;

    /**
     * 跟元素相关的辅助类，帮助我们去获取一些元素相关的信息
     * - VariableElement  一般代表成员变量
     * - ExecutableElement  一般代表类中的方法
     * - TypeElement  一般代表代表类
     * - PackageElement  一般代表Package
     */
    private Elements mElementUtils;

    /**
     * 跟日志相关的辅助类
     */
    private Messager mMessager;


    private Map<String, ProxyInfo> mProxyMap = new HashMap<String, ProxyInfo>();


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFileUtils = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    /**
     * 添加需要支持的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<String>();
        //添加需要支持的注解
        annotationTypes.add(ViewById.class.getCanonicalName());
        annotationTypes.add(Click.class.getCanonicalName());
        annotationTypes.add(RecyclerMore.class.getCanonicalName());
        annotationTypes.add(RefreshView.class.getCanonicalName());
        annotationTypes.add(SwitchChange.class.getCanonicalName());
        annotationTypes.add(CheckBoxChange.class.getCanonicalName());
        return annotationTypes;

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mProxyMap.clear();

        mClasses.add(ViewById.class);
        mClasses.add(Click.class);
        mClasses.add(RecyclerMore.class);
        mClasses.add(RefreshView.class);
        mClasses.add(CheckBoxChange.class);
        mClasses.add(SwitchChange.class);

        //保存注解
        if (!saveAnnotation(roundEnvironment)) {
            return false;
        }

        //生成类
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                //创建一个新的源文件，并返回一个对象以允许写入它
                JavaFileObject jfo = mFileUtils.createSourceFile(
                        proxyInfo.getProxyClassFullName(),
                        proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                error(proxyInfo.getTypeElement(),
                        "Unable to write injector for type %s: %s",
                        proxyInfo.getTypeElement(), e.getMessage());
            }
        }
        return true;
    }


    List<Class> mClasses = new ArrayList<>();


    public boolean saveAnnotation(RoundEnvironment roundEnvironment) {
        for (Class clazz : mClasses) {
            //获取被注解的元素
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(clazz);
            for (Element element : elements) {
                //检查element类型
                if (!checkAnnotationValid(element)) {
                    return false;
                }
                //获取到这个变量的外部类
                TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                //获取外部类的类名
                String qualifiedName = typeElement.getQualifiedName().toString();
                //以外部类为单位保存
                ProxyInfo proxyInfo = mProxyMap.get(qualifiedName);
                if (proxyInfo == null) {
                    proxyInfo = new ProxyInfo(mElementUtils, typeElement);
                    mProxyMap.put(qualifiedName, proxyInfo);
                }
                //把这个注解保存到proxyInfo里面，用于实现功能
                proxyInfo.mElementList.add(element);
            }
        }
        return true;
    }


    /**
     * 检测注解使用是否正确
     *
     * @param annotatedElement
     * @return
     */
    private boolean checkAnnotationValid(Element annotatedElement) {
        //检测是否是变量
       /* if (annotatedElement.getKind() != ElementKind.FIELD) {
            error(annotatedElement, "%s must be declared on field.", clazz.getSimpleName());
            return false;
        }*/
        //检测这个变量是不是公有的
        if (ClassValidator.isPrivate(annotatedElement)) {
            error(annotatedElement, "%s() must can not be private.", annotatedElement.getSimpleName());
            return false;
        }
        return true;
    }


    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message, element);
    }

}
