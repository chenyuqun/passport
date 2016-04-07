/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportService.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月28日下午5:53:48  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;

/**  
 * ClassName:PassportService <br/>  
 * Function: 用户护照. <br/>  
 * Date:     2016年3月28日 下午5:53:48 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface PassportService {
    /**
     * 保存用户
     * @param passport
     */
    public void save(Passport passport) throws ZZKServiceException;
    /**
     * 第一次登录前验证SSID合法性
     * @param SSID
     * @return
     */
    public void checkSSID(String SSID)throws ZZKServiceException;
    /**
     * 
     * getSSID:得到SSID. <br/>  
     *  
     * @author snow.zhang  
     * @param passport
     * @return  
     * @since JDK 1.7
     */
    public PassportResult getSSID(Passport passport)throws ZZKServiceException;
    /**
     * 
     * updatePasspord:更新密码. <br/>  
     *  
     * @author snow.zhang  
     * @param useId
     * @param hash
     * @param salt  
     * @since JDK 1.7
     */
    public void updatePasspword(Integer useId,String hash,String salt)throws ZZKServiceException;
    /**
     * 
     * findPassword:查找密码. <br/>  
     *  
     * @author snow.zhang  
     * @param userId
     * @return
     * @since JDK 1.7
     */
    public Passport findPassport(Integer userId)throws ZZKServiceException;
    
}
  
