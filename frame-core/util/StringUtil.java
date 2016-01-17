package util;

/**
 * Created with IntelliJ IDEA.
 * User: Yam_hard
 * Date: 16-1-17
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    public static Boolean isEmpty(String dealString) {
        return (dealString == null || ("").equals(dealString.trim())) ? true : false;
    }
}
