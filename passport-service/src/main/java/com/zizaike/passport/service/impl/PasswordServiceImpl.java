/**  
 * Project Name:passport-service  <br/>
 * File Name:PasswordServiceImpl.java  <br/>
 * Package Name:com.zizaike.passport.service.impl  <br/>
 * Date:2016年3月31日下午2:50:41  <br/>
 * Copyright (c) 2016, zizaike.com All Rights Reserved.  
 *  
 */

package com.zizaike.passport.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.common.util.string.SuperString;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.IllegalParamterException;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.passport.IPFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordFormatIncorrectException;
import com.zizaike.core.framework.exception.passport.PasswordIncorrectException;
import com.zizaike.core.framework.exception.passport.UserNotExistException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.User;
import com.zizaike.is.passport.PasswordService;
import com.zizaike.passport.domain.event.PassportBusinessOperation;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.service.TlasService;

/**
 * ClassName:PasswordServiceImpl <br/>
 * Function: 密码服务. <br/>
 * Date: 2016年3月31日 下午2:50:41 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 * @see
 */
public class PasswordServiceImpl implements PasswordService {
    private static final Logger LOG = LoggerFactory.getLogger(PasswordServiceImpl.class);
    @Autowired
    private PassportService passportService;
    @Autowired
    private TlasService tlasService;

    @Override
    public void updatePassword(Integer userId, String oldMD5Password, String newMD5Password, String ip)
            throws ZZKServiceException {

        if (!CommonUtil.isIp(ip)) {
            LOG.info("updatePassword ip error, but can updatePassword, userId={}, ip={}, defaultIp={}", userId, ip,
                    Constant.IP_DEFAULT);
            ip = Constant.IP_DEFAULT;
        }

        // 格式验证
        validateFormat(userId, oldMD5Password, newMD5Password, ip);

        // 验证用户是否存在
        Passport passport = passportService.findPassport(userId);
        if (passport == null) {
            throw new UserNotExistException();
        }
        // 验证老密码是否正确
        String normalPassword = CommonUtil.generateHash(oldMD5Password, tlasService.getSalt(passport.getSalt()));
        LOG.info("oldPassword:{},normalPassword:{},hash:{}", oldMD5Password, normalPassword, passport.getHash());
        if (!passport.getHash().equals(normalPassword)) {
            throw new PasswordIncorrectException();
        }
        User findUser = new User();
        findUser.setUserId(userId);
        updatePassword(newMD5Password, ip, userId, PassportBusinessOperation.UPDATE_PASSWORD);

    }

    /**
     * 
     * updatePassword:更新密码. <br/>
     * 
     * @author snow.zhang
     * @param password
     * @param ip
     * @param userId
     * @param passportBusinessOperation
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
    private void updatePassword(String password, String ip, Integer userId,
            PassportBusinessOperation passportBusinessOperation) throws ZZKServiceException {
        long start = System.currentTimeMillis();
        // 重新生成密码Hash
        String newSalt = tlasService.getRandomSalt(ip);
        String newHash = CommonUtil.generateHash(password, tlasService.getSalt(newSalt));
        // 执行改操作
        passportService.updatePasspword(userId, newHash, newSalt);
        // userService.updateUsername(null, id);
        LOG.info("modify password successfully userId={} use {}ms", userId, System.currentTimeMillis() - start);
        // TODO 发送修改密码事件

    }

    /**
     * 
     * validateFormat:正则验证. <br/>
     * 
     * @author snow.zhang
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param ip
     * @throws ZZKServiceException
     * @since JDK 1.7
     */
    private void validateFormat(Integer userId, String oldPassword, String newPassword, String ip)
            throws ZZKServiceException {
        try {
            Preconditions.checkArgument(userId != null && userId > 0, "userId is null or zero");
            Preconditions.checkArgument(!SuperString.isBlank(oldPassword), "old password is empty");
            Preconditions.checkArgument(!SuperString.isBlank(newPassword), "new password is empty");
        } catch (IllegalArgumentException e) {
            throw new IllegalParamterException(e.getMessage());
        }

        if (!CommonUtil.isPasswordFormatCorrect(newPassword)) {
            throw new PasswordFormatIncorrectException();
        }
        if (!CommonUtil.isIp(ip)) {
            throw new IPFormatIncorrectException();
        }

    }

    @Override
    public void resetPassword(Integer userId, String password, String ip) throws ZZKServiceException {
        long start = System.currentTimeMillis();
        if (!CommonUtil.isIp(ip)) {
            LOG.info("resetPassword ip error, but can resetPassword, userId={}, ip={}, defaultIp={}", userId, ip,
                    Constant.IP_DEFAULT);
            ip = Constant.IP_DEFAULT;
        }
        if (userId == null || userId <= 0) {
            throw new IllegalParamterException("userId is null");
        }
        validatePasswordAndIpParams(password, ip);
        Passport findPassport = passportService.findPassport(userId);
        if (findPassport == null) {
            throw new UserNotExistException();
        }
        updatePassword(password, ip, userId, PassportBusinessOperation.RESET_PASSWORD);
        LOG.info("resetPassport success, userId={}, password={}, ip={}, useTime={}", userId, password, ip,
                System.currentTimeMillis() - start);

    }
    /**
     * 
     * validatePasswordAndIpParams:验证密码或是ip. <br/>  
     *  
     * @author snow.zhang  
     * @param password
     * @param ip
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    private void validatePasswordAndIpParams(String password, String ip) throws ZZKServiceException {

        if (SuperString.isBlank(password) || SuperString.isBlank(ip)) {
            throw new IllegalParamterException("password or ip is null");
        }
        if (!CommonUtil.isPasswordFormatCorrect(password)) {
            throw new PasswordFormatIncorrectException();
        }
        if (!CommonUtil.isIp(ip)) {
            throw new IPFormatIncorrectException();
        }
    }

}
