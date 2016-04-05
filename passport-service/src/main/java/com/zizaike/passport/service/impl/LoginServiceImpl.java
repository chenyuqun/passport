/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月28日下午6:21:44  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.string.SuperString;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordIncorrectException;
import com.zizaike.core.framework.exception.passport.SSIDAuthenticationException;
import com.zizaike.core.framework.exception.passport.UserNotExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.vo.LoginVo;
import com.zizaike.is.passport.LoginService;
import com.zizaike.is.redis.passport.SSIDRedisService;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.service.TlasService;
import com.zizaike.passport.service.UserService;

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
    @Autowired
    private UserService userService;
    @Autowired
    private TlasService tlasService;
    @Autowired
    private PassportService passportService;
    @Autowired
    private SSIDRedisService ssidRedisService;


    @Override
    public PassportResult login(LoginVo loginVo) throws ZZKServiceException {
        if (loginVo == null) {
            throw new IllegalParamterException("login loginVo is not null");
        }
        if (loginVo.getLoginType() == null) {
            throw new IllegalParamterException("login loginType is not null");
        }
        long start = System.currentTimeMillis();
        if (!CommonUtil.isIp(loginVo.getIp())) {
            LOG.info("login ip error, but can login, username={},mobile{},email{}, ip={}, defaultIp={}",
                    loginVo.getUserName(), loginVo.getMobile(), loginVo.getEmail(), loginVo.getIp(),
                    Constant.IP_DEFAULT);
            loginVo.setIp(Constant.IP_DEFAULT);
        }
        if (!CommonUtil.isPasswordFormatCorrect(loginVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        User user = null;
        if (loginVo.getLoginType() == LoginType.EMAIL_LOGIN) {
            user = userService.findByEmail(loginVo.getEmail());
        } else if (loginVo.getLoginType() == LoginType.MOBILE_LOGIN) {
            user = userService.findByMobile(loginVo.getMobile());
        } else if (loginVo.getLoginType() == LoginType.USER_NAME_LOGIN) {
            user = userService.findByUserName(loginVo.getUserName());
        }
        if (user == null) {
            throw new UserNotExistException();
        }
        Passport passport = passportService.findPassport(user.getUserId());
        passport.setLoginType(loginVo.getLoginType());
        passport.setIsFirst(false);
        String hash = CommonUtil.generateHash(loginVo.getPassword(), tlasService.getSalt(passport.getSalt()));
        if (StringUtils.isNotEmpty(passport.getHash()) && !passport.getHash().equals(hash)) {
            LOG.info("login failed incorrect password, userId={} ", passport.getUserId());
            throw new PasswordIncorrectException();
        }
        // 保护性copy，防止外部接口直接使用原对象
        Passport ssidPassport = new Passport();
        try {
            BeanUtils.copyProperties(ssidPassport, passport);
        } catch (Exception e) {
            LOG.error("copy passport is error", e);
        }
        // 调用生成SSID的方法
        PassportResult passportResult = passportService.getSSID(loginVo.getChannelType(), ssidPassport);
        LOG.info("passport login success, userId={}, use {}ms", passportResult.getPassport().getUserId(),
                System.currentTimeMillis() - start);
        return passportResult;
    }

    @Override
    public Passport getPassport(ChannelType channelType, String SSID) throws ZZKServiceException {
        if (SuperString.isBlank(SSID)) {
            throw new IllegalParamterException("SSID is error");
        }
        long start = System.currentTimeMillis();
        Passport passport = ssidRedisService.getPassport(channelType,SSID);
        if (passport == null) {
            LOG.info("get passport failed,SSID={}",SSID);
            throw new SSIDAuthenticationException();
        }
        LOG.info("get passport success, SSID={}, userId={}, use {}ms",
                SSID, (passport == null ? null : passport.getUserId()),
                System.currentTimeMillis() - start);
        return passport;
    }

}
