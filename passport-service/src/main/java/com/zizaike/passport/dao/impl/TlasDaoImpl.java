/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasDaoImpl.java  <br/>
 * Package Name:com.zizaike.passport.dao.impl  <br/>
 * Date:2016年3月24日下午3:31:48  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.passport.Tlas;
import com.zizaike.passport.dao.TlasDao;

/**
 * ClassName:TlasDaoImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2016年3月24日 下午3:31:48 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Repository
public class TlasDaoImpl extends GenericMyIbatisDao<Tlas, Integer> implements TlasDao {
    private static final String NAMESPACE = "com.zizaike.passport.dao.TlasMapper.";

    @Override
    public void insertBatch(List<Tlas> tlasList)  {

        this.getSqlSession().insert(NAMESPACE + "insertBatch", tlasList);
    }

    @Override
    public List<Tlas> findAll()  {
        return this.getSqlSession().selectList(NAMESPACE + "findAll");
    }

    @Override
    public Integer countAll()  {
        return this.getSqlSession().selectOne(NAMESPACE + "countAll");
    }

}
