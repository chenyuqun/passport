package com.zizaike.passport.domain.event;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.ChannelType;

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
    private Date registerAt;

    /*** 注册来源 ***/
    private ChannelType channel;

    private Integer userId;

    /*** 注册邀请人 ***/
    private String inviter;

    /** 额外业务参数信息---> json格式 */
    private String extend;

    public static RegisterEventSource newInstance(User user) {
        RegisterEventSource instance = new RegisterEventSource();
        instance.ip = user.getRegisterIP();
        instance.channel = user.getChannel();
        
        return instance;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisterAt() {
        return registerAt;
    }

    public void setRegisterAt(Date registerAt) {
        this.registerAt = registerAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ChannelType getChannel() {
        return channel;
    }

    public void setChannelType(ChannelType channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "RegisterEventSource{" + "ip='" + ip + '\'' + ", registerAt=" + registerAt + '\'' + ", userId=" + userId
                + '\'' + ", inviter='" + inviter + '\'' + ", extend='" + extend + '\'' + '}';
    }
}
