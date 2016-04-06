package com.zizaike.passport.domain.event;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.domain.ChannelType;
/**
 * 
 * ClassName: RegisterFailedEventSource <br/>  
 * Function: 注册失败. <br/>  
 * date: 2016年4月6日 下午5:38:31 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
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

    @Override
    public String toString() {
        return "RegisterFailedEventSource [errorCode=" + errorCode + ", reason=" + reason + ", registerEventSource="
                + registerEventSource + "]";
    }
    
}