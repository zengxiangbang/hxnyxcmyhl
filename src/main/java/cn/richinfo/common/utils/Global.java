package cn.richinfo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 项目全局变量
 */
public class Global {

    private static final Logger logger = LoggerFactory.getLogger(Global.class);
    
    public static String projectCode;
    public static Date starttime;
    public static Date endtime;
    public static String cmccappid;
    public static String accountID;
    public static String baseInfoUrl;
    public static String websiteDomain;

    public static String redisip;
    public static Integer redisport;
    public static String redispassword;
    public static int redisdb;

    public final static String UTF8 = "UTF-8";

    static {
        try {
            projectCode = ConfigUtils.getByKey("ConfigUtils.projectcode");
        	starttime = DateUtils.strToDate(ConfigUtils.getByKey("ConfigUtils.starttime"), "yyyy-MM-dd HH:mm:ss");
            endtime = DateUtils.strToDate(ConfigUtils.getByKey("ConfigUtils.endtime"), "yyyy-MM-dd HH:mm:ss");
            cmccappid = ConfigUtils.getByKey("cmccappid");
            accountID = ConfigUtils.getByKey("accountID");
            baseInfoUrl = ConfigUtils.getByKey("baseInfoUrl");
            websiteDomain = ConfigUtils.getByKey("website.domain");

            //redis配置
            redisip = ConfigUtils.getByKey("ConfigUtils.redis.redisip");
            redisport = Integer.valueOf(ConfigUtils.getByKey("ConfigUtils.redis.redisport"));
            redispassword = ConfigUtils.getByKey("ConfigUtils.redis.redispassword");
            redisdb = Integer.valueOf(ConfigUtils.getByKey("ConfigUtils.redis.redisdb"));
        } catch (Exception e) {
            logger.error("ConfigUtils获取错误: {} ",e.getMessage());
        }
    }
}
