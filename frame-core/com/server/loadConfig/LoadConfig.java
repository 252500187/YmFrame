package com.server.loadConfig;

import org.apache.log4j.Logger;
import com.userDefine.LogDefine;
import com.util.StringUtil;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
public class LoadConfig {
    private static Logger logger = Logger.getLogger(LoadConfig.class);

    public static Properties loadConfig(String configPath) {
        if (StringUtil.isEmpty(configPath)) {
            configPath = ConfigConstant.DEFAULT_CONFIG_FILE;
        }
        Properties configProperties = new Properties();
        try {
            logger.info(LogDefine.getInfoLog("load configFile", configPath));
            configProperties.load(new FileInputStream(configPath));
        } catch (Exception e) {
            logger.error(LogDefine.getErrorLog("LoadConfig loadConfig", configPath, e));
        }
        return configProperties;
    }
}
