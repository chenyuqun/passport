package com.zizaike.passport.service;

import java.util.List;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.passport.entity.UserSSID;

/**
 * ClassName:UserService <br/>
 * Function: userssid服务. <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface UserSSIDService {
    /**
     * 
     * findByUserId:查找找到userId的方法. <br/>
     * 
     * @author snow.zhang
     * @param userId
     * @return
     * @since JDK 1.7
     */
    List<UserSSID> findByUserId(Integer userId) throws IllegalParamterException;

    /**
     * 
     * save:保存. <br/>
     * 
     * @author snow.zhang
     * @param userSSID
     * @since JDK 1.7
     */
    void save(UserSSID userSSID) throws IllegalParamterException;
    /**
     * 
     * delete:软删除单个ssid. <br/>  
     *  
     * @author snow.zhang  
     * @param SSID  
     * @since JDK 1.7
     */
    void deleteSSID(String SSID) throws ZZKServiceException;
    /**
     * 
     * deleteByUserId:删除通过userid. <br/>  
     *  
     * @author snow.zhang  
     * @param userId 
     * @since JDK 1.7
     */
    void deleteByUserId(Integer userId) throws ZZKServiceException;
}
