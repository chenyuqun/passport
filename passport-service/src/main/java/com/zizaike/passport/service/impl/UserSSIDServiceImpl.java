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
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.redis.passport.SSIDRedisService;
import com.zizaike.passport.dao.UserSSIDDao;
import com.zizaike.passport.entity.UserSSID;
import com.zizaike.passport.service.UserSSIDService;

/**
 * 
 * ClassName: LoginLogServiceImpl <br/>
 * Function: user ssid 服务. <br/>
 * date: 2016年4月5日 下午9:20:17 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
@Service
public class UserSSIDServiceImpl implements UserSSIDService {
    private static final Logger LOG = LoggerFactory.getLogger(UserSSIDServiceImpl.class);
    @Autowired
    private UserSSIDDao userSSIDDao;
    @Autowired
    private SSIDRedisService SSIDRedisService;

    @Override
    public List<UserSSID> findByUserId(Integer userId) throws IllegalParamterException {
        if (userId == null || userId <= 0) {
            throw new IllegalParamterException("findByUserId userId is not null");
        }
        return userSSIDDao.findByUserId(userId);
    }

    @Override
    public void save(UserSSID userSSID) throws IllegalParamterException {

        if (userSSID == null) {
            throw new IllegalParamterException("save  userSSID is not null");
        }
        if (userSSID.getUserId() == null || userSSID.getUserId() <= 0) {
            throw new IllegalParamterException("save  userId is not null");
        }
        if (StringUtils.isEmpty(userSSID.getSsid())) {
            throw new IllegalParamterException("save  ssid is not null");
        }
        userSSID.setCreateAt(new Date());
        userSSID.setUpdateAt(new Date());
        userSSID.setActive(1);
        userSSIDDao.save(userSSID);

    }

    @Override
    public void deleteSSID(String SSID) throws ZZKServiceException {

        if (StringUtils.isEmpty(SSID)) {
            throw new IllegalParamterException("deleteSSID  ssid is not null");
        }
        SSIDRedisService.delPassport(SSID);
        userSSIDDao.deleteSSID(SSID);
    }

    @Override
    public void deleteByUserId(Integer userId) throws ZZKServiceException {
        long start = System.currentTimeMillis();
        if (userId == null || userId <= 0) {
            throw new IllegalParamterException("deleteByUserId userId is not null");
        }
        List<UserSSID> list = findByUserId(userId);
        //删除缓存
        for (UserSSID userSSID : list) {
            SSIDRedisService.delPassport(userSSID.getSsid());
        }
        userSSIDDao.deleteByUserId(userId);
        LOG.info("userSSID deleteByUserId success, userId={} use{}ms", userId, System.currentTimeMillis() - start);
    }

}
