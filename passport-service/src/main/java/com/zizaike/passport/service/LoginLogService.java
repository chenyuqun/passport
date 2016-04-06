package com.zizaike.passport.service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.passport.entity.LoginLog;

/**
 * ClassName:UserService <br/>
 * Function: 登陆日志服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface LoginLogService {
    /**
     * 
     * success:成功保存登陆日志. <br/>  
     *  
     * @author snow.zhang  
     * @param loginLog  
     * @since JDK 1.7
     */
    void success(LoginLog loginLog) throws IllegalParamterException;
    /**
     * 
     * failure:失败保存登陆日志. <br/>  
     *  
     * @author snow.zhang  
     * @param loginLog  
     * @since JDK 1.7
     */
    void failure(LoginLog loginLog)  throws IllegalParamterException;
}
