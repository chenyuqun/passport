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
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.passport.basetest.BaseTest;
import com.zizaike.passport.entity.RegisterLog;

/**
 * 
 * ClassName: LoginServiceTest <br/>  
 * Function: 登陆日志服务. <br/>  
 * date: 2016年4月1日 下午3:41:16 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class RegisterLogServiceTest extends BaseTest{
    @Autowired
    private  RegisterLogService registerLogService;
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testRegisterLogEmpty() throws ZZKServiceException {
        registerLogService.success(null);
    }
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testRegisterLogUserIdEmpty() throws ZZKServiceException {
        RegisterLog registerLog = new RegisterLog();
        registerLog.setUserId(null);
        registerLogService.success(registerLog);
    }
    @Test()
    public void testRegisterLogSuccess() throws ZZKServiceException {
        RegisterLog registerLog = new RegisterLog();
        registerLog.setUserId(1234567);
        registerLog.setChannel(ChannelType.APP);
        registerLog.setIp(IP_DEFAULT);
        registerLog.setEmail(generateRandomMail());
        registerLog.setRegisterType(RegisterType.EMAIL);
        registerLog.setRegisterAt(new Date());
        registerLogService.success(registerLog);
    }
    @Test()
    public void testRegisterLogFailure() throws ZZKServiceException {
        RegisterLog registerLog = new RegisterLog();
        registerLog.setUserId(1234567);
        registerLog.setChannel(ChannelType.APP);
        registerLog.setIp(IP_DEFAULT);
        registerLog.setEmail(generateRandomMail());
        registerLog.setRegisterType(RegisterType.EMAIL);
        registerLog.setRegisterAt(new Date());
        registerLog.setReason("错误");
        registerLog.setErrorCode("出错");
        registerLogService.failure(registerLog);
    }
  
    
}
  
