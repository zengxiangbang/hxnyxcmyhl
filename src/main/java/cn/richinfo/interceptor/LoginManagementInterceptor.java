package cn.richinfo.interceptor;

import cn.richinfo.common.annotation.VerifyLogin;
import cn.richinfo.common.enumeration.RequestMode;
import cn.richinfo.common.enumeration.Role;
import cn.richinfo.common.utils.Global;
import cn.richinfo.common.utils.ResponseJson;
import cn.richinfo.spring.domain.ActiveUser;
import cn.richinfo.spring.result.Result;
import cn.richinfo.spring.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 登录控制,下线控制
 * @author Grant
 */

public class LoginManagementInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginManagementInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            VerifyLogin verifyLogin = ((HandlerMethod) handler).getMethodAnnotation(VerifyLogin.class);

            if(verifyLogin != null){
                //下线控制
                if (verifyLogin.validateEndTime()) {
                    if (Global.endtime.getTime() < System.currentTimeMillis()) {
                        logger.info("当前访问为结束时间（"+ Global.endtime+"）小于当前时间（"+new Date()+"）");
                        if (verifyLogin.requestModule().getValue() == RequestMode.ajax.getValue()) {
                            ResponseJson.write(response, new Result(ResultCode.ACTIVITY_OFFLINE.getCode()));
                            return false;
                        } else {
                            response.sendRedirect("http://gddqres.richworks.cn/dqtg/bye/index.html");
                            return false;
                        }
                    }
                }

                //登录控制
                HttpSession session = request.getSession();
                ActiveUser user = (ActiveUser) session.getAttribute("USER");
                int role = verifyLogin.role().getValue();
                if(role == Role.mobileuser.getValue() || role == Role.weixinuser.getValue()){
                    if(user == null){
                        ResponseJson.write(response, new Result(ResultCode.NEED_LOGIN.getCode()));
                        logger.info("用户未登录, 返回首页");
                        return false;
                    }
                }
            }



        }
        return true;
    }
}