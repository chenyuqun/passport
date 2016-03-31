package com.zizaike.passport.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 * ClassName: SSIDUtil <br/>
 * Function: SSID加密，解密. <br/>
 * date: 2016年3月28日 下午5:56:49 <br/>
 * 
 * @author snow.zhang
 * @version
 * @since JDK 1.7
 */
public class SSIDUtil {

    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    private static final int Keysize = 168;

    public static final String SSID_KEY = "*()&^%RT";

    public static final String FIND_PASSWORD_KEY = "&GFHGH435fg&";

    /**
     * SSID中的user_id所在位置
     */
    public static final int SSID_PASSPORT_ID_INT = 0;
    /**
     * SSID中的过期时间所在位置
     */
    public static final int SSID_DATE_INT = 1;

    /**
     * 
     * decrypt:数据解密. <br/>
     * 
     * @author snow.zhang
     * @param decryptStr
     * @param SSIDKey
     * @return
     * @throws Exception
     * @since JDK 1.7
     */
    public final static String decrypt(String decryptStr, String SSIDKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(Algorithm);
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(SSIDKey.getBytes("utf-8"));
        kgen.init(Keysize, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, Algorithm);
        Cipher cipher = Cipher.getInstance(Algorithm);// 创建密码器
        byte[] byteContent = parseHexStr2Byte(decryptStr);
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(byteContent);
        return new String(result, "utf-8");
    }

    /**
     * 
     * parseHexStr2Byte:将16进制字符串转化成byte[]. <br/>
     * 
     * @author snow.zhang
     * @param hexStr
     * @return
     * @since JDK 1.7
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 
     * encrypt:数据加密.userId,expiredTimes和到期时间合并成字符串后，使用密钥进行DES加密 <br/>
     * 
     * @author snow.zhang
     * @param encryptStr
     * @param SSIDKey
     * @return
     * @since JDK 1.7
     */
    public final static String encrypt(String encryptStr, String SSIDKey) {
        try {

            KeyGenerator kgen = KeyGenerator.getInstance(Algorithm);
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(SSIDKey.getBytes("utf-8"));
            kgen.init(Keysize, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, Algorithm);
            Cipher cipher = Cipher.getInstance(Algorithm);// 创建密码器
            byte[] byteContent = encryptStr.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return parseByte2HexStr(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * parseByte2HexStr:将byte[]转化成16进制字符串. <br/>
     * 
     * @author snow.zhang
     * @param buf
     * @return
     * @since JDK 1.7
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
}
