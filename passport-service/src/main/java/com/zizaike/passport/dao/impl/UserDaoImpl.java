/**  
 * Project Name:passport-service  <br/>
 * File Name:UserDaoImpl.java  <br/>
 * Package Name:com.zizaike.passport.dao.impl  <br/>
 * Date:2016年3月26日下午1:31:49  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.dao.impl;  

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.passport.Tlas;
import com.zizaike.entity.passport.User;
import com.zizaike.passport.dao.UserDao;

/**  
 * ClassName:UserDaoImpl <br/>  
 * Function: 用户服务. <br/>  
 * Date:     2016年3月26日 下午1:31:49 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Repository
public class UserDaoImpl extends GenericMyIbatisDao<User, Integer> implements UserDao {
    private static final String NAMESPACE = "com.zizaike.passport.dao.UserMapper.";
    @Override
    public Boolean isMobileExist(String mobile) {
        User user = this.getSqlSession().selectOne(NAMESPACE+"selectByMobile", mobile);
        if(user==null){
            return false;
        }
        return true;
    }

    @Override
    public Boolean isEmailExist(String email) {
        User user = this.getSqlSession().selectOne(NAMESPACE+"selectByEmail", email);
        if(user==null){
            return false;
        }
        return true;
    }

    @Override
    public void save(User user) {
        this.getSqlSession().selectOne(NAMESPACE+"save", user);
    }

    @Override
    public User findByMobile(String mobile) throws ZZKServiceException {
          
        User user = this.getSqlSession().selectOne(NAMESPACE+"selectByMobile", mobile);
        return user;
    }

}
  
