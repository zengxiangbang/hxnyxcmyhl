package cn.richinfo.spring.web;


import cn.richinfo.spring.domain.ActiveUser;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Grant
 */
public class BaseController {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    protected ActiveUser getUser(){
        HttpSession session = request.getSession();
        return (ActiveUser) session.getAttribute("USER");
    }
}
