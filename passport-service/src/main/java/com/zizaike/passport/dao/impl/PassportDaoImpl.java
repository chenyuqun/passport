/**  
 * Project Name:passport-service  <br/>
 * File Name:PassportDaoImpl.java  <br/>
 * Package Name:com.zizaike.passport.dao.impl  <br/>
 * Date:2016年3月28日下午3:26:50  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.passport.Passport;
import com.zizaike.passport.dao.PassportDao;

/**
 * ClassName:PassportDaoImpl <br/>
 * Function: passport. <br/>
 * Date: 2016年3月28日 下午3:26:50 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Repository
public class PassportDaoImpl extends GenericMyIbatisDao<Passport, Integer> implements PassportDao {
    private static final String NAMESPACE = "com.zizaike.passport.dao.PassportMapper.";

    @Override
    public void save(Passport passport) {
        this.getSqlSession().insert(NAMESPACE + "insertSelective", passport);
    }

    @Override
    public Passport findPassport(Integer userId) {
        return this.getSqlSession().selectOne(NAMESPACE + "selectByUserId", userId);
    }

    @Override
    public Integer updateStatus(Integer status, Integer id) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("id", id);

        return this.getSqlSession().update(NAMESPACE + "updateStatus", map);
    }

    @Override
    public Integer updatePassword(Passport passport) {

        return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKeySelective", passport);
    }

    @Override
    public void setPlaintext(Integer userId) {
        Passport passport = new Passport();
        passport.setPlaintext(true);
        passport.setUserId(userId);
        this.getSqlSession().update(NAMESPACE + "updatePlaintext", passport);
    }

    @Override
    public void setCiphertext(Integer userId) {
        Passport passport = new Passport();
        passport.setPlaintext(false);
        passport.setUserId(userId);
        this.getSqlSession().update(NAMESPACE + "updatePlaintext", passport);

    }

}
