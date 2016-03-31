/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportDao.java  <br/>
 * Package Name:com.zizaike.passport.dao  <br/>
 * Date:2016年3月28日下午2:26:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.dao;

import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.passport.Passport;

/**
 * ClassName:PassportDao <br/>
 * Function: USER 相关. <br/>
 * Date: 2016年3月28日 下午2:26:14 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface PassportDao {
    /**
     * 保存passport
     * 
     * @param passport
     */
    @Master
    public void save(Passport passport);

    /**
     * 
     * findPassport:通过用户名和密码查询passport. <br/>
     * 
     * @author snow.zhang
     * @param userId
     * @return
     * @since JDK 1.7
     */
    @Slave
    public Passport findPassport(Integer userId);

    /**
     * 
     * updateStatus:更新用户状态. <br/>
     * 
     * @author snow.zhang
     * @param status
     * @param id
     * @return
     * @since JDK 1.7
     */
    @Master
    public Integer updateStatus(Integer status, Integer id);

    /**
     * 
     * updatePassword:更新密码. <br/>
     * 
     * @author snow.zhang
     * @param hash
     * @param salt
     * @param id
     * @return
     * @since JDK 1.7
     */
    @Master
    public Integer updatePassword ( String hash, String salt, Integer userId);
    
    /**
     * 
     * setPlaintext:设置密码为明文. <br/>  
     *  
     * @author snow.zhang  
     * @param id  
     * @since JDK 1.7
     */
    @Master
    public void setPlaintext(Integer userId);

    /**
     * 
     * setCiphertext:设置密码为密文. <br/>  
     *  
     * @author snow.zhang
     * @param id  
     * @since JDK 1.7
     */
    @Master
    public void setCiphertext(Integer userId);
}
