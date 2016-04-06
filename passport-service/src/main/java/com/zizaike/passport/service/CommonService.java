/**  
 * Project Name:passport-service  <br/>
 * File Name:UserService.java  <br/>
 * Package Name:com.zizaike.passport.service  <br/>
 * Date:2016年3月26日下午1:20:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.vo.LoginVo;
import com.zizaike.entity.passport.domain.vo.RegisterVo;

/**
 * ClassName:CommonService <br/>
 * Function: 基础公共service服务(内部服务). <br/>
 * Date: 2016年3月26日 下午1:20:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public interface CommonService {
    /****
     * <p>
     * 基础注册服务.
     * </p>
     * <p>
     * 此处将验证<br>
     * 1>、注册用户名，注册密码，及注册IP必须<br>
     * 2>、当注册类型为普通注册时，将验证注册用户名为手机或者邮箱<br>
     * 3>、当注册类型为第三方类型时，将验证注册用户名必须以第三方type小写开头<br>
     * 4>、验证注册用户名不能重复<br>
     * 5>、注册密码长度必须介于0-32位之间 6>、注册IP必须有效，无效IP均会设置为默认IP
     * </p>
     * <p>
     * 注册成功后，将会设置两个临时属性，用于描述注册行为 loginType，注册类型 isFirst，是否第一次，主要用于第三方，普通注册均会true， 注册失败,将发送注册失败事件到FDS<br>
     * //废弃 注册成功,将发送注册成功事件到FDS<br>
     * //废弃 作为基础注册服务,将不做注册防攻击检验.
     * </p>
     * 
     * @param registerVo 注册参数
     * @return
     * @throws ZZKServiceException
     */
    public PassportResult register(RegisterVo registerVo) throws ZZKServiceException;

    /**
     * 
     * login:
     * <p>
     * 基础登录服务.
     * </p>
     * 登录失败时,将会发送相关失败事件. passport表和用户表不同步时，会执行同步操作. 第三方用户登录时, 密码不进行校验.. <br/>
     * 
     * @author snow.zhang
     * @param loginVo
     * @return
     * @since JDK 1.7
     */
    PassportResult login(LoginVo loginVo) throws ZZKServiceException;

}
