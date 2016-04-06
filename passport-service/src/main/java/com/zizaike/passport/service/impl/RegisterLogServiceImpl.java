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
import com.zizaike.passport.dao.RegisterLogDao;
import com.zizaike.passport.entity.RegisterLog;
import com.zizaike.passport.service.RegisterLogService;

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
public class RegisterLogServiceImpl implements RegisterLogService {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterLogServiceImpl.class);
    @Autowired
    private RegisterLogDao registerLogDao;
    @Override
    public void success(RegisterLog registerLog) throws IllegalParamterException{
        if(registerLog==null){
            throw new IllegalParamterException(" registerLog is not null");
        }
        if(registerLog.getUserId()==null || registerLog.getUserId()<=0){
            throw new IllegalParamterException(" registerLog userId value is not <=0 ");
        }
        if(registerLog.getRegisterType()==null){
            throw new IllegalParamterException(" registerLog registerType is not null");
        }
        if(registerLog.getChannel()==null){
            throw new IllegalParamterException(" registerLog channel is not null");
        }
        registerLog.setStatus(OperateStatus.SUCCESS);
        registerLogDao.save(registerLog);
        
    }
    @Override
    public void failure(RegisterLog registerLog) throws IllegalParamterException{
          
        if(registerLog==null){
            throw new IllegalParamterException(" registerLog is not null");
        }
        if(registerLog.getRegisterType()==null){
            throw new IllegalParamterException(" registerLog registerType is not null");
        }
        if(registerLog.getChannel()==null){
            throw new IllegalParamterException(" registerLog channel is not null");
        }
        registerLog.setStatus(OperateStatus.FAILURE);
        registerLogDao.save(registerLog);
        
    }


  
}
