/**  
 * Project Name:passport-service  <br/>
 * File Name:RegisterServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月25日下午8:23:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.common.CommonUtils;
import com.zizaike.core.common.util.date.DateUtil;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.IPFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.UserAlreadlyExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.Activation;
import com.zizaike.entity.passport.domain.PassportStatus;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.RegisterVo;
import com.zizaike.is.passport.RegisterService;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.service.TlasService;
import com.zizaike.passport.service.UserService;
import com.zizaike.passport.util.PassportUtil;

/**
 * ClassName:RegisterServiceImpl <br/>
 * Function: 注册服务 <br/>
 * Date: 2016年3月25日 下午8:23:43 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private TlasService tlasService;
    @Autowired
    private PassportService passportService;

    @Override
    public PassportResult registerPassport(RegisterVo registerVo, String extend) throws ZZKServiceException {
        long start = System.currentTimeMillis();
        // 非法IP,无法获取IP的情况,均设为默认IP
        if (!CommonUtil.isIp(registerVo.getIp())) {
            LOG.info("register ip error, but can register, mobile={}, ip={}, defaultIp={}", registerVo.getUsername(),
                    registerVo.getIp(), Constant.IP_DEFAULT);
            registerVo.setIp(Constant.IP_DEFAULT);
        }
        // 注册接口，密码必须做32位MD5
        if (!PassportUtil.isPasswordFormatCorrect(registerVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        /**
         * 注册参数正则校验
         */
        validRegisterParams(registerVo);
        /**
         * 用户存在校验
         */
        if (RegisterType.MOBILE == registerVo.getRegisterType()) {
            if (userService.isMobileExist(registerVo.getMobile())) {
                throw new UserAlreadlyExistException();
            }
        } else if (RegisterType.EMAIL == registerVo.getRegisterType()) {
            if (userService.isEmailExist(registerVo.getEmail())) {
                throw new UserAlreadlyExistException();
            }
        }
        User user = buildUser(registerVo);
        userService.save(user);
        Passport passport = buildPassport(user.getUserId(), registerVo.getPassword(), registerVo.getIp());
        passport.setIsFirst(true);
        passport.setUserId(user.getUserId());
        //一年后过期
        passport.setExpireAt(DateUtil.addYear(new Date(), 1));
        passportService.save(passport);

        // 调用生成SSID的方法
        PassportResult passportResult = passportService.getSSID(registerVo.getChannelType(), passport);
        long end = System.currentTimeMillis();
        LOG.info("passport register success, userId={} use{}ms", passport.getUserId(), end - start);
        return passportResult;
    }

    private Passport buildPassport(Integer userId, String md5Password, String ip) {
        // salt为顺延值的key
        String salt = tlasService.getRandomSalt(ip);
        String hash = CommonUtil.generateHash(md5Password, tlasService.getSalt(salt));
        Passport passport = new Passport();
        passport.setUserId(userId);
        passport.setSalt(salt);
        passport.setHash(hash);
        Date nowDate = new Date();
        passport.setCreateAt(nowDate);
        passport.setUpdateAt(nowDate);
        passport.setStatus(PassportStatus.AVAILABLE);
        return passport;
    }

    private User buildUser(RegisterVo registerVo) throws ZZKServiceException {
        User user = new User();
        user.setUserName(registerVo.getUsername());
        user.setActive(1);
        user.setChannel(registerVo.getChannelType());
        user.setMobile(registerVo.getMobile());
        user.setEmail(registerVo.getEmail());
        user.setActivation(Activation.NO);
        user.setCreateAt(new Date());
        user.setIntegral(0);
        user.setLoginCount(1);
        user.setPassword(registerVo.getPassword());
        user.setRegisterIP(registerVo.getIp());
        user.setUpdateAt(new Date());
        user.setUserType(registerVo.getUserType());
        return user;
    }

    /**
     * 
     * validRegisterParams:验证注册参数合法性. <br/>
     * 
     * @author snow.zhang
     * @param registerVo
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
    private void validRegisterParams(RegisterVo registerVo) throws ZZKServiceException {

        if (registerVo.getChannelType() == null) {
            throw new IllegalParamterException("register channelType is not null");
        }
        if (registerVo.getRegisterType() == null) {
            throw new IllegalParamterException("registerType is null");
        }
        if (registerVo.getRegisterType() == RegisterType.MOBILE) {
            if (!CommonUtils.isMobile(registerVo.getMobile())) {
                throw new MobileFormatIncorrectException();
            }
        } else if (registerVo.getRegisterType() == RegisterType.EMAIL) {
            if (!CommonUtils.isEmail(registerVo.getEmail())) {
                throw new EmailFormatIncorrectException();
            }
        }
        if (!CommonUtil.isIp(registerVo.getIp())) {
            throw new IPFormatIncorrectException();
        }
    }

    @Override
    public Boolean isMobileExist(String mobile) throws MobileFormatIncorrectException {
        if (!CommonUtils.isMobile(mobile)) {
            throw new MobileFormatIncorrectException();
        }
        return userService.isMobileExist(mobile);
    }

    @Override
    public Boolean isEmailExist(String email) throws EmailFormatIncorrectException {
        if (!CommonUtils.isMobile(email)) {
            throw new EmailFormatIncorrectException();
        }
        return userService.isEmailExist(email);
    }
}
