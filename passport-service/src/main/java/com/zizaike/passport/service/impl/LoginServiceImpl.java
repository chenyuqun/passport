/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月28日下午6:21:44  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.string.SuperString;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.IPFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.vo.LoginVo;
import com.zizaike.is.passport.LoginService;

/**
 * ClassName:PassportServiceImpl <br/>
 * Function: passport. <br/>
 * Date: 2016年3月28日 下午6:21:44 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServiceImpl.class);
  
    /**
     * 登录参数检查合法性
     * 
     * @param result
     * @param username
     * @param password
     * @param ip
     * @throws PassportException
     */
    private void validLoginParams(String email, String password, String ip) throws ZZKServiceException {
        // 直接字段检查合法性
        try {
            Preconditions.checkArgument(!SuperString.isBlank(email), "email is empty");
            Preconditions.checkArgument(!SuperString.isBlank(password), "password is empty");
            Preconditions.checkArgument(!SuperString.isBlank(ip), "ip is empty");
        } catch (IllegalArgumentException e) {
            throw new IllegalParamterException(e.getMessage());
        }
        
        if (!CommonUtil.isPasswordFormatCorrect(password)) {
            throw new PasswordFormatIncorrectException();
        }
        if (!CommonUtil.isIp(ip)) {
            throw new IPFormatIncorrectException();
        }
    }

    @Override
    public PassportResult login(LoginVo loginVo) throws ZZKServiceException {
        return null;
    }

    @Override
    public Passport getPassport(ChannelType channelType, String SSID) throws ZZKServiceException {
          
        // TODO Auto-generated method stub  
        return null;
    }


}
