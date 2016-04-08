/**  
 * Project Name:passport-service  <br/>
 * File Name:RegisterServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月25日下午8:23:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.passport.LogoutService;
import com.zizaike.passport.service.UserSSIDService;

/**
 * ClassName:RegisterServiceImpl <br/>
 * Function: 注册服务 <br/>
 * Date: 2016年3月25日 下午8:23:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class LogoutServiceImpl implements LogoutService {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutServiceImpl.class);
    @Autowired
    private UserSSIDService UserSSIDService;
    @Override
    public void logout(String SSID) throws ZZKServiceException {
        if (StringUtils.isEmpty(SSID)) {
            throw new IllegalParamterException("SSID is empty");
        }
        long start = System.nanoTime();
        UserSSIDService.deleteSSID(SSID);
        LOG.info("logout end, SSID={}, use {}ns", SSID, System.nanoTime() - start);
    }

   
}
