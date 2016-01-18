package userDefine;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class LogDefine {
    public static String getErrorLog(String errorLocation, String errorContent, Exception errorException) {
        return errorLocation + ": " + errorContent + " Exception: " + errorException;
    }

    public static String getInfoLog(String logLocation, String logContent) {
        return logLocation + ": " + logContent;
    }
}
