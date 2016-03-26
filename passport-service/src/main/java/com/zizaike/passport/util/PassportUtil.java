package com.zizaike.passport.util;
/**
 * 
 * ClassName: PassportUtil <br/>  
 * Function: passport工具. <br/>  
 * date: 2016年3月25日 下午8:25:58 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public abstract class PassportUtil {
	
	/**
     * 验证密码格式是否正确
     * 
     * @param password
     * @return
     */
    public static boolean isPasswordFormatCorrect(String password) {
        if (password == null) {
            return false;
        }
        return password.length() == 32 ? true : false;
    }
}