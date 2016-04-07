package com.zizaike.passport.dao;

import java.util.List;

import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.passport.entity.UserSSID;

/**
 * ClassName:UserService <br/>
 * Function: 用户服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface UserSSIDDao {
    /**
     * 
     * findByUserId:查找找到userId的方法. <br/>
     * 
     * @author snow.zhang
     * @param userId
     * @return
     * @since JDK 1.7
     */
    @Slave
    List<UserSSID> findByUserId(Integer userId);

    /**
     * 
     * save:保存. <br/>
     * 
     * @author snow.zhang
     * @param userSSID
     * @since JDK 1.7
     */
    @Master
    void save(UserSSID userSSID);
    /**
     * 
     * delete:软删除单个ssid. <br/>  
     *  
     * @author snow.zhang  
     * @param SSID  
     * @since JDK 1.7
     */
    @Master
    void deleteSSID(String SSID);
    /**
     * 
     * deleteByUserId:删除通过userid. <br/>  
     *  
     * @author snow.zhang  
     * @param userId  
     * @since JDK 1.7
     */
    @Master
    void deleteByUserId(Integer userId);
}
