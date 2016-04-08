package com.zizaike.passport.domain.source;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.UpdatePasswordOpreateType;

/**
 * 
 * ClassName: RegisterEventSource <br/>
 * Function: 更新密码事件源数据. <br/>
 * date: 2016年4月5日 下午2:38:46 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
public class UpdatePasswordEventSource implements Serializable {

    private static final long serialVersionUID = -5722972179519675246L;
    /*** 注册IP ***/
    private String ip;
    /**
     * 注册时间
     */
    private Date createAt = new Date();

    /*** 注册来源 ***/
    private ChannelType channel;

    private Integer userId;

    private UpdatePasswordOpreateType updatePasswordOpreateType;

    /**
     * MD5密码
     */
    private String md5Password;

    public static UpdatePasswordEventSource newInstance(Integer userId, String ip, String md5Password,
            UpdatePasswordOpreateType updatePasswordOpreateType, ChannelType channel) {
        UpdatePasswordEventSource instance = new UpdatePasswordEventSource();
        instance.ip = ip;
        instance.channel = channel;
        instance.md5Password = md5Password;
        instance.userId = userId;
        instance.updatePasswordOpreateType = updatePasswordOpreateType;
        return instance;
    }

    public Integer getUserId() {
        return userId;
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
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

    public String getMd5Password() {
        return md5Password;
    }

    public UpdatePasswordOpreateType getUpdatePasswordOpreateType() {
        return updatePasswordOpreateType;
    }

}
