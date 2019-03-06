package cn.richinfo.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.text.ParseException;

/**
 * @Author:yhq
 * @Date: Create in 2018/8/4 18:46
 * @Description 配置类
 */
@Component
@PropertySource(value = {"classpath:config-dev.properties", "classpath:properties/redis.properties"})
public class ConfigUtil {

    /**
     * 登陆用户SESSION
     */
    public static String APP_NAME;
    public static String LOGIN_SESSION_NAME;
    public static String APP_ACCESS_TOKEN;
    public static String APP_TICKET;
    public static String CALLBACK;
    public static String REDIS_IP;
    public static Integer REDIS_PORT;
    public static String REDIS_PASSWORD;
    public static Integer REDIS_DB;
    public static String signatureUrl;
    public static String ticketUrl;
    public static String accountID;
    public static String cmccappid;
    public static long START_TIME;
    public static long END_TIME;
    public static String END_TIME_URL;
    public static String START_TIME_URL;
    public static String TIANJIN_PROVINCE_CODE;
    public static String baseInfoUrl;

    @Value("${provinceCode}")
    public void setTianjinProvinceCode(String provinceCode) {
        TIANJIN_PROVINCE_CODE = provinceCode;
    }

    @Value("${baseInfoUrl}")
    public void setBaseInfoUrl(String baseInfoUrl) {
        ConfigUtil.baseInfoUrl = baseInfoUrl;
    }

    @Value("${startTime}")
    public void setStartTime(String startTime) throws ParseException {
        System.out.println("开始时间：" + startTime);
        START_TIME = DateUtil.getStrToDateFormat(startTime, null).getTime();
    }

    @Value("${endTime}")
    public void setEndTime(String endTime) throws ParseException {
        System.out.println("结束时间：" + endTime);
        END_TIME = DateUtil.getStrToDateFormat(endTime, null).getTime();
    }

    @Value("${endTimeUrl}")
    public void setEndTimeUrl(String endTimeUrl) {
        END_TIME_URL = endTimeUrl;
    }
    @Value("${startTimeUrl}")
    public  void setStartTimeUrl(String startTimeUrl) {
        START_TIME_URL = startTimeUrl;
    }

    @Value("${cmccappid}")
    public void setCmccappid(String cmccappid) {
        ConfigUtil.cmccappid = cmccappid;
    }

    @Value("${accountID}")
    public void setAccountID(String accountID) {
        ConfigUtil.accountID = accountID;
    }

    @Value("${signatureUrl}")
    public void setSignatureUrl(String signatureUrl) {
        ConfigUtil.signatureUrl = signatureUrl;
    }

    @Value("${ticketUrl}")
    public void setTicketUrl(String ticketUrl) {
        ConfigUtil.ticketUrl = ticketUrl;
    }

    @Value("${redis_ip}")
    public void setRedisIp(String redisIp) {
        REDIS_IP = redisIp;
    }

    @Value("${redis_port}")
    public void setRedisPort(Integer redisPort) {
        REDIS_PORT = redisPort;
    }

    @Value("${redis_default_db}")
    public void setRedisDb(Integer redisDb) {
        REDIS_DB = redisDb;
    }

    @Value("${redis_password}")
    public void setRedisPassword(String redisPassword) {
        REDIS_PASSWORD = redisPassword;
    }

    @Value("${app_name}")
    public void setAppName(String appName) {
        APP_NAME = appName;
    }

    @Value("${app_name}")
    public void setLoginSessionName(String appName) {
        LOGIN_SESSION_NAME = appName + "_login_user";
    }

    @Value("${app_name}")
    public void setAppAccessToken(String appName) {
        APP_ACCESS_TOKEN = appName + "_access_token";
    }


    @Value("${app_name}")
    public void setAppTicket(String appName) {
        APP_TICKET = appName + "_ticket";
    }

    @Value("${call_back}")
    public void setCALLBACK(String CALLBACK) {
        ConfigUtil.CALLBACK = CALLBACK;
    }


}
