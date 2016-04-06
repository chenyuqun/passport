package com.zizaike.passport.domain.event;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.LoginType;
import com.zizaike.entity.passport.domain.vo.LoginVo;

/**
 * 
 * ClassName: LoginEventSource <br/>  
 * Function: 登陆事件源数据. <br/>  
 * date: 2016年4月5日 下午2:33:57 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class LoginEventSource implements Serializable {

    private static final long serialVersionUID = -1042578922387237570L;

    /*** 用户ID ***/
    private Integer userId;

    /*** 登录类型 ***/
    private LoginType loginType;
    /**
     * 登陆渠道
     */
    private ChannelType channel;
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

    /*** 登录IP ***/
    private String ip;

    private Date loginAt = new Date();

    /** MD5(登陆密码) **/
    private String password;

    public static LoginEventSource newInstance(LoginVo loginVo) {
        LoginEventSource instance = new LoginEventSource();
        if (loginVo == null){
            return instance;
        }
        instance.channel = loginVo.getChannelType();
        instance.loginType = loginVo.getLoginType();
        instance.ip = loginVo.getIp();
        instance.email = loginVo.getEmail();
        instance.mobile = loginVo.getMobile();
        instance.ip = loginVo.getIp();
        instance.nickName = loginVo.getNickName();
        instance.password = loginVo.getPassword();
        return instance;
    }
    
    public ChannelType getChannel() {
        return channel;
    }

    public void setChannel(ChannelType channel) {
        this.channel = channel;
    }

    public Integer getUserId() {
        return userId;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public String getIp() {
        return ip;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    /*** 不序列化password，只passport内部事件监听器使用 ***/
    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getUserName() {
        return userName;
    }


    public String getNickName() {
        return nickName;
    }


    public String getEmail() {
        return email;
    }


    public String getMobile() {
        return mobile;
    }
    


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "LoginEventSource [userId=" + userId + ", loginType=" + loginType + ", channel=" + channel
                + ", userName=" + userName + ", nickName=" + nickName + ", email=" + email + ", mobile=" + mobile
                + ", ip=" + ip + ", loginAt=" + loginAt + ", password=" + password + "]";
    }

    
}
