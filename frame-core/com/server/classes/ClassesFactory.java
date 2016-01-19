package com.server.classes;

import com.server.Annotation.Controller;
import com.server.bean.BeanFactory;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public class ClassesFactory {

    private static Set<Class<?>> classSet;

    private static Set<Class<?>> controllerMap;

    static {
        classSet = ClassLoaderUtil.getPackageClassSet("");
        setControllerAnnotationClasses();
    }

    public static void setControllerAnnotationClasses() {
        for (Class<?> clas : classSet) {
            if (clas.isAnnotationPresent(Controller.class)) {
                controllerMap.add(clas);
            }
        }
    }

    public void addClassSet(Class<?> clas) {
        classSet.add(clas);
        if (clas.isAnnotationPresent(Controller.class)) {
            controllerMap.add(clas);
        }
        BeanFactory.addBean(clas);
    }

    public static Set<Class<?>> getClassSet() {
        return classSet;
    }

    public static Set<Class<?>> getControllerAnnotationClasses() {
        return controllerMap;
    }
}
