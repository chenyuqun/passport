package com.zizaike.passport.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.passport.LogoutService;
import com.zizaike.passport.api.BaseAjaxController;


@Controller
@RequestMapping("/passport/password/logout")
public class LogoutController extends BaseAjaxController {
	
	@Autowired
	private LogoutService logoutService;
	/**
	 * 
	 * logout:注销服务. <br/>  
	 *  
	 * @author snow.zhang  
	 * @param ssid
	 * @return
	 * @throws ZZKServiceException  
	 * @since JDK 1.7
	 */
	@RequestMapping
	public @ResponseBody ResponseResult logout(String ssid) throws ZZKServiceException{
		this.logoutService.logout(ssid);
		return  new ResponseResult();
	}
}
