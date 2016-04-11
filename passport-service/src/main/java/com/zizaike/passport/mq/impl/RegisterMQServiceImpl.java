package com.zizaike.passport.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.event.CommonOperationAction;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.passport.domain.PassportBusinessOperation;
import com.zizaike.passport.domain.event.PassportApplicationEvent;
import com.zizaike.passport.domain.source.RegisterEventSource;
import com.zizaike.passport.domain.source.RegisterFailedEventSource;
import com.zizaike.passport.entity.RegisterLog;
import com.zizaike.passport.entity.UserSSID;
import com.zizaike.passport.mq.MQService;
import com.zizaike.passport.service.RegisterLogService;
import com.zizaike.passport.service.UserSSIDService;

/**
 * 
 * ClassName: RegisterMQServiceImpl <br/>
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
    @Autowired
    private UserSSIDService userSSIDService;

    @Override
    public void sendMsg(PassportApplicationEvent event) throws IllegalParamterException {
        if (PassportBusinessOperation.REGISTER != event.getOperation()) {
            return;
        }
        RegisterEventSource registerEventSource = (RegisterEventSource) event.getSource();
        RegisterLog registerLog = RegisterLog.newInstance(registerEventSource);
        UserSSID userSSID = UserSSID.newInstance(registerEventSource.getUserId(), registerEventSource.getSSID());
        if (CommonOperationAction.Completed == event.getAction()) {
            registerLogService.success(registerLog);
            userSSIDService.save(userSSID);
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
