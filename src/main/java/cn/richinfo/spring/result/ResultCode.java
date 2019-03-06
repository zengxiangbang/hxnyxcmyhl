package cn.richinfo.spring.result;


/**
 * @author Grant
 */
public enum  ResultCode {
    /**
     * 错误
     */
    ERROR(0,"ERROR"),
    /**
     * 成功
     */
    SUCCESS(1,"SUCCESS"),

    /**
     * 需要登录
     */
    NEED_LOGIN(10,"NEED_LOGIN"),

    /**
     * 非法参数
     */
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),

    /**
     * 活动下线
     */
    ACTIVITY_OFFLINE(302,"ACTIVITY_OFFLINE"),

    /**
     * 非活动用户
     */
    INACTIVE_USER(20,"INACTIVE_USER");




    private final int code;

    private final String desc;

    ResultCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }
}
