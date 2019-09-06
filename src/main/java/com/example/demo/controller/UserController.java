package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.CommResult;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public CommResult insertUser(User user) {
    	try {
    		CommResult result = userService.saveUser(user);
			return result;
		} catch (Exception e) {
			return CommResult.build(500, "");
		}

    }
    
    /**
     * 接收表单，包含用户、密码。调用Service进行登录返回CommResult。
     * @param user
     * @return CommResult 返回的工具数据类
     */
    @RequestMapping(value="/login")
    @ResponseBody
    public Object userLogin(User user,HttpServletRequest request,HttpServletResponse response){
        try {
        	CommResult result = userService.getUser(user, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return CommResult.build(500, "");
        }
	}
    
    /**
     * 做一个接口，调用的时候，从redis数据库中检查是否存在相同的token,token是否过期来证明是否已经登陆了。
     * 接收token调用Service返回用户信息，使用CommResult。
     * @param user
     * @param request
     * @param response
     * @return
     */
    @CrossOrigin//可以作用在类上，也可以作用在方法上，代替jsonp跨域请求
    @RequestMapping(value="/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable("token") String token){
        CommResult result = null;
		try {
			result = userService.queryUserByToken(token);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = CommResult.build(500, "");
		}
		return result;
	}
    
    
    /**
     * 注销，从Redis中删除key
     * @param token
     * @return
     */
    @RequestMapping(value="/logout/{token}")
    @CrossOrigin
    @ResponseBody
    public String logout(@PathVariable("token") String token,HttpServletRequest request,HttpServletResponse response) {
        Boolean b = userService.logout(request,response,token);
        System.out.println(b);
        return 	"1";
    }
    
   
    
}
