package com.server.bean;

import org.apache.log4j.Logger;
import com.server.Annotation.Action;
import com.server.Annotation.Controller;
import com.server.Annotation.Inject;
import com.server.classes.ClassesFactory;
import com.userDefine.LogDefine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunbo on 2016/1/18.
 */
public class BeanFactory {

    private static Logger logger = Logger.getLogger(BeanFactory.class);

    private static Map<String, Object> beans = new HashMap<String, Object>();

    private static Map<String, Map<String, Method>> controllerMethods = new HashMap<String, Map<String, Method>>();

    static {
        try {
            for (Class<?> clas : ClassesFactory.getClassSet()) {
                beans.put(clas.getName(), clas.newInstance());
            }
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("BeanFactory", "", e));
        }
    }

    public Object getBean(String beanClassName) {
        return beans.get(beanClassName);
    }

    public static void addBean(Class<?> clas) {
        try {
            Object object = clas.newInstance();
            beans.put(object.getClass().getName(), object);
            if (object.getClass().isAnnotationPresent(Controller.class)) {
                addControllerMethods(object);
            }
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("BeanFactory addBean", clas.getName(), e));
        }
    }

    public static void addControllerMethods(Object object) {
        Map<String, Method> actionMethods = new HashMap<String, Method>();
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Action.class)) {
                actionMethods.put(method.getAnnotation(Action.class).value(), method);
            }
        }
        controllerMethods.put(object.getClass().getAnnotation(Controller.class).value(), actionMethods);
    }

    public static Map<String, Map<String, Method>> getControllerMethods() {
        return controllerMethods;
    }

    public void buildBeanByInject(Class<?> clas) {
        try {
            Object object = clas.newInstance();
            for (Field field : clas.getFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object bean = getBean(field.getType().getName());
                    field.set(object, bean);
                }
            }
            beans.put(clas.getName(), object);
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("BeanFactory buildBeanByInject", clas.getName(), e));
        }
    }
}
