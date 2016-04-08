package com.zizaike.passport.domain;

import com.zizaike.core.framework.event.BusinessOperation;

/**
 * 
 * ClassName: PassportBusinessOperation <br/>  
 * Function: Passport 业务操作. <br/>  
 * date: 2016年3月31日 下午3:05:57 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public enum PassportBusinessOperation implements BusinessOperation {

    /*** 登陆 ***/
    LOGIN("LOGIN"),
    /*** 注册 ***/
    REGISTER("REGISTER"),
    /*** 第三方登录 ***/
    THIRD_LOGIN("THIRD_LOGIN"),
    /*** 第三方绑定 ***/
    THIRD_BIND("THIRD_BIND"),
    /*** 更新密码 ***/
    UPDATE_PASSWORD("UPDATE_PASSWORD"),
    /*** 重置密码 ***/
    RESET_PASSWORD("RESET_PASSWORD"),
    /** 用户身份验证 */
    VERIFY_USER_IDENTIFY("VERIFY_USER_IDENTIFY"),
    /** 验证验证码 **/
    VERIFY_DOVERIFY("VERIFY_DOVERIFY");

    private String operation;

    public String getOperation() {
        return operation;
    }

    private PassportBusinessOperation(String operation) {
        this.operation = operation;
    }

    public static void main(String[] args) {
        System.err.println(PassportBusinessOperation.UPDATE_PASSWORD.getOperation());
    }
}
