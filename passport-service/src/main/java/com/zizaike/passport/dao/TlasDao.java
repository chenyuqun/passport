/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasDao.java  <br/>
 * Package Name:com.zizaike.passport.dao  <br/>
 * Date:2016年3月24日下午3:20:25  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.dao;  

import java.util.List;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.passport.Tlas;

/**  
 * ClassName:TlasDao <br/>  
 * Date:     2016年3月24日 下午3:20:25 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface TlasDao {
    /**
     * 
     * insertBatch:初始化数据. <br/>  
     *  
     * @author snow.zhang  
     * @param TlasBatch
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Master
    void insertBatch(List<Tlas> tlasList) ;
    /**
     * 
     * findAll:查询所有. <br/>  
     * 
     * @author snow.zhang  
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    List<Tlas> findAll () ;
    /**
     * 
     * countAll:总计. <br/>  
     *  
     * @author snow.zhang  
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    Integer countAll() ;
    
}
  
