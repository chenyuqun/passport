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
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.entity.passport.User;
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
public class UserServiceTest extends BaseTest{
    @Autowired
    private UserService userService;
    @Test(description="mobile is null or '' or error Format",expectedExceptions= MobileFormatIncorrectException.class)
    public void testIsMobileExistEmptyError() throws MobileFormatIncorrectException {
        userService.isMobileExist("");
    }
    @Test(description="mobile is null or '' or error Format" ,expectedExceptions= MobileFormatIncorrectException.class)
    public void testIsMobileExistNullError() throws MobileFormatIncorrectException {
        userService.isMobileExist(null);
    }
    @Test(description="mobile is null or '' or error Format" ,expectedExceptions= MobileFormatIncorrectException.class)
    public void testIsMobileExistFormatError() throws MobileFormatIncorrectException {
        userService.isMobileExist("88");
    }
    @Test(description="手机不存在" )
    public void testIsMobile() throws MobileFormatIncorrectException {
        Assert.assertTrue(!userService.isMobileExist("18521002423"));
    }
    @Test(description="email is null or '' or error Format" ,expectedExceptions= EmailFormatIncorrectException.class)
    public void testIsEmailExistEmptyError() throws   EmailFormatIncorrectException{
        userService.isEmailExist("");
    }
    @Test(description="email is null or '' or error Format" ,expectedExceptions= EmailFormatIncorrectException.class)
    public void testIsEmailExistNullError() throws   EmailFormatIncorrectException{
        userService.isEmailExist(null);
    }
    @Test(description="email is null or '' or error Format" ,expectedExceptions= EmailFormatIncorrectException.class)
    public void testIsEmailExistFormatError() throws   EmailFormatIncorrectException {
        userService.isEmailExist("88");
    }
    @Test(description="邮件不存在")
    public void testIsEmail() throws EmailFormatIncorrectException {
        Assert.assertTrue(!userService.isEmailExist("493455221@zizaike.com"));
    }
    
    @Test
    public void testSaveUserEmpty() throws ZZKServiceException {
        userService.save(null);
    }
    @Test(description="save user success")
    public void testSaveSuccess() throws Exception {
        User user = createUser();
        userService.save(user);
        User exist = userService.findByMobile(user.getMobile());
        Assert.assertNotNull(exist);
        Assert.assertEquals(user.getUserId(), user.getUserId());
    }
   
}
  
