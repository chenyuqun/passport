package com.zizaike.passport.mq;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.domain.event.PassportApplicationEvent;

/**
 * ClassName:UserService <br/>
 * Function: 发送消息服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface MQService {
   /**
    * 
    * sendMsg:发送消息. <br/>  
    *  
    * @author snow.zhang  
    * @param event  
    * @since JDK 1.7
    */
    void sendMsg(PassportApplicationEvent event) throws ZZKServiceException;
}
