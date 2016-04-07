/**  
 * Project Name:passport-service  <br/>
 * File Name:UserDaoImpl.java  <br/>
 * Package Name:com.zizaike.passport.dao.impl  <br/>
 * Date:2016年3月26日下午1:31:49  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.passport.dao.UserSSIDDao;
import com.zizaike.passport.entity.UserSSID;

/**
 * ClassName:UserDaoImpl <br/>
 * Function: userSSID dao. <br/>
 * Date: 2016年3月26日 下午1:31:49 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Repository
public class UserSSIDDaoImpl extends GenericMyIbatisDao<UserSSID, Integer> implements UserSSIDDao {
    private static final String NAMESPACE = "com.zizaike.passport.dao.UserSSIDMapper.";

    @Override
    public List<UserSSID> findByUserId(Integer userId) {
        return this.getSqlSession().selectList(NAMESPACE + "findByUserId", userId);
    }

    @Override
    public void save(UserSSID userSSID) {
        this.getSqlSession().insert(NAMESPACE + "insertSelective", userSSID);

    }

    @Override
    public void deleteSSID(String SSID) {
        this.getSqlSession().update(NAMESPACE + "deleteSSID", SSID);
    }

    @Override
    public void deleteByUserId(Integer userId) {

        this.getSqlSession().update(NAMESPACE + "deleteByUserId", userId);

    }

}
