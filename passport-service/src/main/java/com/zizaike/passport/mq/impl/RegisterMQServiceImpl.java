package com.zizaike.passport.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.event.CommonOperationAction;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.domain.event.PassportApplicationEvent;
import com.zizaike.passport.domain.event.PassportBusinessOperation;
import com.zizaike.passport.domain.event.RegisterEventSource;
import com.zizaike.passport.domain.event.RegisterFailedEventSource;
import com.zizaike.passport.entity.RegisterLog;
import com.zizaike.passport.mq.MQService;
import com.zizaike.passport.service.RegisterLogService;

/**
 * 
 * ClassName: LoginMQServiceImpl <br/>  
 * Function: 登陆消息（暂时只有日志）. <br/>  
 * date: 2016年4月6日 下午2:53:50 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Service("registerMQService")
public class RegisterMQServiceImpl implements MQService {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterMQServiceImpl.class);
    @Autowired
    private RegisterLogService registerLogService;

    @Override
    public void sendMsg(PassportApplicationEvent event) throws IllegalParamterException {
        if (PassportBusinessOperation.LOGIN != event.getOperation()) {
            return;
        }
        RegisterEventSource registerEventSource = (RegisterEventSource) event.getSource();
        RegisterLog registerLog = RegisterLog.newInstance(registerEventSource);
        if (CommonOperationAction.Completed == event.getAction()) {
            registerLogService.success(registerLog);
            LOG.info("send register success event,event source={}", registerEventSource);
        } else if (CommonOperationAction.Failed == event.getAction()) {
            ZZKServiceException ve = event.getException();
            RegisterFailedEventSource registerFailedEventSouce = new RegisterFailedEventSource(ve, registerEventSource);
            registerLog.setErrorCode(registerFailedEventSouce.getErrorCode());
            registerLog.setReason(registerFailedEventSouce.getReason());
            registerLogService.failure(registerLog);
            LOG.info("send register fail event,event source={}", registerEventSource);
        }
    }
}
