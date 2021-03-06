package com.server.bean;

import com.server.vo.ControllerMethod;
import com.servlet.RequestWay;
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

    private static Map<String, ControllerMethod> controllerMethods = new HashMap<String, ControllerMethod>();

    static {
        for (Class<?> clas : ClassesFactory.getClassSet()) {
            addBean(clas);
        }
    }

    public static Object getBean(String beanClassName) {
        return beans.get(beanClassName);
    }

    public static void addBean(Class<?> clas) {
        try {
            Object object = clas.newInstance();
            beans.put(object.getClass().getName(), object);
            for (Object bean : beans.values()) {
                modifyBeanByInject(bean);
            }
            if (clas.isAnnotationPresent(Controller.class)) {
                addControllerMethod(clas, object);
            }
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("BeanFactory addBean", clas.getName(), e));
        }
    }

    public static void modifyBeanByInject(Object object) {
        try {
            for (Field field : object.getClass().getFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object bean = getBean(field.getType().getName());
                    field.set(object, bean);
                }
            }
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("BeanFactory buildBeanByInject", object.getClass().getName(), e));
        }
    }

    public static void addControllerMethod(Class<?> clas, Object object) {
        boolean errorControllerActionSet = false;
        String controllerUrl = clas.getAnnotation(Controller.class).value();
        for (Method method : clas.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Action.class)) {
                String methodUrl = method.getAnnotation(Action.class).value();
                ControllerMethod controllerMethod = new ControllerMethod();
                String methodWay = method.getAnnotation(Action.class).method().trim().toLowerCase();
                if (!methodWay.equals(RequestWay.GET) && !methodWay.equals(RequestWay.POST) && !methodWay.equals(RequestWay.UPDATE) && !methodWay.equals(RequestWay.DELETE)) {
                    errorControllerActionSet = true;
                    break;
                }
                controllerMethod.setMethodWay(methodWay);
                controllerMethod.setMethod(method);
                controllerMethod.setObject(object);
                controllerMethods.put(controllerUrl + methodUrl, controllerMethod);
            }
        }
        if (errorControllerActionSet) {
            try {
                throw new Exception("UnSupport Request Method In Class: " + clas.getName());
            } catch (Exception e) {
                logger.error(LogDefine.getErrorLog("addControllerMethod", clas.getName(), e));
            }
        }
    }

    public static Map<String, ControllerMethod> getControllerMethods() {
        return controllerMethods;
    }

    public static ControllerMethod getControllerActionMethod(String requestPath, String requestWay) {
        ControllerMethod controllerMethod = controllerMethods.get(requestPath);
        if (controllerMethod != null) {
            if (controllerMethod.getMethodWay().equals(requestWay.trim().toLowerCase())) {
                return controllerMethod;
            }
        }
        return null;
    }
}
