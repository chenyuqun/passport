/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceTest.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午4:27:08  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.passport.basetest.BaseTest;
import com.zizaike.passport.entity.LoginLog;

/**
 * 
 * ClassName: LoginServiceTest <br/>  
 * Function: 登陆j日志服务. <br/>  
 * date: 2016年4月1日 下午3:41:16 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class LoginLogServiceTest extends BaseTest{
    @Autowired
    private  LoginLogService loginLogService;
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testLoginLogEmpty() throws ZZKServiceException {
        loginLogService.success(null);
    }
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testLoginLogUserIdEmpty() throws ZZKServiceException {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(null);
        loginLogService.success(loginLog);
    }
    @Test()
    public void testLoginLogSuccess() throws ZZKServiceException {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(1234567);
        loginLog.setChannel(ChannelType.APP);
        loginLog.setIp(IP_DEFAULT);
        loginLog.setLoginAt(new Date());
        loginLog.setLoginType(LoginType.EMAIL_LOGIN);
        loginLogService.success(loginLog);
    }
  
    
}
  
