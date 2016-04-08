package com.zizaike.passport.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.event.CommonOperationAction;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.domain.PassportBusinessOperation;
import com.zizaike.passport.domain.event.PassportApplicationEvent;
import com.zizaike.passport.domain.source.LoginEventSource;
import com.zizaike.passport.domain.source.LoginFailedEventSouce;
import com.zizaike.passport.entity.LoginLog;
import com.zizaike.passport.entity.UserSSID;
import com.zizaike.passport.mq.MQService;
import com.zizaike.passport.service.LoginLogService;
import com.zizaike.passport.service.UserSSIDService;

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
@Service("loginMQService")
public class LoginMQServiceImpl implements MQService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginMQServiceImpl.class);
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private UserSSIDService userSSIDService;

    @Override
    public void sendMsg(PassportApplicationEvent event) throws IllegalParamterException {
        if (PassportBusinessOperation.LOGIN != event.getOperation()) {
            return;
        }
        LoginEventSource loginEventSource = (LoginEventSource) event.getSource();
        LoginLog loginLog = LoginLog.newInstance(loginEventSource);
        UserSSID userSSID = UserSSID.newInstance(loginEventSource.getUserId(), loginEventSource.getSSID());
        if (CommonOperationAction.Completed == event.getAction()) {
            loginLogService.success(loginLog);
            userSSIDService.save(userSSID);
            LOG.info("send login success event,event source={}", loginEventSource);
        } else if (CommonOperationAction.Failed == event.getAction()) {
            ZZKServiceException ve = event.getException();
            LoginFailedEventSouce loginFailedEventSouce = new LoginFailedEventSouce(ve, loginEventSource);
            loginLog.setErrorCode(loginFailedEventSouce.getErrorCode());
            loginLog.setReason(loginFailedEventSouce.getReason());
            loginLogService.failure(loginLog);
            LOG.info("send login fail event,event source={}", loginEventSource);
        }
    }
}
