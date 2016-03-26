/**  
 * Project Name:passport-service  <br/>
 * File Name:UserService.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月26日下午1:20:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.entity.passport.User;

/**  
 * ClassName:UserService <br/>  
 * Function: 用户服务. <br/>  
 * Date:     2016年3月26日 下午1:20:43 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface UserService {
    /**
     * 检查手机是否存在
     * @param username
     * @return
     */
    public Boolean isMobileExist(String mobile) throws MobileFormatIncorrectException;
    /**
     * 检查邮箱是否存在
     * @param username
     * @return
     */
    public Boolean isEmailExist(String email) throws EmailFormatIncorrectException;
    /**
     * 
     * save:保存用户. <br/>  
     *  
     * @author snow.zhang  
     * @param user  
     * @since JDK 1.7
     */
    public void save(User user) throws ZZKServiceException;
    /**
     * 
     * findByMobile:通过手机号找用户. <br/>  
     *  
     * @author snow.zhang  
     * @param Mobile
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    public User findByMobile(String mobile) throws ZZKServiceException;
    
}
  
