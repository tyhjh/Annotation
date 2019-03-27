package com.dhht.annotator;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.PRIVATE;

final class ClassValidator {
    /**
     * 判断变量是不是PRIVATE
     *
     * @param annotatedClass
     * @return
     */
    static boolean isPrivate(Element annotatedClass) {
        return annotatedClass.getModifiers().contains(PRIVATE);
    }


    /**
     * 拼接类名
     *
     * @param type
     * @param packageName
     * @return
     */
    static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }
}
