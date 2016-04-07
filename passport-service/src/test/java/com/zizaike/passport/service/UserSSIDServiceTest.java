/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceTest.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午4:27:08  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.passport.basetest.BaseTest;
import com.zizaike.passport.entity.UserSSID;

/**
 * 
 * ClassName: LoginServiceTest <br/>
 * Function: user ssid服务. <br/>
 * date: 2016年4月1日 下午3:41:16 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserSSIDServiceTest extends BaseTest {
    @Autowired
    private UserSSIDService userSSIDService;

    @Test(expectedExceptions = IllegalParamterException.class)
    public void testSaveEmpty() throws ZZKServiceException {
        userSSIDService.save(null);
    }

    @Test
    public void testUserSSIDsave() throws ZZKServiceException {
        PassportResult passportResult = registerTestPassport();
        Passport passport = passportResult.getPassport();
        UserSSID userSSID = new UserSSID();
        userSSID.setUserId(passport.getUserId());
        userSSID.setSsid(passportResult.getExpends().get("SSID"));
        userSSIDService.save(userSSID);
    }
    @Test
    public void deleteSSID() throws ZZKServiceException {
        PassportResult passportResult = registerTestPassport();
        Passport passport = passportResult.getPassport();
        UserSSID userSSID = new UserSSID();
        userSSID.setUserId(passport.getUserId());
        userSSID.setSsid(passportResult.getExpends().get("SSID"));
        userSSIDService.save(userSSID);
        userSSIDService.deleteSSID(passportResult.getExpends().get("SSID"));
    }
    @Test
    public void deleteUserId() throws ZZKServiceException {
        PassportResult passportResult = registerTestPassport();
        Passport passport = passportResult.getPassport();
        UserSSID userSSID = new UserSSID();
        userSSID.setUserId(passport.getUserId());
        userSSID.setSsid(passportResult.getExpends().get("SSID"));
        userSSIDService.save(userSSID);
        userSSIDService.deleteByUserId(passport.getUserId());
    }
    @Test
    public void findByUserId() throws ZZKServiceException {
        PassportResult passportResult = registerTestPassport();
        Passport passport = passportResult.getPassport();
        UserSSID userSSID = new UserSSID();
        userSSID.setUserId(passport.getUserId());
        userSSID.setSsid(passportResult.getExpends().get("SSID"));
        userSSIDService.save(userSSID);
        List<UserSSID> list =  userSSIDService.findByUserId(passport.getUserId());
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(passport.getUserId(), list.get(0).getUserId());
    }

}
