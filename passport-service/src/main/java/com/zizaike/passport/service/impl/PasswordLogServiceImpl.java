/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月28日下午6:21:44  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.passport.dao.PasswordLogDao;
import com.zizaike.passport.entity.PasswordLog;
import com.zizaike.passport.service.PasswordLogService;

/**
 * 
 * ClassName: LoginLogServiceImpl <br/>  
 * Function: password日志服务. <br/>  
 * date: 2016年4月5日 下午9:20:17 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Service
public class PasswordLogServiceImpl implements PasswordLogService {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordLogServiceImpl.class);
    @Autowired
    private PasswordLogDao passwordLogDao;
    @Override
    public void success(PasswordLog passwordLog) throws IllegalParamterException{
        if(passwordLog==null){
            throw new IllegalParamterException(" passwordLog is not null");
        }
        if(passwordLog.getUserId()==null || passwordLog.getUserId()<=0){
            throw new IllegalParamterException(" passwordLog userId value is not <=0 ");
        }
        if(passwordLog.getChannel()==null){
            throw new IllegalParamterException(" passwordLog channel is not null");
        }
        passwordLog.setStatus(OperateStatus.SUCCESS);
        passwordLogDao.save(passwordLog);
        
    }
    @Override
    public void failure(PasswordLog passwordLog) throws IllegalParamterException{
        if(passwordLog==null){
            throw new IllegalParamterException(" passwordLog is not null");
        }
        if(passwordLog.getChannel()==null){
            throw new IllegalParamterException(" passwordLog channel is not null");
        }
        if(StringUtils.isEmpty(passwordLog.getReason())){
            throw new IllegalParamterException(" passwordLog reason is not null");
        }
        if(StringUtils.isEmpty(passwordLog.getErrorCode())){
            throw new IllegalParamterException(" passwordLog errorCode is not null");
        }
        passwordLog.setStatus(OperateStatus.FAILURE);
        passwordLogDao.save(passwordLog);
        
    }


  
}
