package cn.richinfo.common.annotation;

import cn.richinfo.common.enumeration.ContentType;
import cn.richinfo.common.enumeration.RequestMode;
import cn.richinfo.common.enumeration.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注一个类或某个方法需要登录才能访问(调用)
 *
 * @author liyuan
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface VerifyLogin {

    ContentType contentType() default ContentType.textjson;

    RequestMode requestModule() default RequestMode.ajax;

    /**
     * 登录才能访问该类或方法
     *
     * @return 1 微信用户
     * 2微信用户绑定手机号码
     */
    Role role() default Role.guest;


    /**
     * 为true 验证开始时间
     *
     * @return
     */
    boolean validateBeginTime() default true;

    /**
     * 为true 验证结束时间
     *
     * @return
     */
    boolean validateEndTime() default true;


}
