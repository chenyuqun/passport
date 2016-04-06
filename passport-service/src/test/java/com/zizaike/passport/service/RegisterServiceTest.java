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
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.RegisterVo.RegisterVoBuilder;
import com.zizaike.is.passport.RegisterService;
import com.zizaike.is.redis.passport.SSIDRedisService;
import com.zizaike.passport.basetest.BaseTest;

/**
 * 
 * ClassName: LoginServiceTest <br/>
 * Function: 注册服务. <br/>
 * date: 2016年4月1日 下午3:41:16 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RegisterServiceTest extends BaseTest {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private SSIDRedisService ssidRedisService;

    @Test(description = "password format incorrect", expectedExceptions = PasswordFormatIncorrectException.class)
    public void testRegisterPasswordEmpty() throws ZZKServiceException {
        RegisterVoBuilder builder = new RegisterVoBuilder(generateRandomMail(), null, null, IP_DEFAULT);
        builder.setChannelType(ChannelType.APP).setRegisterType(RegisterType.EMAIL);
        registerService.registerPassport(builder.build(), null);
        Assert.fail("testRegisterPasswordEmpty");
    }

    @Test
    public void testRegisterMobileSuccess() throws ZZKServiceException {

        String password = PASSWORD_UNENCRPTED;
        RegisterVoBuilder builder = new RegisterVoBuilder(null, generateRandomMobile(), password, IP_DEFAULT);
        builder.setChannelType(ChannelType.APP);
        builder.setRegisterType(RegisterType.MOBILE);
        PassportResult result = registerService.registerPassport(builder.build(), null);
        Passport passport = result.getPassport();
        Assert.assertNotNull(passport);
        Assert.assertTrue(passport.getUserId() > 0);

        Assert.assertNotNull(passportService.findPassport(passport.getUserId()));

        User user = userService.findByUserId(passport.getUserId());
        Assert.assertNotNull(user);
        Assert.assertEquals(passport.getUserId(), user.getUserId());

        String SSID = result.getExpends().get("SSID");
        Assert.assertEquals(user.getUserId(), ssidRedisService.getPassport(ChannelType.APP, SSID).getUserId());
    }

    @Test
    public void testRegisterEmailSuccess() throws ZZKServiceException {

        String password = PASSWORD_UNENCRPTED;
        RegisterVoBuilder builder = new RegisterVoBuilder(generateRandomMail(), null, password, IP_DEFAULT);
        builder.setChannelType(ChannelType.APP);
        builder.setRegisterType(RegisterType.EMAIL);
        PassportResult result = registerService.registerPassport(builder.build(), null);
        Passport passport = result.getPassport();
        Assert.assertNotNull(passport);
        Assert.assertTrue(passport.getUserId() > 0);

        Assert.assertNotNull(passportService.findPassport(passport.getUserId()));

        User user = userService.findByUserId(passport.getUserId());
        Assert.assertNotNull(user);
        Assert.assertEquals(passport.getUserId(), user.getUserId());

        String SSID = result.getExpends().get("SSID");
        Assert.assertEquals(user.getUserId(), ssidRedisService.getPassport(ChannelType.APP, SSID).getUserId());
    }

}
