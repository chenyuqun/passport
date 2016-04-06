package com.zizaike.passport.dao;

import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.passport.entity.RegisterLog;

/**
 * ClassName:UserService <br/>
 * Function: 注册日志服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface RegisterLogDao {
    /**
     * 
     * save:保存注册日志. <br/>  
     *  
     * @author snow.zhang  
     * @param loginLog  
     * @since JDK 1.7
     */
    @Master
    void save(RegisterLog registerLog);
}
