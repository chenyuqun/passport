/**  
 * Project Name:passport-api  <br/>
 * File Name:RegisterController.java  <br/>
 * Package Name:com.zizaike.passport.api.controller  <br/>
 * Date:2016年3月31日上午10:10:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.api.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.vo.LoginVo.LoginVoBuilder;
import com.zizaike.is.passport.LoginService;
import com.zizaike.passport.api.BaseAjaxController;

/**  
 * ClassName:RegisterController <br/>  
 * Function: 密码服务. <br/>  
 * Date:     2016年3月31日 上午10:10:14 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Controller
@RequestMapping("/passport/login")
public class LoginController  extends BaseAjaxController {
    @Autowired
    private LoginService loginService;
    /**
     * 
     * login:邮箱登陆. <br/>  
     *  
     * @author snow.zhang  
     * @param email
     * @param password
     * @param ip
     * @param channelType
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "emailLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult emailLogin(String email,String password,String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(email, null, null, password, ip);
        loginVoBuilder.setChannelType(channelType).setLoginType(LoginType.EMAIL_LOGIN);
        result.setInfo(loginService.login(loginVoBuilder.build()));
        return result;
    }
    /**
     * 
     * mobileLogin:手机登陆. <br/>  
     *  
     * @author snow.zhang  
     * @param mobile
     * @param password
     * @param ip
     * @param channelType
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "mobileLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult mobileLogin(String mobile,String password,String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(null, mobile, null, password, ip);
        loginVoBuilder.setChannelType(channelType).setLoginType(LoginType.MOBILE_LOGIN);
        result.setInfo(loginService.login(loginVoBuilder.build()));
        return result;
    }
    /**
     * 
     * userNameLogin:手机登陆. <br/>  
     *  
     * @author snow.zhang  
     * @param userName
     * @param password
     * @param ip
     * @param channelType
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "userNameLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult userNameLogin(String userName,String password,String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(null, null, userName, password, ip);
        loginVoBuilder.setChannelType(channelType).setLoginType(LoginType.USER_NAME_LOGIN);
        result.setInfo(loginService.login(loginVoBuilder.build()));
        return result;
    }
}
  
