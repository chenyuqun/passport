package com.zizaike.passport.domain.event;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.core.common.util.date.DateUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;

/**
 * 
 * ClassName: LoginFailedEventSouce <br/>  
 * Function: 登录失败事件源. <br/>  
 * date: 2016年4月5日 上午11:37:50 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class LoginFailedEventSouce implements Serializable{

    private static final long serialVersionUID = 1787869513542016139L;

    /*** 错误吗 ***/
    private String errorCode;

    /*** 错误原因 ***/
    private String reason;

    private LoginEventSource loginEventSource;

    public LoginFailedEventSouce(String errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public LoginFailedEventSouce(ZZKServiceException zzke, LoginEventSource loginEventSource) {
        this.errorCode = zzke.getErrorCode().getErrorCode();
        this.reason = zzke.getErrorCode().getErrorMsg();
        this.loginEventSource = loginEventSource;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getReason() {
        return reason;
    }

    public Integer getUserId() {
        if (loginEventSource == null) {
            return null;
        }
        return loginEventSource.getUserId();
    }

    public LoginType getLoginType() {
        if (loginEventSource == null) {
            return null;
        }
        return loginEventSource.getLoginType();
    }
    public ChannelType getChannel() {
        if (loginEventSource == null) {
            return null;
        }
        return loginEventSource.getChannel();
    }

    public String getIp() {
        if (loginEventSource == null) {
            return null;
        }
        return loginEventSource.getIp();
    }

    @JSONField(format = DateUtil.patternDateTime)
    public Date getLoginTime() {
        if (loginEventSource == null) {
            return null;
        }
        return loginEventSource.getLoginAt();
    }

    @Override
    public String toString() {
        return "LoginFailedEventSouce [errorCode=" + errorCode + ", reason=" + reason + ", loginEventSource="
                + loginEventSource + "]";
    }
    
}
