package com.zizaike.passport.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.zizaike.core.framework.event.BusinessOperation;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.domain.event.PassportApplicationEvent;
import com.zizaike.passport.domain.event.PassportBusinessOperation;
import com.zizaike.passport.mq.MQService;

/**
 * 
 * ClassName: PassportEventListener <br/>  
 * Function: passport行为监听器. <br/>  
 * date: 2016年4月6日 下午7:17:15 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Component
public class PassportEventListener implements ApplicationListener<PassportApplicationEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PassportEventListener.class);
     
     /** @Autowired
    private MQService thirdLoginMQServiceImpl;
    @Autowired
    private MQService updatePasswordMQServiceImpl;
    @Autowired
    private MQService bindVerifyInfoMQServiceImpl;**/
    @Autowired
    private MQService loginMQService;
    @Autowired
    private MQService registerMQService;


    @Override
    public void onApplicationEvent(PassportApplicationEvent event) {
        BusinessOperation operation = event.getOperation();
        if (operation == null)
            return;
        MQService mqService = getMQService(operation);
        if (mqService != null) {
            try {
                mqService.sendMsg(event);
            } catch (ZZKServiceException e) {
                e.printStackTrace();  
                logger.error("onApplicationEvent error {}",e);
            }
        }
    }

    /**
     * 
     * getMQService:根据业务操作获取对应的事件消息服务. <br/>  
     *  
     * @author snow.zhang  
     * @param operation
     * @return  
     * @since JDK 1.7
     */
    private MQService getMQService(BusinessOperation operation) {
        MQService mqService = null;
        switch ((PassportBusinessOperation) operation) {
            case LOGIN:
                mqService = loginMQService;
                break;
                case REGISTER:
                mqService = registerMQService;
                break;
             /** case THIRD_LOGIN:
                mqService = thirdLoginMQServiceImpl;
                break;
            case THIRD_BIND:
                mqService = thirdLoginMQServiceImpl;
                break;
            case VERIFY_USER_IDENTIFY:
                mqService = bindVerifyInfoMQServiceImpl;
                break;
            case UPDATE_PASSWORD:
                mqService = updatePasswordMQServiceImpl;
                break;
            case RESET_PASSWORD:
                mqService = updatePasswordMQServiceImpl;
                break;
            case FORGET_PASSWORD:
                mqService = updatePasswordMQServiceImpl;
                break;**/
            default:
                break;
        }
        return mqService;
    }
}
