package com.zizaike.passport.dao;

import com.zizaike.core.framework.springext.database.Master;
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
public interface LoginLogDao {
    /**
     * 
     * save:保存登陆日志. <br/>  
     *  
     * @author snow.zhang  
     * @param loginLog  
     * @since JDK 1.7
     */
    @Master
    void save(LoginLog loginLog);
}
