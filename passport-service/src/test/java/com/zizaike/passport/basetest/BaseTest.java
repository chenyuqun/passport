package com.zizaike.passport.basetest;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.constants.Constant;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.Activation;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.UserType;

@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml",
        "classpath:/spring/springmvc-servlet.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests {
    public static final String IP_DEFAULT = "127.0.0.1";
    public static final String PASSWORD_UNENCRPTED = CommonUtil.generatePassword(null, "password");

    /**
     * 创建默认的user对象
     * 
     * @return
     */
    public User createUser() {
        User user = new User();

        String mail = generateRandomMail();
        String mobile = generateRandomMobile();
        fillUser(user, mail, mobile);
        return user;
    }

    /**
     * 生成随机的mail
     * 
     * @return
     */
    public String generateRandomMail() {
        return UUID.randomUUID().toString() + "@zizaike.com";
    }

    /**
     * 生成随机的mobile
     * 
     * @return
     */
    public String generateRandomMobile() {
        Random random = new Random();
        int phoneStartIndex = random.nextInt(3);
        return phones[phoneStartIndex] + generateRandomInt(9);

    }
    public String generateRandomInt(int length){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<length;i++){
            sb.append(random.nextInt(9));
        }
        
        return sb.toString();
    }
    

    static String[] phones = { "13", "14", "15", "18" };
    
    /**
     * @param user
     * @param mail
     * @param mobile
     * @param username
     */
    private void fillUser(User user, String mail, String mobile) {
        user.setNickName(Constant.DEFAULT_NICKNAME);
        user.setMail(mail);
        user.setMobile(mobile);
        user.setPassword(PASSWORD_UNENCRPTED);
        user.setRegisterIP(IP_DEFAULT);
        user.setChannel(ChannelType.APP);
        user.setIntegral(0);
        user.setLoginCount(0);
        user.setActivation(Activation.YES);
        user.setActive(1);
        user.setCreateAt(new Date());
        user.setUpdateAt(new Date());
        user.setUserType(UserType.BUSINESS);
    }
}
