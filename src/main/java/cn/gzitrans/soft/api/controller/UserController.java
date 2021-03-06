package cn.gzitrans.soft.api.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.gzitrans.soft.api.entity.UserEntity;
import cn.gzitrans.soft.api.service.PictureUploadService;
import cn.gzitrans.soft.api.service.UserService;
import cn.gzitrans.soft.api.utils.EmojiFilter;
import cn.gzitrans.soft.api.utils.HttpAccess;

@RestController
@RequestMapping("${basepath}/user")
public class UserController {
	
	public static Logger logger = LogManager.getLogger(UserController.class);
	
	@Value("${wx.appid}")
	private String appid;
	
	@Value("${wx.secret}")
	private String secret;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PictureUploadService pictureUploadService;
	
	/**
	 * 接收小程序传过来的登录code，返回对应的openid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/receiveCode", method = RequestMethod.GET)
	public String receiveCode(HttpServletRequest request, @RequestParam String loginCode){
		logger.info("请求成功,传过来的登录码为：" + loginCode);
		//请求微信服务器端去获取openid和sessionkey
		String url = "https://api.weixin.qq.com/sns/jscode2session";
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appid", appid);
		params.put("secret", secret);
		params.put("js_code", loginCode);
		params.put("grant_type", "authorization_code");
		
		String ret = HttpAccess.getNameValuePairRequest(url, params, "utf-8", "authorization_code");
		
		JSONObject retJson = JSONObject.parseObject(ret);
		String openid = retJson.getString("openid");
		return openid;
	}
	
	
	/**
	 * 小程序用户登录接口
	 * 第一步:取openid查用户表是否已经存在
	 * 第二步：若不存在则只保存该用户
	 * @param request
	 * @param openId
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(HttpServletRequest request, @RequestParam String userString, @RequestParam String openId){
		//将下程序传过来的json串转为对象
		UserEntity user = JSON.parseObject(userString, UserEntity.class);
		user.setOpenId(openId);
		user.setNickName(EmojiFilter.filterEmoji(user.getNickName()));
		String openid = user.getOpenId();
		UserEntity checkUser = userService.findUserByOpenId(openid);
		
		if(checkUser == null){
			//保存用户信息
			userService.save(user);
		}
		
	}
	
	
	
	/**
	 * 根据用户openid取得他上传的批次号列表
	 * @param openId
	 * @return
	 */
	@RequestMapping(value = "/getPlateList", method = RequestMethod.GET)
	public List<String> getPlateList(@RequestParam String openId){
		List<String> plateList = pictureUploadService.getPlateListByOpenId(openId);
		return plateList;
	}

}
