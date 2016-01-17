package server.classLoader;

import org.apache.log4j.Logger;
import userDefine.LogDefine;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderUtil {

    private static Logger logger = org.apache.log4j.Logger.getLogger(ClassLoaderUtil.class);

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className) {
        Class<?> loadClass;
        try {
            loadClass = Class.forName(className);
        } catch (Exception e) {
            loadClass = null;
            logger.error(LogDefine.getErrorLog("loadClassError", e));
        }
        return loadClass;
    }
}
