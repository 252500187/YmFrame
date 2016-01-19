package com.server.loadConfig;

import com.util.StringUtil;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午3:57
 * To change this template use File | Settings | File Templates.
 */
public class GetConfig {

    private static Properties CONFIGS;

    static {
        //TODO,获取配置文件名称
        String CONFIG_FILE = "resource/frame.properties";
        CONFIGS = LoadConfig.loadConfig(CONFIG_FILE);
    }

    public static void setAppViewPath(String appViewPath) {
        CONFIGS.setProperty(ConfigConstant.APP_VIEW_PATH, appViewPath);
    }

    public static String getAppViewPath() {
        return StringUtil.isEmpty(CONFIGS.getProperty(ConfigConstant.APP_VIEW_PATH)) ? ConfigConstant.DEFAULT_APP_VIEW_PATH : CONFIGS.getProperty(ConfigConstant.APP_VIEW_PATH);
    }
}
