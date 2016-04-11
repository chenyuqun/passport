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
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.passport.basetest.BaseTest;
import com.zizaike.passport.entity.PasswordLog;

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
@TransactionConfiguration(defaultRollback = true)
public class PasswordLogServiceTest extends BaseTest{
    @Autowired
    private  PasswordLogService passwordLogService;
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testPasswordLogEmpty() throws ZZKServiceException {
        passwordLogService.success(null);
    }
    @Test(expectedExceptions=IllegalParamterException.class)
    public void testPasswordLogUserIdEmpty() throws ZZKServiceException {
        PasswordLog passwordLog = new PasswordLog();
        passwordLog.setUserId(null);
        passwordLogService.success(passwordLog);
    }
    @Test()
    public void testPasswordLogSuccess() throws ZZKServiceException {
        PasswordLog passwordLog = new PasswordLog();
        passwordLog.setUserId(1234567);
        passwordLog.setChannel(ChannelType.APP);
        passwordLog.setIp(IP_DEFAULT);
        passwordLog.setPassword(PASSWORD_UNENCRPTED);
        passwordLogService.success(passwordLog);
    }
    @Test()
    public void testPasswordLogFailure() throws ZZKServiceException {
        PasswordLog passwordLog = new PasswordLog();
        passwordLog.setUserId(1234567);
        passwordLog.setChannel(ChannelType.APP);
        passwordLog.setIp(IP_DEFAULT);
        passwordLog.setReason("错误");
        passwordLog.setErrorCode("出错");
        passwordLog.setPassword(PASSWORD_UNENCRPTED);
        passwordLogService.failure(passwordLog);
    }
  
    
}
  
