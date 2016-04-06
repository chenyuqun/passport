package com.zizaike.passport.domain.event;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;

public class RegisterFailedEventSource{

    /***错误吗***/
    private String errorCode;

    /***错误原因***/
    private String reason;

    private RegisterEventSource registerEventSource;

    public RegisterFailedEventSource(ZZKServiceException ve,RegisterEventSource registerEventSource){
        this.errorCode = ve.getErrorCode().getErrorCode();
        this.reason = ve.getErrorCode().getErrorMsg();
        this.registerEventSource = registerEventSource;
    }

    public String getReason() {
        return reason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getExtend() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getExtend();
    }

    public String getInviter() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getInviter();
    }

    public String getIp() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getIp();
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisterAt() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getRegisterAt();
    }

    public ChannelType getChannel() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getChannel();
    }


    public Integer getUserId() {
        if(registerEventSource == null){
            return null;
        }
        return registerEventSource.getUserId();
    }




}