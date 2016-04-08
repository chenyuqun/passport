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

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.LoginVo.LoginVoBuilder;
import com.zizaike.entity.passport.domain.vo.RegisterVo.RegisterVoBuilder;
import com.zizaike.is.passport.LoginService;
import com.zizaike.is.passport.LogoutService;
import com.zizaike.passport.basetest.BaseTest;

/**
 * 
 * ClassName: LoginServiceTest <br/>
 * Function: 注销服务. <br/>
 * date: 2016年4月1日 下午3:41:16 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class LogoutServiceTest extends BaseTest {
    @Autowired
    private LoginService loginService;
    @Autowired
    private LogoutService logoutService;

    @Test(description = "成功注销")
    public void testLoginSuccess() throws ZZKServiceException {

        String password = PASSWORD_UNENCRPTED;
        RegisterVoBuilder builder = new RegisterVoBuilder(null, generateRandomMobile(), password, IP_DEFAULT);
        builder.setChannelType(ChannelType.APP);
        builder.setRegisterType(RegisterType.MOBILE);
        // 注册
        PassportResult result = registerService.registerPassport(builder.build(), null);
        Passport passport = result.getPassport();
        Assert.assertNotNull(passport);
        Assert.assertTrue(passport.getUserId() > 0);

        Assert.assertNotNull(passportService.findPassport(passport.getUserId()));

        User user = userService.findByUserId(passport.getUserId());
        Assert.assertNotNull(user);
        Assert.assertEquals(passport.getUserId(), user.getUserId());
        LoginVoBuilder loginVoBuilder = new LoginVoBuilder(user.getEmail(), null, null, PASSWORD_UNENCRPTED, IP_DEFAULT);
        loginVoBuilder.setChannelType(ChannelType.APP).setLoginType(LoginType.EMAIL_LOGIN);
        // 注销
        logoutService.logout(result.getExpends().get("SSID"));
    }

}
