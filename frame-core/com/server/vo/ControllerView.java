package com.server.vo;

/**
 * Created by lijunbo on 2016/1/20.
 */
public class ControllerView {

    private Object view;

    private String toUrl;

    public Object getView() {
        return view;
    }

    public void setView(Object view) {
        this.view = view;
    }

    public String getToUrl() {
        return toUrl;
    }

    public void setToUrl(String toUrl) {
        this.toUrl = toUrl;
    }
}
