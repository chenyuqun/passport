/**  
 * Project Name:passport-service  <br/>
 * File Name:RegisterServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月25日下午8:23:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.common.CommonUtils;
import com.zizaike.core.framework.event.BusinessOperationBeforeEvent;
import com.zizaike.core.framework.event.BusinessOperationCompletedEvent;
import com.zizaike.core.framework.event.BusinessOperationFailedEvent;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.vo.RegisterVo;
import com.zizaike.is.passport.RegisterService;
import com.zizaike.passport.bo.EventPublishService;
import com.zizaike.passport.domain.PassportBusinessOperation;
import com.zizaike.passport.domain.source.RegisterEventSource;
import com.zizaike.passport.service.CommonService;
import com.zizaike.passport.service.UserService;

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
public class RegisterServiceImpl implements RegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private EventPublishService eventPublishService;
    @Autowired
    private CommonService commonService;

    @Override
    public PassportResult registerPassport(RegisterVo registerVo, String extend) throws ZZKServiceException {
        PassportResult passportResult = null;
        RegisterEventSource eventSource = RegisterEventSource.newInstance(registerVo);
        try {
            BusinessOperationBeforeEvent<RegisterEventSource> beforeEvent = new BusinessOperationBeforeEvent<RegisterEventSource>(
                    PassportBusinessOperation.REGISTER, eventSource);
            eventPublishService.publishEvent(beforeEvent);
            passportResult = commonService.register(registerVo);
            eventSource.setUserId(passportResult.getPassport().getUserId());
            eventSource.setRegisterAt(passportResult.getPassport().getCreateAt());
            eventSource.setSSID(passportResult.getExpends().get("SSID"));
            BusinessOperationCompletedEvent<RegisterEventSource> completedEvent = new BusinessOperationCompletedEvent<RegisterEventSource>(
                    PassportBusinessOperation.REGISTER, eventSource);
            eventPublishService.publishEvent(completedEvent);
        } catch (ZZKServiceException zzke) {
            BusinessOperationFailedEvent<RegisterEventSource> failedEvent = new BusinessOperationFailedEvent<RegisterEventSource>(
                    PassportBusinessOperation.REGISTER, eventSource, zzke);
            eventPublishService.publishEvent(failedEvent);
            throw zzke;
        }
        return passportResult;
    }

   

    @Override
    public Boolean isMobileExist(String mobile) throws MobileFormatIncorrectException {
        if (!CommonUtils.isMobile(mobile)) {
            throw new MobileFormatIncorrectException();
        }
        return userService.isMobileExist(mobile);
    }

    @Override
    public Boolean isEmailExist(String email) throws EmailFormatIncorrectException {
        if (!CommonUtils.isMobile(email)) {
            throw new EmailFormatIncorrectException();
        }
        return userService.isEmailExist(email);
    }
}
