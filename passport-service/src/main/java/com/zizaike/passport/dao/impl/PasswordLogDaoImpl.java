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

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.passport.dao.PasswordLogDao;
import com.zizaike.passport.entity.PasswordLog;

/**
 * ClassName:UserDaoImpl <br/>
 * Function: password日志服务. <br/>
 * Date: 2016年3月26日 下午1:31:49 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Repository
public class PasswordLogDaoImpl extends GenericMyIbatisDao<PasswordLog, Integer> implements PasswordLogDao {
    private static final String NAMESPACE = "com.zizaike.passport.dao.PasswordLogMapper.";

    @Override
    public void save(PasswordLog passwordLog) {
        this.getSqlSession().insert(NAMESPACE + "save", passwordLog);
    }

   

}
