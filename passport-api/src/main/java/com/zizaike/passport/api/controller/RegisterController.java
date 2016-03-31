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
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.RegisterVo.RegisterVoBuilder;
import com.zizaike.is.open.UserService;
import com.zizaike.is.passport.RegisterService;
import com.zizaike.passport.api.BaseAjaxController;

/**  
 * ClassName:RegisterController <br/>  
 * Function: 注册. <br/>  
 * Date:     2016年3月31日 上午10:10:14 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Controller
@RequestMapping("/passport/register")
public class RegisterController  extends BaseAjaxController {
    @Autowired
    private RegisterService registerService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "mobileRegister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerMobile(String mobile,String password ,String ip,ChannelType channelType,String extend) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        RegisterVoBuilder builder = new RegisterVoBuilder(null, mobile, password, ip);
        builder.setChannelType(channelType).setRegisterType(RegisterType.MOBILE);
        result.setInfo(registerService.registerPassport(builder.build(), extend));
        return result;
    }
    @RequestMapping(value = "emailRegister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult registerEmail(String email,String password ,String ip,ChannelType channelType,String extend) throws ZZKServiceException {
        ResponseResult result = new ResponseResult();
        RegisterVoBuilder builder = new RegisterVoBuilder(email, null, password, ip);
        builder.setChannelType(channelType).setRegisterType(RegisterType.EMAIL);
        result.setInfo(registerService.registerPassport(builder.build(), extend));
        return result;
    }
    /**
     * 
     * checkMobile:查看手机是否存在. <br/>  
     *  
     * @author snow.zhang  
     * @param mobile
     * @return  
     * @throws MobileFormatIncorrectException 
     * @since JDK 1.7
     */
    @RequestMapping( value = "checkMobileExist",method = RequestMethod.GET)
    public @ResponseBody ResponseResult checkMobile(String mobile) throws MobileFormatIncorrectException{
        ResponseResult result = new ResponseResult();
        result.setInfo(registerService.isMobileExist(mobile));
        return result;
    }
    /**
     * 
     * checkEmail:查看邮箱是否存在. <br/>  
     *  
     * @author snow.zhang  
     * @param email
     * @return  
     * @throws EmailFormatIncorrectException 
     * @since JDK 1.7
     */
    @RequestMapping(value = "checkEmailExist",method = RequestMethod.GET)
    public @ResponseBody ResponseResult checkEmail(String email) throws EmailFormatIncorrectException{
        ResponseResult result = new ResponseResult();
        result.setInfo(registerService.isEmailExist(email));
        return result;
    }
}
  
