package cn.richinfo.common.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

public class Log {

    static Logger logger = null;

    static final RollingFileAppender appender = (RollingFileAppender) Logger
            .getRootLogger().getAppender("File");

    static {

        if (logger == null) {

            logger = Logger.getLogger("");

        }

    }

    public static void WriteLog(LogLevel exLevel, String msg) {
        WriteLog(null, exLevel, msg, null);
    }

    public static void WriteLog(Exception ex, LogLevel exLevel) {
        WriteLog(ex, exLevel, null, null);
    }

    public static void WriteLog(Exception ex, LogLevel exLevel, String url) {
        WriteLog(ex, exLevel, null, url);
    }

    public static void WriteLog(LogLevel exLevel, String msg, String url) {
        WriteLog(null, exLevel, msg, url);
    }

    public static void WriteLog(Exception ex, LogLevel exLevel, String msg,
                                String url) {


        switch (exLevel) {
            case FATAL:
                logger.fatal(msg + url, ex);
                break;
            case ERROR:
                logger.error(msg + url, ex);
                break;
            case WARN:
                logger.warn(msg + url, ex);
                break;
            case INFO:
                logger.info(msg + url, ex);
                break;
            case DEBUG:
                logger.debug(msg + url, ex);
                break;
            default:
                break;
        }

    }

    public enum LogLevel {

        FATAL(0),
        ERROR(3),
        WARN(4),
        INFO(6),
        DEBUG(7);


        private int value;

        LogLevel(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}

