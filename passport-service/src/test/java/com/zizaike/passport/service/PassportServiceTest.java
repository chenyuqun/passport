/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceTest.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午4:27:08  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.SSIDAuthenticationException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.passport.basetest.BaseTest;

/**  
 * ClassName:UserServiceTest <br/>  
 * Function: User. <br/>  
 * Date:     2016年3月24日 下午4:27:08 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PassportServiceTest extends BaseTest{
    @Autowired
    private PassportService passportService;
    @Test(description="测试查询条件: userID is null", expectedExceptions = IllegalParamterException.class)
    public void testFindPassportParam1() throws ZZKServiceException {
            passportService.findPassport(null);
    }
    @Test
    public void testFindById() throws ZZKServiceException {
        Assert.assertNull(passportService.findPassport(generateId()));
        
        Passport exist = addTestPassportByMail();
        
        Passport passport = passportService.findPassport(exist.getUserId());
        Assert.assertNotNull(passport);
        Assert.assertEquals(exist.getUserId(), passport.getUserId());
    }
    @Test(description="测试查询条件:  is null", expectedExceptions = SSIDAuthenticationException.class)
    public void testSSIDIsNull() throws ZZKServiceException{
        passportService.checkSSID(ChannelType.APP, "");
    }
    @Test(description="测试查询条件: ssid is UUID", expectedExceptions = SSIDAuthenticationException.class)
    public void testSSIDUnknownException() throws ZZKServiceException{
        passportService.checkSSID(ChannelType.APP, UUID.randomUUID().toString());
    }
    @Test
    public void testSSIDSuccess() throws ZZKServiceException{
        Passport passport = addTestPassportByMail();
        PassportResult result = passportService.getSSID(ChannelType.APP,passport);
       passportService.checkSSID(ChannelType.APP,result.getExpends().get("SSID"));
    }
    
    @Test(description="保存 passport is null", expectedExceptions = IllegalParamterException.class)
    public void testSaveEmpty() throws ZZKServiceException {
        passportService.save(null);
    }
    
    @Test
    public void testSaveSuccess() throws ZZKServiceException {
        Passport passport = createPassport();
        passport.setUserId(generateId());
        passportService.save(passport);
        
        Passport exist = passportService.findPassport( passport.getUserId());
        Assert.assertNotNull(exist);
        Assert.assertEquals(passport.getUserId(), exist.getUserId());
    }
    @Test(description="修改密码:slat hash is null and userID is null", expectedExceptions=IllegalParamterException.class)
    public void testUpdatePasswordParam1() throws ZZKServiceException{
        passportService.updatePasspword(null, null, null);
    }
    
    @Test(description="修改密码成功")
    public void testUpdateSuccess() throws ZZKServiceException{
        int id = generateId();
        Passport passport = createPassport();
        passport.setUserId(id);
        passportService.save(passport);
        String hash = UUID.randomUUID().toString();
        passport.setHash(hash);
        passportService.updatePasspword(passport.getUserId(), hash, "salt");
        Passport exist = passportService.findPassport(passport.getUserId());
        Assert.assertNotNull(exist);
        Assert.assertEquals(hash, exist.getHash());
    }
    @Test(expectedExceptions = IllegalParamterException.class)
    public void testGetSSIDPassportNull() throws ZZKServiceException {
        passportService.getSSID(ChannelType.APP, null);
    }
    
    
}
  
