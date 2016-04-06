package com.zizaike.passport.entity;

import java.util.Date;

import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.passport.domain.event.RegisterEventSource;

/**
 * 
 * ClassName: RegisterLog <br/>  
 * Function: 注册日志. <br/>  
 * date: 2016年4月6日 下午5:45:01 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class RegisterLog {
    private Integer id;

    private Integer userId;

    private String userName;

    private String nickName;

    private String email;

    private String mobile;
    /**
     * 渠道
     */
    private ChannelType channel;
    /**
     * 注册类型
     */
    private RegisterType registerType;

    private String ip;

    private String password;
    /**
     * 状态
     */
    private OperateStatus status;

    private String errorCode;

    private String reason;

    private Date registerAt ;
    
    public static RegisterLog newInstance(RegisterEventSource registerEventSource){
        RegisterLog instance = new RegisterLog();
        if(registerEventSource == null){
            return instance;
        }
        instance.channel = registerEventSource.getChannel();
        instance.registerType = registerEventSource.getRegisterType();
        instance.ip = registerEventSource.getIp();
        instance.email = registerEventSource.getEmail();
        instance.mobile = registerEventSource.getMobile();
        instance.ip = registerEventSource.getIp();
        instance.nickName = registerEventSource.getNickName();
        instance.password = registerEventSource.getPassword();
        instance.userId = registerEventSource.getUserId();
        instance.userName = registerEventSource.getUserName();
        instance.registerAt = registerEventSource.getRegisterAt();
        return instance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public RegisterType getRegisterType() {
        return registerType;
    }

    public void setRegisterType(RegisterType registerType) {
        this.registerType = registerType;
    }

    public OperateStatus getStatus() {
        return status;
    }

    public void setStatus(OperateStatus status) {
        this.status = status;
    }
    
}