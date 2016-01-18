package server.bean;

import org.apache.log4j.Logger;
import server.Annotation.Controller;
import server.Annotation.Inject;
import userDefine.LogDefine;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijunbo on 2016/1/18.
 */
public class BeanFactory {

    Logger logger = Logger.getLogger(BeanFactory.class);

    private static Map<String, Object> beans = new HashMap<String, Object>();

    public Object getBean(String beanName) {
        return beans.get(beanName);
    }

    public void addBean(String beanName, Object object) {
        beans.put(beanName, object);
    }

    public Object getBeanByClass(Class<?> clas) {
        for (Object object : beans.values()) {
            if (object.getClass() == clas) {
                return object;
            }
        }
        return null;
    }

    public void buildBeanByInject(Class<?> clas, String beanName) {
        try {
            Object object = clas.newInstance();
            for (Field field : clas.getFields()) {
                if (field.isAnnotationPresent(Inject.class)) {
                    Object bean = getBeanByClass(field.getType());
                    if (bean != null) {
                        field.set(object, bean);
                    }
                }
            }
            beans.put(beanName, object);
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("buildBeanByInject", clas.getName(), e));
        }
    }
}
