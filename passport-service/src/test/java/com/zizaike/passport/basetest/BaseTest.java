package com.zizaike.passport.basetest;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.zizaike.core.common.util.CommonUtil;
import com.zizaike.core.constants.Constant;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.passport.Passport;
import com.zizaike.entity.passport.PassportResult;
import com.zizaike.entity.passport.User;
import com.zizaike.entity.passport.domain.Activation;
import com.zizaike.entity.passport.domain.ChannelType;
import com.zizaike.entity.passport.domain.PassportStatus;
import com.zizaike.entity.passport.domain.RegisterType;
import com.zizaike.entity.passport.domain.UserType;
import com.zizaike.entity.passport.domain.vo.RegisterVo;
import com.zizaike.entity.passport.domain.vo.RegisterVo.RegisterVoBuilder;
import com.zizaike.is.passport.RegisterService;
import com.zizaike.passport.service.CommonService;
import com.zizaike.passport.service.PassportService;
import com.zizaike.passport.service.TlasService;
import com.zizaike.passport.service.UserService;

@ContextConfiguration(locations = { "classpath:/spring/applicationContext.xml",
        "classpath:/spring/springmvc-servlet.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests {
    public static final String IP_DEFAULT = "127.0.0.1";
    public static final String PASSWORD_UNENCRPTED = CommonUtil.generatePassword(null, "password");
    @Autowired
    protected TlasService tlasService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected PassportService passportService;
    @Autowired
    protected RegisterService registerService;
    @Autowired
    protected CommonService commonService;

    /**
     * 生成随机ID
     * 
     * @return
     */
    public int generateId() {
        int id = Math.abs(UUID.randomUUID().hashCode());
        while (id == 0) {
            id = Math.abs(UUID.randomUUID().hashCode());
        }
        return id;
    }

    public PassportResult registerTestPassport() throws ZZKServiceException {
        String email = generateRandomMail();
        String password = PASSWORD_UNENCRPTED;
        String ip = IP_DEFAULT;
        RegisterVo registerVo = new RegisterVoBuilder(email, null, password, ip).setChannelType(ChannelType.WEB)
                .setRegisterType(RegisterType.EMAIL).build();
        return commonService.register(registerVo);
    }

    /**
     * 创建邮箱注册的测试用户
     * 
     * @return
     */
    public Passport addTestPassportByMail() throws ZZKServiceException {
        User user = addTestUserByMail();
        Passport passport = createPassport();
        passport.setUserId(user.getUserId());
        return passport;
    }

    public User addTestUserByMail() throws ZZKServiceException {
        User user = createUser();
        Passport passport = createPassport();
        userService.save(user);
        passport.setUserId(user.getUserId());
        passportService.save(passport);
        return user;
    }

    /**
     * 创建默认的passport对象
     * 
     * @param id
     * @return
     */
    public Passport createPassport() {
        Passport passport = new Passport();
        String password = PASSWORD_UNENCRPTED;
        passport.setSalt(tlasService.getRandomSalt(IP_DEFAULT));
        passport.setStatus(PassportStatus.AVAILABLE);
        fillPassport(passport, password, tlasService.getSalt(passport.getSalt()));
        return passport;
    }

    private void fillPassport(Passport passport, String password, String salt) {

        String hash = CommonUtil.generateHash(password, salt);
        passport.setHash(hash);

        Date date = new Date();
        passport.setCreateAt(date);
        passport.setUpdateAt(date);
    }

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

    public String generateRandomInt(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
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
    private void fillUser(User user, String email, String mobile) {
        user.setNickName(Constant.DEFAULT_NICKNAME);
        user.setEmail(email);
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
