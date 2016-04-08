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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.string.SuperString;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.event.BusinessOperationCompletedEvent;
import com.zizaike.core.framework.event.BusinessOperationFailedEvent;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.SSIDAuthenticationException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.vo.LoginVo;
import com.zizaike.is.passport.LoginService;
import com.zizaike.is.redis.passport.SSIDRedisService;
import com.zizaike.passport.bo.EventPublishService;
import com.zizaike.passport.domain.PassportBusinessOperation;
import com.zizaike.passport.domain.source.LoginEventSource;
import com.zizaike.passport.service.CommonService;

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
    private CommonService commonService;
    @Autowired
    private SSIDRedisService ssidRedisService;
    @Autowired
    private EventPublishService eventPublishService;

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
        PassportResult passportResult = null;
        LoginEventSource loginEventSource = LoginEventSource.newInstance(loginVo);
        try {

            passportResult = commonService.login(loginVo);
            loginEventSource.setUserId(passportResult.getPassport().getUserId());
            loginEventSource.setSSID(passportResult.getExpends().get("SSID"));
            BusinessOperationCompletedEvent<LoginEventSource> completedEvent = new BusinessOperationCompletedEvent<LoginEventSource>(
                    PassportBusinessOperation.LOGIN, loginEventSource);
            eventPublishService.publishEvent(completedEvent);
            LOG.info("passport login success, userId={}, use {}ms", passportResult.getPassport().getUserId(),
                    System.currentTimeMillis() - start);
        } catch (ZZKServiceException zz) {
            BusinessOperationFailedEvent<LoginEventSource> failedEvent = new BusinessOperationFailedEvent<LoginEventSource>(
                    PassportBusinessOperation.LOGIN, loginEventSource, zz);
            eventPublishService.publishEvent(failedEvent);
            throw zz;
        }
        return passportResult;
    }

    @Override
    public Passport getPassport(ChannelType channelType, String SSID) throws ZZKServiceException {
        if (SuperString.isBlank(SSID)) {
            throw new IllegalParamterException("SSID is error");
        }
        long start = System.currentTimeMillis();
        Passport passport = ssidRedisService.getPassport( SSID);
        if (passport == null) {
            LOG.info("get passport failed,SSID={}", SSID);
            throw new SSIDAuthenticationException();
        }
        LOG.info("get passport success, SSID={}, userId={}, use {}ms", SSID,
                (passport == null ? null : passport.getUserId()), System.currentTimeMillis() - start);
        return passport;
    }

}
