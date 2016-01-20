package com.util;

import net.sf.json.JSONArray;

/**
 * Created by lijunbo on 2016/1/20.
 */
public class JsonUtil {
    public static String toJson(Object object) {
        JSONArray jsonArray = JSONArray.fromObject(object);
        return jsonArray.toString();
    }
}
