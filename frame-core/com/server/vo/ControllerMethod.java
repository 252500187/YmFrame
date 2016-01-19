package com.server.vo;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-19
 * Time: 下午9:09
 * To change this template use File | Settings | File Templates.
 */
public class ControllerMethod {

    private String methodWay;

    private Method method;

    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMethodWay() {
        return methodWay;
    }

    public void setMethodWay(String methodWay) {
        this.methodWay = methodWay;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
