/**  
 * Project Name:passport-service  <br/>
 * File Name:RegisterServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月25日下午8:23:43  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.common.CommonUtils;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.EmailFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.IPFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.MobileFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.UserType;
import com.zizaike.entity.passport.domain.vo.RegisterVo;
import com.zizaike.is.passport.RegisterService;
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

    @Override
    public PassportResult registerPassport(RegisterVo registerVo, String extend) throws ZZKServiceException {
        long start = System.currentTimeMillis();
        // 非法IP,无法获取IP的情况,均设为默认IP
        if (!CommonUtil.isIp(registerVo.getIp())) {
            LOG.info("register ip error, but can register, mobile={}, ip={}, defaultIp={}", registerVo.getUsername(),
                    registerVo.getIp(), Constant.IP_DEFAULT);
            registerVo.setIp(Constant.IP_DEFAULT);
        }
        validRegisterParams(registerVo);
        PassportResult result = null;
        // 注册接口，密码必须做32位MD5
        if (!PassportUtil.isPasswordFormatCorrect(registerVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        return result;
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

        if (registerVo.getRegisterType() == null) {
            throw new IllegalParamterException("register type is null");
        }

        switch (registerVo.getRegisterType()) {
        case REGISTER:
            if (registerVo.getUserType() == null) {
                throw new IllegalParamterException("userType is null");
            }
            if (registerVo.getUserType() == UserType.CUSTOMER) {
                if (!CommonUtils.isMobile(registerVo.getMobile())) {
                    throw new MobileFormatIncorrectException();
                }
            }else if(registerVo.getUserType() == UserType.BUSINESS) {
                if (!CommonUtils.isMobile(registerVo.getMobile())) {
                    throw new MobileFormatIncorrectException();
                }
                if (!CommonUtils.isMobile(registerVo.getEmail())) {
                    throw new EmailFormatIncorrectException();
                }
            }
            break;
        case LOGIN:
            break;
        case GUEST:
            break;
        default:
            if (!CommonUtils.isMobile(registerVo.getMobile())) {
                throw new MobileFormatIncorrectException();
            }
        }

        if (registerVo.getRegisterType() != null
                && (LoginType.LOGIN == registerVo.getRegisterType() || LoginType.REGISTER == registerVo
                        .getRegisterType()) && !CommonUtil.isPasswordFormatCorrect(registerVo.getPassword())) {
            throw new PasswordFormatIncorrectException();
        }
        if (!CommonUtil.isIp(registerVo.getIp())) {
            throw new IPFormatIncorrectException();
        }

    }
}
