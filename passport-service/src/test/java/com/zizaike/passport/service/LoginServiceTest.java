/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceTest.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午4:27:08  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.date.SuperDate;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordIncorrectException;
import com.zizaike.core.framework.exception.passport.UserNameFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.UserNotExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.vo.LoginVo.LoginVoBuilder;
import com.zizaike.is.passport.LoginService;
import com.zizaike.passport.basetest.BaseTest;

/**
 * 
 * ClassName: LoginServiceTest <br/>  
 * Function: 登陆服务. <br/>  
 * date: 2016年4月1日 下午3:41:16 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LoginServiceTest extends BaseTest{
    @Autowired
    private  LoginService loginService;
    @Test(expectedExceptions=UserNameFormatIncorrectException.class)
    public void testLoginUserNameEmpty() throws ZZKServiceException {
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(null, null, null, PASSWORD_UNENCRPTED, IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.USER_NAME_LOGIN);
        loginService.login(loginVoBuilder.build());
    }
    @Test(expectedExceptions=PasswordFormatIncorrectException.class)
    public void testLoginPasswordEmpty() throws ZZKServiceException {
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(null, null, null, null, IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.USER_NAME_LOGIN);
        loginService.login(loginVoBuilder.build());
    }
    
    @Test(expectedExceptions=UserNotExistException.class)
    public void testLoginNotExistUser() throws ZZKServiceException {
        String userName = "自在客测试";
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(null, null, userName, PASSWORD_UNENCRPTED, IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.USER_NAME_LOGIN);
        loginService.login(loginVoBuilder.build());
        
    }
    
    
    @Test(description="密码出错" ,expectedExceptions=PasswordIncorrectException.class)
    public void testLoginIncorrectPassword() throws ZZKServiceException {
        Passport passport = registerTestPassport();
        User user = userService.findByUserId(passport.getUserId());
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(user.getMail(), null, null, CommonUtil.generatePassword(null, "LUCKY_DAY"), IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.EMAIL_LOGIN);
        loginService.login(loginVoBuilder.build());
    }
    @Test(description="成功登陆")
    public void testLoginSuccess() throws ZZKServiceException {
        Passport passport = registerTestPassport();
        User user = userService.findByUserId(passport.getUserId());
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(user.getMail(), null, null, PASSWORD_UNENCRPTED, IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.EMAIL_LOGIN);
        PassportResult result = loginService.login(loginVoBuilder.build());
        
        Passport findPassport = passportService.findPassport(result.getPassport().getUserId());
        Assert.assertNotNull(findPassport);
    }
    
}
  
