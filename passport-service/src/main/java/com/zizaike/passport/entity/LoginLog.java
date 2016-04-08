package com.zizaike.passport.entity;

import java.util.Date;

import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.OperateStatus;
import com.zizaike.passport.domain.source.LoginEventSource;

/**
 * 
 * ClassName: LoginLog <br/>  
 * Function: 登陆日志. <br/>  
 * date: 2016年4月6日 上午10:48:21 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class LoginLog {
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     *昵称
     */
    private String nickName;
    /**
     * 邮件
     */
    private String email;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 登陆类型
     */
    private LoginType loginType;
    /**
     * 渠道
     */
    private ChannelType channel;
    /**
     * ip
     */
    private String ip;
    /**
     * md5 密码
     */
    private String password;
    /**
     * 状态
     */
    private OperateStatus status;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误理由
     */
    private String reason;
    /**
     * 登陆时间
     */
    private Date loginAt;
    public static LoginLog newInstance(LoginEventSource loginEventSource){
        LoginLog instance = new LoginLog();
        if(loginEventSource == null){
            return instance;
        }
        instance.channel = loginEventSource.getChannel();
        instance.loginType = loginEventSource.getLoginType();
        instance.ip = loginEventSource.getIp();
        instance.email = loginEventSource.getEmail();
        instance.mobile = loginEventSource.getMobile();
        instance.ip = loginEventSource.getIp();
        instance.nickName = loginEventSource.getNickName();
        instance.password = loginEventSource.getPassword();
        instance.userId = loginEventSource.getUserId();
        instance.loginAt = loginEventSource.getLoginAt();
        instance.userName = loginEventSource.getUserName();
        
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

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
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
    @Override
    public String toString() {
        return "LoginLog [id=" + id + ", userId=" + userId + ", userName=" + userName + ", nickName=" + nickName
                + ", email=" + email + ", mobile=" + mobile + ", loginType=" + loginType + ", channel=" + channel
                + ", ip=" + ip + ", password=" + password + ", status=" + status + ", errorCode=" + errorCode
                + ", reason=" + reason + ", loginAt=" + loginAt + "]";
    }
    
    

}