/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceTest.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午4:27:08  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import static org.junit.Assert.assertEquals;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.zizaike.entity.passport.Tlas;
import com.zizaike.passport.basetest.BaseTest;

/**  
 * ClassName:TlasServiceTest <br/>  
 * Function: Tlas. <br/>  
 * Date:     2016年3月24日 下午4:27:08 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class TlasServiceTest extends BaseTest{
    @Autowired
    private TlasService tlasService;
    @Test
    public void findAll() {
        assertEquals(TlasService.TLAS_COUNT, tlasService.findAll().size());
    }
    @Test
    public void testGetSaltEmpty() {
        Assert.assertNull(tlasService.getSalt(null));
        Assert.assertNull(tlasService.getSalt(""));
    }
    @Test
    public void testGetSaltNotExist() {
        Assert.assertNull(tlasService.getSalt(UUID.randomUUID().toString()));
    }
    @Test
    public void testGetSaltSuccess() {
        int index = new Random(System.currentTimeMillis()).nextInt(TlasService.TLAS_COUNT);
        Tlas salt = tlasService.findAll().get(index);
        
        String result = tlasService.getSalt(salt.getTlasRef());
        Assert.assertNotNull(result);
    }
    @Test
    public void testGetRandomSaltEmpty() {
        Assert.assertNull(tlasService.getRandomSalt(null));
        Assert.assertNull(tlasService.getRandomSalt(""));
    }

    @Test
    public void testGetRandomSaltIncorrectInput() {
        Assert.assertNull(tlasService.getRandomSalt(UUID.randomUUID().toString()));
    }
    
    @Test
    public void testGetRandomSaltSuccess() {
        Assert.assertNotNull(tlasService.getRandomSalt(IP_DEFAULT));
    }
}
  
