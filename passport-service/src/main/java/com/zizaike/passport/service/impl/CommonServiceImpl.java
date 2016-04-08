/**  
 * Project Name:passport-service  <br/>
 * File Name:CommonServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月24日下午3:48:07  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.zizaike.core.framework.exception.passport.EmailAlreadlyExistException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.IPFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileAlreadlyExistException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordIncorrectException;
import com.zizaike.core.framework.exception.passport.UserNotExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.Activation;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.PassportStatus;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.LoginVo;
import com.zizaike.entity.passport.domain.vo.RegisterVo;
import com.zizaike.passport.service.CommonService;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.service.TlasService;
import com.zizaike.passport.service.UserService;
import com.zizaike.passport.util.PassportUtil;

/**
 * ClassName:UserServiceImpl <br/>
 * Date: 2016年3月24日 下午3:48:07 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
@Service
public class CommonServiceImpl implements CommonService {
    private static final Logger LOG = LoggerFactory.getLogger(CommonServiceImpl.class);
    @Autowired
    private PassportService passportService;
    @Autowired
    private UserService userService;
    @Autowired
    private TlasService tlasService;

    @Override
    public PassportResult register(RegisterVo registerVo) throws ZZKServiceException {

        long start = System.currentTimeMillis();
        // 非法IP,无法获取IP的情况,均设为默认IP
        if (!CommonUtil.isIp(registerVo.getIp())) {
            LOG.info("register ip error, but can register, mobile={}, ip={}, defaultIp={}", registerVo.getUsername(),
                    registerVo.getIp(), Constant.IP_DEFAULT);
            registerVo.setIp(Constant.IP_DEFAULT);
        }
        PassportResult passportResult = null;
        // 注册接口，密码必须做32位MD5
        if (!PassportUtil.isPasswordFormatCorrect(registerVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        if(registerVo.getRegisterType()==null){
            throw new IllegalParamterException(" register registerType is not null");
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
                throw new MobileAlreadlyExistException();
            }
        } else if (RegisterType.EMAIL == registerVo.getRegisterType()) {
            if (userService.isEmailExist(registerVo.getEmail())) {
                throw new EmailAlreadlyExistException();
            }
        }
        User user = buildUser(registerVo);
        userService.save(user);
        Passport passport = buildPassport(user.getUserId(), registerVo.getPassword(), registerVo.getIp());
        passport.setIsFirst(true);
        passport.setUserId(user.getUserId());
        // 一年后过期
        passport.setExpireAt(DateUtil.addYear(new Date(), 1));
        passportService.save(passport);

        // 调用生成SSID的方法
        passportResult = passportService.getSSID(passport);
        long end = System.currentTimeMillis();
        LOG.info("passport register success, userId={} use{}ms", passport.getUserId(), end - start);
        return passportResult;
    }

    /**
     * 
     * buildUser:构建用户. <br/>
     * 
     * @author snow.zhang
     * @param registerVo
     * @return
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
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
     * buildPassport:构建passport. <br/>
     * 
     * @author snow.zhang
     * @param userId
     * @param md5Password
     * @param ip
     * @return
     * @since JDK 1.7
     */
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
    public PassportResult login(LoginVo loginVo) throws ZZKServiceException {

        if (loginVo == null) {
            throw new IllegalParamterException("login loginVo is not null");
        }
        if (loginVo.getLoginType() == null) {
            throw new IllegalParamterException("login loginType is not null");
        }
        long start = System.currentTimeMillis();
        if (!CommonUtil.isIp(loginVo.getIp())) {
            LOG.info("login ip error, but can login, username={},mobile{},email{}, ip={}, defaultIp={}",
                    loginVo.getUserName(), loginVo.getMobile(), loginVo.getEmail(), loginVo.getIp(),
                    Constant.IP_DEFAULT);
            loginVo.setIp(Constant.IP_DEFAULT);
        }
        PassportResult passportResult = null;

        if (!CommonUtil.isPasswordFormatCorrect(loginVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        User user = null;
        if (loginVo.getLoginType() == LoginType.EMAIL_LOGIN) {
            user = userService.findByEmail(loginVo.getEmail());
        } else if (loginVo.getLoginType() == LoginType.MOBILE_LOGIN) {
            user = userService.findByMobile(loginVo.getMobile());
        } else if (loginVo.getLoginType() == LoginType.USER_NAME_LOGIN) {
            user = userService.findByUserName(loginVo.getUserName());
        }
        if (user == null) {
            throw new UserNotExistException();
        }
        Passport passport = passportService.findPassport(user.getUserId());
        passport.setLoginType(loginVo.getLoginType());
        passport.setIsFirst(false);
        String hash = CommonUtil.generateHash(loginVo.getPassword(), tlasService.getSalt(passport.getSalt()));
        if (StringUtils.isNotEmpty(passport.getHash()) && !passport.getHash().equals(hash)) {
            LOG.info("login failed incorrect password, userId={} ", passport.getUserId());
            throw new PasswordIncorrectException();
        }
        // 保护性copy，防止外部接口直接使用原对象
        Passport ssidPassport = new Passport();
        try {
            BeanUtils.copyProperties(ssidPassport, passport);
        } catch (Exception e) {
            LOG.error("copy passport is error", e);
        }

        // 调用生成SSID的方法
        passportResult = passportService.getSSID(ssidPassport);
        LOG.info("passport login success, userId={}, use {}ms", passportResult.getPassport().getUserId(),
                System.currentTimeMillis() - start);
        LOG.info("passport login success, userId={}, use {}ms", passportResult.getPassport().getUserId(),
                System.currentTimeMillis() - start);
        return passportResult;
    }

}
