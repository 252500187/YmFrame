package server.classes;

import server.Annotation.Action;
import server.Annotation.Controller;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午8:14
 * To change this template use File | Settings | File Templates.
 */
public class ClassesClassify {

    private static Set<Class<?>> classSet;

    static {
        String basePackage = "";
        classSet = ClassLoaderUtil.getPackageClassSet(basePackage);
    }

    public Set<Class<?>> getAnnotationClasses(Class annotationClass) {
        Set<Class<?>> annotationClassSet = new HashSet<Class<?>>();
        for (Class<?> clas : classSet) {
            if (clas.isAnnotationPresent(annotationClass)) {
                annotationClassSet.add(clas);
            }
        }
        return annotationClassSet;
    }

    public Set<Class<?>> getActionAnnotationClasses() {
        return getAnnotationClasses(Action.class);
    }

    public Set<Class<?>> getControllerAnnotationClasses() {
        return getAnnotationClasses(Controller.class);
    }
}
