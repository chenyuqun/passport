/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月24日下午3:48:07  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service.impl;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.common.CommonUtils;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.UserNameFormatIncorrectException;
import com.zizaike.entity.passport.User;
import com.zizaike.passport.dao.UserDao;
import com.zizaike.passport.service.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Date:     2016年3月24日 下午3:48:07 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Boolean isMobileExist(String mobile) throws MobileFormatIncorrectException {
         if( !CommonUtils.isMobile(mobile)){
             throw new MobileFormatIncorrectException();
         }
        return userDao.isMobileExist(mobile);
    }

    @Override
    public Boolean isEmailExist(String email) throws EmailFormatIncorrectException  {
          
        if( !CommonUtils.isEmail(email)){
            throw new EmailFormatIncorrectException();
        }
       return userDao.isEmailExist(email);
    }

    @Override
    public void save(User user)   {
        if(user==null){
            return;
        }
        long start = System.currentTimeMillis();
        userDao.save(user);
        long end = System.currentTimeMillis();
        LOG.info("save user, mobile={}, userId={}, use {}ms",
                user.getMobile(), user.getUserId(), end - start);
    }

    @Override
    public User findByMobile(String mobile) throws MobileFormatIncorrectException {
          
        if( !CommonUtils.isMobile(mobile)){
            throw new MobileFormatIncorrectException();
        }
        return userDao.findByMobile(mobile);
    }

    @Override
    public Boolean isUserNameExist(String userName) throws UserNameFormatIncorrectException{
        if( !CommonUtils.isUserName(userName)){
            throw new UserNameFormatIncorrectException();
        }
        return userDao.isUserNameExist(userName);
    }

    @Override
    public User findByEmail(String email) throws EmailFormatIncorrectException {
        if( !CommonUtils.isEmail(email)){
            throw new EmailFormatIncorrectException();
        }
        return userDao.findByEmail(email);
    }

    @Override
    public User findByUserName(String userName) throws UserNameFormatIncorrectException {
        if( !CommonUtils.isUserName(userName)){
            throw new UserNameFormatIncorrectException();
        }
        return userDao.findByUserName(userName);
    }

    @Override
    public User findByUserId(Integer userId) {
          
        if( userId==null || userId<=0){
            return null;
        } 
        return userDao.findByUserId(userId);
    }
}
  
