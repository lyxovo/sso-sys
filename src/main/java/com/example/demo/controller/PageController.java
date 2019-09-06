package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommResult;


/**
 * 作为跳转中介
 * @author b
 *
 */
@Controller
@RequestMapping("/page")
public class PageController {
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping("/register")
    public String toRegister() {
        return "register";
    }
	
	
    /**登录页面回调URL作用。
     * 仅仅是负责跳转到登录页面
     * @param redirect  浏览器中的请求路径
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public String toLogin(String redirect, Model model) {
    	//把请求路径放到request域
        model.addAttribute("redirect", redirect);
        return "login";
    }
    
    
}
