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
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.RegisterVo.RegisterVoBuilder;
import com.zizaike.is.passport.RegisterService;
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
@TransactionConfiguration(defaultRollback = true)
public class RegisterServiceTest extends BaseTest {
    @Autowired
    private RegisterService registerService;

    @Test(description = "password format incorrect", expectedExceptions = PasswordFormatIncorrectException.class)
    public void testRegisterPasswordEmpty() throws ZZKServiceException {
        RegisterVoBuilder builder = new RegisterVoBuilder(generateRandomMail(), null, null, IP_DEFAULT);
        builder.setChannelType(ChannelType.APP).setRegisterType(RegisterType.EMAIL);
        registerService.registerPassport(builder.build(), null);
        Assert.fail("testRegisterPasswordEmpty");

    }

}
