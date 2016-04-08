package com.zizaike.passport.service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.passport.entity.PasswordLog;

/**
 * ClassName:UserService <br/>
 * Function: password日志服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface PasswordLogService {
    /**
     * 
     * success:成功保存password日志. <br/>  
     *  
     * @author snow.zhang  
     * @param passwordLog  
     * @since JDK 1.7
     */
    void success(PasswordLog passwordLog) throws IllegalParamterException;
    /**
     * 
     * failure:失败保存password日志. <br/>  
     *  
     * @author snow.zhang  
     * @param passwordLog  
     * @since JDK 1.7
     */
    void failure(PasswordLog passwordLog)  throws IllegalParamterException;
}
