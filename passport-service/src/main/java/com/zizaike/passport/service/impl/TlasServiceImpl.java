/**  
 * Project Name:passport-service  <br/>
 * File Name:TlasServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月24日下午3:48:07  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.service.impl;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.encrypt.Digests;
import com.zizaike.core.common.util.ip.IpUtil;
import com.zizaike.entity.passport.Tlas;
import com.zizaike.passport.dao.TlasDao;
import com.zizaike.passport.service.TlasService;

/**  
 * ClassName:TlasServiceImpl <br/>  
 * Date:     2016年3月24日 下午3:48:07 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Service
public class TlasServiceImpl implements TlasService {
    /**
     * 存储saltMap
     */
    private static Map<String, String> saltMap = null;
    @Autowired
    private TlasDao tlasDao;
    private void initSaltMap() {

        // 判断saltMap是否存在
        if (saltMap != null && saltMap.size() > 0) {
            return;
        }
        // 判断数据库库中是否存在数据
        int base = 1024;
        initTlasDataBase(base);
        saltMap = tlasToMap(tlasDao.findAll());
    }
    private Map<String, String> tlasToMap(List<Tlas> list) {
        Map<String, String> map = new HashMap<String, String>();
        for (Tlas tlas : list) {
            map.put(tlas.getTlasRef(), saltDigestHexString(tlas.getTlasOne(), tlas.getTlasTwo()));
        }

        return map;
    }
    private String saltDigestHexString(String salt1, String salt2) {
        return CommonUtil.bytes2HexString(Digests.sha256(
                (CommonUtil.bytes2HexString(Digests.sha256(salt1.getBytes(), 2)) + CommonUtil.bytes2HexString(Digests
                        .sha256(salt2.getBytes(), 3))).getBytes(), 2));
    }
    private synchronized void initTlasDataBase(int base) {
        if (tlasDao.countAll() != 0) {
            return;
        }

        // 数据库中没有数据
        int nowbase;
        List<Tlas> tlasList = new ArrayList<Tlas>();
        for (int i = 0; i < TLAS_COUNT; i++) {
            nowbase = base + i;
            Tlas tlas = new Tlas();
            tlas.setTlasOne(Digests.generateSalt(nowbase, 12));
            tlas.setTlasTwo(Digests.generateSalt(nowbase + 1, 12));
            tlas.setTlasRef(Digests.generateSalt(nowbase + 2, 12));
            tlasList.add(tlas);
        }
        tlasDao.insertBatch(tlasList);
    }
    
    @Override
    public List<Tlas> findAll() {

        initSaltMap();
        List<Tlas> list = tlasDao.findAll();
        return list;
    }

    @Override
    public String getSalt(String saleRef) {

        if (saleRef == null) {
            return null;
        }
        // 先进行一次判断，防止saltMap有问题
        initSaltMap();
        return saltMap.get(saleRef);
    }

    @Override
    public String getRandomSalt(String ip) {
        // 初始化map （包括map的整体判断）
        if (!CommonUtil.isIp(ip)) {
            return null;
        }
        initSaltMap();

        long subscript = IpUtil.ipToLong(ip);
        return (String) saltMap.keySet().toArray()[(int) (subscript % TLAS_COUNT)];
    }
}
  
