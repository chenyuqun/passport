/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasService.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月24日下午3:43:51  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service;  

import java.util.List;

import org.springframework.stereotype.Service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.Tlas;

/**  
 * ClassName:TlasService <br/>  
 * Function: 顺延值服务 . <br/>  
 * Date:     2016年3月24日 下午3:43:51 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface TlasService {
    /**
     * tlas表中默认的数据条数
     */
    public static final int TLAS_COUNT = 1000;
    /**
     * 
     * findAll:查询所有. <br/>  
     * 
     * @author snow.zhang  
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    List<Tlas> findAll () ;
    /**
     * 
     * getSalt:根据salt的对应值，获取对应的salt. <br/>  
     *  
     * @author snow.zhang  
     * @param saleRef
     * @return  
     * @since JDK 1.7
     */
    public String getSalt(String saleRef) ;
    /**
     * 
     * getRandomSalt:返回salt的唯一标识，即tlas_ref的值，在map中为键值对. <br/>  
     *  
     * @author snow.zhang  
     * @param ip
     * @return  
     * @since JDK 1.7
     */
    public String getRandomSalt(String ip) ;
}
  
