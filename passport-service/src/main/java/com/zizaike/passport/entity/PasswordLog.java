package com.zizaike.passport.entity;

import java.util.Date;

import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.entity.passport.domain.UpdatePasswordOpreateType;
import com.zizaike.passport.domain.source.UpdatePasswordEventSource;

/**
 * 
 * ClassName: PasswordLog <br/>  
 * Function: 密码操作日志. <br/>  
 * date: 2016年4月8日 下午12:39:23 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class PasswordLog {
    private Integer id;

    private Integer userId;

    private UpdatePasswordOpreateType operation;

    /**
     * 渠道
     */
    private ChannelType channel;

    private String ip;

    /**
     * 状态
     */
    private OperateStatus status;

    private String errorCode;

    private String reason;
    /**
     * 密码
     */
    private String password;

    private Date createAt;
    
    public String getPassword() {
        return password;
    }
    
    public static PasswordLog newInstance(UpdatePasswordEventSource updatePasswordEventSource){
        PasswordLog instance = new PasswordLog();
        if(updatePasswordEventSource == null){
            return instance;
        }
        instance.operation = updatePasswordEventSource.getUpdatePasswordOpreateType();
        instance.channel = updatePasswordEventSource.getChannel();
        instance.ip = updatePasswordEventSource.getIp();
        instance.password = updatePasswordEventSource.getMd5Password();
        instance.userId = updatePasswordEventSource.getUserId();
        return instance;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    public UpdatePasswordOpreateType getOperation() {
        return operation;
    }

    public void setOperation(UpdatePasswordOpreateType operation) {
        this.operation = operation;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    
    public OperateStatus getStatus() {
        return status;
    }

    public void setStatus(OperateStatus status) {
        this.status = status;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}