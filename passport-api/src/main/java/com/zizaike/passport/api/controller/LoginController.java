/**  
 * Project Name:passport-api  <br/>
 * File Name:RegisterController.java  <br/>
 * Package Name:com.zizaike.passport.api.controller  <br/>
 * Date:2016年3月31日上午10:10:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.api.controller;  

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.passport.api.BaseAjaxController;

/**  
 * ClassName:RegisterController <br/>  
 * Function: 密码服务. <br/>  
 * Date:     2016年3月31日 上午10:10:14 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Controller
@RequestMapping("/passport/login")
public class LoginController  extends BaseAjaxController {
    /**
     * 
     * login:登陆. <br/>  
     *  
     * @author snow.zhang  
     * @param email
     * @param password
     * @param ip
     * @param channelType
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "emailLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(String email,String password,String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        return result;
    }
}
  
