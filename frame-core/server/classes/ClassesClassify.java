package server.classes;

import org.apache.log4j.Logger;
import server.Annotation.Action;
import server.Annotation.Controller;
import userDefine.LogDefine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public class ClassesClassify {

    private static Logger logger = org.apache.log4j.Logger.getLogger(ClassesClassify.class);

    private static Set<Class<?>> classSet;

    private static boolean isClassSetRefresh = false;

    private static Set<Class<?>> ActionClassSet;

    private static boolean isActionRefresh = false;

    private static Map<String, Class<?>> controllerMap;

    private static boolean isControllerRefresh = false;

    static {
        classSet = ClassLoaderUtil.getPackageClassSet("");
        ActionClassSet = defaultGetAnnotationClasses(Action.class);
        setControllerAnnotationClasses();
    }

    public Set<Class<?>> getClassSet() {
        if (!isClassSetRefresh) {
            return classSet;
        }
        classSet = ClassLoaderUtil.getPackageClassSet("");
        isClassSetRefresh = false;
        isActionRefresh = true;
        isControllerRefresh = true;
        return classSet;
    }

    public static Set<Class<?>> defaultGetAnnotationClasses(Class annotationClass) {
        Set<Class<?>> annotationClassSet = new HashSet<Class<?>>();
        for (Class<?> clas : classSet) {
            if (clas.isAnnotationPresent(annotationClass)) {
                annotationClassSet.add(clas);
            }
        }
        return annotationClassSet;
    }

    public Set<Class<?>> getActionAnnotationClasses() {
        if (!isActionRefresh) {
            return ActionClassSet;
        }
        ActionClassSet = defaultGetAnnotationClasses(Action.class);
        isActionRefresh = false;
        return ActionClassSet;
    }

    public static void setControllerAnnotationClasses() {
        controllerMap.clear();
        for (Class<?> clas : classSet) {
            if (clas.isAnnotationPresent(Controller.class)) {
                Annotation annotation = clas.getAnnotation(Controller.class);
                Method[] annotationMethods = annotation.annotationType().getDeclaredMethods();
                String mapKey = "";
                for (Method method : annotationMethods) {
                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }
                    try {
                        if (("value").equals(method.getName())) {
                            mapKey = method.invoke(annotation).toString();
                        } else {
                            method.invoke(annotation);
                        }
                    } catch (Exception e) {
                        logger.error(LogDefine.getErrorLog("setControllerAnnotationClasses", "", e));
                    }
                }
                controllerMap.put(mapKey, clas);
            }
        }
    }

    public Map<String, Class<?>> getControllerAnnotationClasses() {
        if (!isControllerRefresh) {
            return controllerMap;
        }
        setControllerAnnotationClasses();
        isControllerRefresh = false;
        return controllerMap;
    }
}
