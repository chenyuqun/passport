/**  
 * Project Name:passport-api  <br/>
 * File Name:RegisterController.java  <br/>
 * Package Name:com.zizaike.passport.api.controller  <br/>
 * Date:2016年3月31日上午10:10:14  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.passport.api.controller;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.common.util.MD5Util;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.is.passport.PasswordService;
import com.zizaike.is.passport.RegisterService;
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
@RequestMapping("/passport/password")
public class PasswordController  extends BaseAjaxController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private PasswordService passwordService;
    /**
     * 
     * md5:.md5 <br/>  
     *  
     * @author snow.zhang  
     * @param text
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "md5", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult md5(String text) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        String md5 = MD5Util.MD5Encode(text,"UTF-8");
        result.setInfo(md5);
        return result;
    }
    /**
     * 
     * updatePassword:更新密码. <br/>  
     *  
     * @author snow.zhang  
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param ip
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updatePassword(Integer userId, String oldPassword, String newPassword, String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        passwordService.updatePassword(userId, oldPassword, newPassword, ip,channelType);
        return result;
    }
    @RequestMapping(value = "reset", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult resetPassword(Integer userId, String password, String ip,ChannelType channelType) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        passwordService.resetPassword(userId,  password, ip,channelType);
        return result;
    }
}
  
