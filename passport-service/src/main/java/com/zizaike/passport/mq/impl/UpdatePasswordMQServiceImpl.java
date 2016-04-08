package com.zizaike.passport.mq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.event.CommonOperationAction;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.domain.PassportBusinessOperation;
import com.zizaike.passport.domain.event.PassportApplicationEvent;
import com.zizaike.passport.domain.source.UpdatePasswordEventSource;
import com.zizaike.passport.entity.PasswordLog;
import com.zizaike.passport.mq.MQService;
import com.zizaike.passport.service.PasswordLogService;
import com.zizaike.passport.service.UserSSIDService;

/**
 * 
 * ClassName: LoginMQServiceImpl <br/>
 * Function: 修改密码通知 . <br/>
 * date: 2016年4月6日 下午2:53:50 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
@Service("updatePasswordMQService")
public class UpdatePasswordMQServiceImpl implements MQService {

    private static final Logger LOG = LoggerFactory.getLogger(UpdatePasswordMQServiceImpl.class);
    @Autowired
    private PasswordLogService passwordLogService;
    @Autowired
    private UserSSIDService userSSIDService;
    
    @Override
    public void sendMsg(PassportApplicationEvent event) throws ZZKServiceException {
        if (!(PassportBusinessOperation.UPDATE_PASSWORD == event.getOperation() || PassportBusinessOperation.RESET_PASSWORD == event
                .getOperation())) {
            return;
        }
        UpdatePasswordEventSource updatePasswordEventSource = (UpdatePasswordEventSource) event.getSource();
        PasswordLog passwordLog = PasswordLog.newInstance(updatePasswordEventSource);
        if (CommonOperationAction.Failed == event.getAction()) {
            // TODO 先不考虑
        } else if (CommonOperationAction.Completed == event.getAction()) {
            passwordLogService.success(passwordLog);
            userSSIDService.deleteByUserId(updatePasswordEventSource.getUserId());
            LOG.info("send update password success message,userId={},operation={},action={}", updatePasswordEventSource.getUserId(),
                    event.getOperation(), event.getAction());
        }
    }
}
