package com.zizaike.passport.domain.source;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.vo.RegisterVo;

/**
 * 
 * ClassName: RegisterEventSource <br/>
 * Function: 注册事件源数据. <br/>
 * date: 2016年4月5日 下午2:38:46 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
public class RegisterEventSource implements Serializable {

    private static final long serialVersionUID = -5722972179519675246L;
    /*** 注册IP ***/
    private String ip;
    /**
     * 注册时间
     */
    private Date registerAt = new Date();

    /*** 注册来源 ***/
    private ChannelType channel;

    private Integer userId;

    private RegisterType registerType;

    private String userName;

    private String mobile;
    private String email;
    /**
     * MD5密码
     */
    private String password;

    private String nickName;
    private String SSID;

    public static RegisterEventSource newInstance(RegisterVo registerVo) {
        RegisterEventSource instance = new RegisterEventSource();
        instance.ip = registerVo.getIp();
        instance.channel = registerVo.getChannelType();
        instance.registerType = registerVo.getRegisterType();
        instance.email = registerVo.getEmail();
        instance.mobile = registerVo.getMobile();
        instance.nickName = registerVo.getNickname();
        instance.password = registerVo.getPassword();
        return instance;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisterAt() {
        return registerAt;
    }
    public  void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }


    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
         this.userId = userId;
    }


    public String getIp() {
        return ip;
    }


    public ChannelType getChannel() {
        return channel;
    }


    public RegisterType getRegisterType() {
        return registerType;
    }


    public String getUserName() {
        return userName;
    }


    public String getMobile() {
        return mobile;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getNickName() {
        return nickName;
    }

    
    public String getSSID() {
        return SSID;
    }

    public void setSSID(String sSID) {
        SSID = sSID;
    }

    @Override
    public String toString() {
        return "RegisterEventSource [ip=" + ip + ", registerAt=" + registerAt + ", channel=" + channel + ", userId="
                + userId + ", registerType=" + registerType + ", userName=" + userName + ", mobile=" + mobile
                + ", email=" + email + ", password=" + password + ", nickName=" + nickName + ", SSID=" + SSID + "]";
    }

    
}
