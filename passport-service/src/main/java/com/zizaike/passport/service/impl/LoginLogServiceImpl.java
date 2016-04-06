/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月28日下午6:21:44  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.passport.dao.LoginLogDao;
import com.zizaike.passport.entity.LoginLog;
import com.zizaike.passport.service.LoginLogService;

/**
 * 
 * ClassName: LoginLogServiceImpl <br/>  
 * Function: 登陆日志服务. <br/>  
 * date: 2016年4月5日 下午9:20:17 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {
    private static final Logger LOG = LoggerFactory.getLogger(LoginLogServiceImpl.class);
    @Autowired
    private LoginLogDao loginLogDao;
    @Override
    public void success(LoginLog loginLog) throws IllegalParamterException{
        if(loginLog==null){
            throw new IllegalParamterException(" loginLog is not null");
        }
        if(loginLog.getUserId()==null || loginLog.getUserId()<=0){
            throw new IllegalParamterException(" loginLog userId value is not <=0 ");
        }
        if(loginLog.getLoginType()==null){
            throw new IllegalParamterException(" loginLog loginTpe is not null");
        }
        if(loginLog.getChannel()==null){
            throw new IllegalParamterException(" loginLog channel is not null");
        }
        loginLog.setStatus(OperateStatus.SUCCESS);
        loginLogDao.save(loginLog);
        
    }
    @Override
    public void failure(LoginLog loginLog) throws IllegalParamterException{
          
        if(loginLog==null){
            throw new IllegalParamterException(" loginLog is not null");
        }
        if(loginLog.getLoginType()==null){
            throw new IllegalParamterException(" loginLog loginTpe is not null");
        }
        if(loginLog.getChannel()==null){
            throw new IllegalParamterException(" loginLog channel is not null");
        }
        loginLog.setStatus(OperateStatus.FAILURE);
        loginLogDao.save(loginLog);
        
    }


  
}
