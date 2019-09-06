package com.example.demo.service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.CommResult;
import com.example.demo.utils.CookieUtils;
import com.example.demo.utils.JsonUtils;

@Service
public class UserService {
	//用户session在redis保存的key
	private static final String USER_SESSION_KEY="REDIS_USER_SESSION";
	//redis中session过期时间 30分钟：30*60*60。单位:秒
	private static final long USER_SESSION_EXPIRE=1800;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserMapper userMapper;
	
	
    
    /**
     * 注册
     * @param user
     * @return
     */
	public CommResult saveUser(User user) {
		//md5加密
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return CommResult.ok();
	}


	/**
	 * 把用户信息存到redis(去密码），并生成一把"钥匙"token,把这把钥匙放到cookie中带给login浏览器
	 * @param loginUser
	 * @param request
	 * @param response
	 * @return
	 */
	public CommResult getUser(User loginUser,HttpServletRequest request,HttpServletResponse response){
    	List<User> list = userMapper.queryAll(loginUser);
    	
    	if (null == list || list.size() == 0) {
			return CommResult.build(400, "用户名或密码错误");
		}
    	
    	User user = list.get(0);//现数据库中的用户信息
//		比对密码
		if (!DigestUtils.md5DigestAsHex(loginUser.getPassword().getBytes()).equals(user.getPassword())) {
			return CommResult.build(400, "用户名或密码错误");
		}
//		生成token
		String token = UUID.randomUUID().toString();
//		保存用户到redis之前，把用户对象中的密码清空,防止别人看到。
		user.setPassword(null);
//		把用户信息写入redis,形式key-value .key：名称:+盐，保证不重复。value:存清除密码的json格式的用户信息。并设置过期时间。
		redisTemplate.opsForValue().set(USER_SESSION_KEY + ":" + token,JsonUtils.objectToJson(user),USER_SESSION_EXPIRE,TimeUnit.SECONDS);
//		redisTemplate.expire(key, timeout, unit);//或者
		
		//把信息存进cookie中。
		CookieUtils.setCookie(request, response, "u_token", token);
		return CommResult.ok(token);

    }
    
    /**
     *根据 token 检查是否登录，如果是已经登录了，把redis中存的数据过期时间重新更新一下。
     */
	public CommResult queryUserByToken(String token) {
		//(1)根据token从redis中查询用户信息
		String json = redisTemplate.opsForValue().get(USER_SESSION_KEY + ":" + token);
		System.out.println(json);
		System.out.println(token);
		//(2)判断是否为空
		if (StringUtils.isBlank(json)) {
			return CommResult.build(400, "session已经过期，请重新登录");
		}
		
		//(3)更新过期时间
		redisTemplate.expire(USER_SESSION_KEY + ":" + token, USER_SESSION_EXPIRE, TimeUnit.SECONDS);//或者
		return CommResult.ok(JsonUtils.jsonToPojo(json, User.class));
	}
	
	/**
	 * 删除redis中的session
	 * @param token
	 */
	public Boolean logout(HttpServletRequest request,HttpServletResponse response,String token) {
		Boolean b = redisTemplate.delete(USER_SESSION_KEY + ":" + token);
		CookieUtils.deleteCookie(request, response, "u_token");
		return b;
    }
	
	
}
