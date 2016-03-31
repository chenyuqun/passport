package com.zizaike.passport.dao;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.passport.User;

/**  
 * ClassName:UserService <br/>  
 * Function: 用户服务. <br/>  
 * Date:     2016年3月26日 下午1:20:43 <br/>  
 * @author   snow.zhang  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
public interface UserDao {
    /**
     * 检查手机是否存在
     * @param username
     * @return
     */
    @Slave
    public Boolean isMobileExist(String mobile);
    /**
     * 检查邮箱是否存在
     * @param username
     * @return
     */
    @Slave
    public Boolean isEmailExist(String email);
    /**
     * 
     * isUserNameExist:检查用户名是否存在. <br/>  
     *  
     * @author snow.zhang  
     * @param userName
     * @return  
     * @since JDK 1.7
     */
    @Slave
    public Boolean isUserNameExist(String userName);
    /**
     * 
     * save:保存用户. <br/>  
     *  
     * @author snow.zhang  
     * @param user  
     * @since JDK 1.7
     */
    @Master
    public void save(User user);
    
    /**
     * 
     * findByMobile:通过手机号找用户. <br/>  
     *  
     * @author snow.zhang  
     * @param mobile
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    public User findByMobile(String mobile) ;
    /**
     * 
     * findByEmail:通过邮箱找用户. <br/>  
     *  
     * @author snow.zhang  
     * @param email
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    public User findByEmail(String email);
    /**
     * 
     * findByUserName:通过密码找用户. <br/>  
     *  
     * @author snow.zhang  
     * @param userName
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @Slave
    public User findByUserName(String userName);
}
  