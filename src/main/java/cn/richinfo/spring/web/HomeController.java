package cn.richinfo.spring.web;


import cn.richinfo.common.annotation.VerifyLogin;
import cn.richinfo.common.enumeration.Role;
import cn.richinfo.common.utils.BehaviorLog;
import cn.richinfo.common.utils.IP;
import cn.richinfo.spring.domain.ActiveUser;
import cn.richinfo.spring.exception.InactiveUserException;
import cn.richinfo.spring.result.Result;
import cn.richinfo.spring.result.ResultCode;
import cn.richinfo.spring.service.ActiveUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Grant
 */
@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ActiveUserService activeUserService;


    /**
     * 数据统计
     * @param type
     * @param request
     */
    @RequestMapping(value = "/count.do", method = RequestMethod.GET)
    @VerifyLogin(role = Role.mobileuser)
    public void count(String type, HttpServletRequest request) {
        ActiveUser user = getUser();
        if(StringUtils.isNotBlank(type)){
            if("0".equals(type)) {
                logger.info("count.do | mobile: " + user.getAccount() + " | 首页访问");
                //首页访问
                BehaviorLog.WriteLog("hxnyxcmyhl", user.getAccount(), "首页访问", "1", "001", IP.getIpAddr(request), "1");
            }else if("1".equals(type)){
                //分享
                logger.info("count.do | mobile: " + user.getAccount() + " | 用户分享");
                BehaviorLog.WriteLog("hxnyxcmyhl", user.getAccount(), "用户分享", "2", "002", IP.getIpAddr(request), "1");
            }else if("2".equals((type))){
                logger.info("count.do | mobile: " + user.getAccount() + " | 三佳购物优惠券领取");
                BehaviorLog.WriteLog("hxnyxcmyhl", user.getAccount(), "三佳购物优惠券领取", "3", "003", IP.getIpAddr(request), "1");
            }
        }

    }




    /**
     * 登录认证
     * @param mobile 加密的手机号
     * @param type   加密方式
     * @param request request
     * @return Result
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    @VerifyLogin
    public Result login(String mobile, Integer type, HttpServletRequest request){
        if(StringUtils.isBlank(mobile)){
            logger.info("login.do | mobile为空，用户未登录");
            return new Result(ResultCode.ILLEGAL_ARGUMENT.getCode());
        }
        if(type == null){
            logger.info("login.do | type为空");
            return new Result(ResultCode.ILLEGAL_ARGUMENT.getCode());
        }
        try {
            ActiveUser activeUser = activeUserService.login(mobile, type);
            HttpSession session = request.getSession();
            session.setAttribute("USER", activeUser);
            String loginType = "AES";
            if(type == 2){
                loginType = "RAS";
            }
            logger.info("login.do | mobile : " + activeUser.getAccount() + " | 登录成功 | type :" + loginType);
            return Result.createBySuccess(activeUser);
        } catch (InactiveUserException inactiveUserException) {
            return new Result(ResultCode.INACTIVE_USER.getCode());
        }catch (Exception e) {
            logger.error("login.do | 登录异常", e);
            return Result.createByError();
        }
    }
}
