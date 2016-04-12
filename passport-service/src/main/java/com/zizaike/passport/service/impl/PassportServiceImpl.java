/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月28日下午6:21:44  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.date.DateUtil;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.SSIDAuthenticationException;
import com.zizaike.core.framework.exception.passport.UserNotExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.is.redis.passport.SSIDRedisService;
import com.zizaike.passport.dao.PassportDao;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.util.SSIDUtil;

/**
 * ClassName:PassportServiceImpl <br/>
 * Function: passport. <br/>
 * Date: 2016年3月28日 下午6:21:44 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class PassportServiceImpl implements PassportService {
    private static final Logger LOG = LoggerFactory.getLogger(PassportServiceImpl.class);
    @Autowired
    private PassportDao passportDao;
    @Autowired
    private SSIDRedisService ssidRedisService;

    @Override
    public void save(Passport passport) throws ZZKServiceException{
        if (passport == null) {
            throw new IllegalParamterException("passport is not null");
        }
        if (StringUtils.isEmpty(passport.getHash())) {
            throw new IllegalParamterException("passport hash is not null");
        }
        if (StringUtils.isEmpty(passport.getSalt())) {
            throw new IllegalParamterException("passport salt is not null");
        }
        if (passport.getUserId()<=0) {
            throw new IllegalParamterException("passport userId is not <=0");
        }
        long start = System.currentTimeMillis();
        passport.setCreateAt(new Date());
        passport.setUpdateAt(new Date());
        passportDao.save(passport);
        long end = System.currentTimeMillis();
        LOG.info("save passport, userID={}, use {}ms", passport.getUserId(), end - start);

    }

    @Override
    public void checkSSID(String SSID) throws ZZKServiceException{
        Passport passport= ssidRedisService.getPassport(SSID);
        if(passport==null){
            throw new SSIDAuthenticationException();
        }
    }

    @Override
    public PassportResult getSSID(Passport passport) throws ZZKServiceException{
        
        if (passport == null) {
            throw new IllegalParamterException("passport is null");
        }
        //如果做随机SSIDKey时，将下面的注释信息解开
        StringBuffer SSIDBuffer = new StringBuffer();
        SSIDBuffer.append(passport.getUserId()).append(",");
        SSIDBuffer.append(System.currentTimeMillis() + Constant.SSID_EXP * 1000l);
        String SSIDKey = SSIDUtil.SSID_KEY;
        
        String SSID = SSIDUtil.encrypt(SSIDBuffer.toString(), SSIDKey);
        // 给页面对应生成的临时passport设置passportId，并且将密码相关字段清空，然后将passport对象保存进redis，并且传回前台
        
        passport.setHash(null);
        passport.setSalt(null);
        
        ssidRedisService.setPassport(SSID, passport, Constant.SSID_EXP);
        
        PassportResult result = new PassportResult();
        result.putToExpends("SSID", SSID);
        result.setPassport(passport);
        return result;
    }


    @Override
    public void updatePasspword(Integer userId, String hash, String salt) throws ZZKServiceException{

        long start = System.currentTimeMillis();
        if(userId==null || userId<=0 ){
            throw new IllegalParamterException("userId is null or <=0)");
        }
        if(StringUtils.isBlank(hash) || StringUtils.isBlank(salt)){
            throw new IllegalParamterException("userId is format error and (hash is null or salt is null)");
        }
        Passport passport = passportDao.findPassport(userId); 
        if(passport == null){
            throw new UserNotExistException();
        }
        Passport passportUpdate = new Passport();
        passportUpdate.setHash(hash);
        // 一年后过期
        passportUpdate.setExpireAt(DateUtil.addYear(new Date(), 1));
        passportUpdate.setSalt(salt);
        passportUpdate.setUserId(userId);
        passportUpdate.setPlaintext(false);
        passportDao.updatePassword(passportUpdate);
        LOG.info("updatePassword, userID={},  hash={}, use={}ms",  userId, hash, System.currentTimeMillis() - start);;

    }

    @Override
    public Passport findPassport(Integer userId) throws ZZKServiceException{
        if(userId==null || userId<=0 ){
            throw new IllegalParamterException("userId is null or <=0)");
        }
        Passport passport = passportDao.findPassport(userId); 
        return passport;
    }

}
