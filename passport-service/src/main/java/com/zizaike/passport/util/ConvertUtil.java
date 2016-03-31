package com.zizaike.passport.util;




import java.util.ArrayList;
import java.util.List;

import com.zizaike.entity.passport.domain.PassportStatus;


/**
 * 
 * ClassName: ConvertUtil <br/>  
 * Function: 进制转换用. <br/>  
 * date: 2016年3月28日 下午2:10:03 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
public class ConvertUtil {
    /**
     * 
     * binary2Decimalism:二进制转成十进制 <br/>
     * 
     * @author snow.zhang
     * @param binary
     * @return
     * @since JDK 1.6
     */
    public static int binary2Decimalism(int binary) {
        int sum = 0;
        int i = 0;
        while (binary != 0) {
            sum = (int) (sum + binary % 10 * (Math.pow(2, i)));
            binary = binary / 10;
            i++;
        }
        return sum;

    }

    public static int[] binary2binaryArray(int binary) {
        int[] binaryArray = new int[10];// int 表示也就10位
        int i = 0;
        while (binary != 0) {
            binaryArray[i] = (binary % 10);
            binary = binary / 10;
            i++;
        }
        return binaryArray;
    }

    /**
     * 
     * binary2Decimalism:二进制转成十进制 <br/>
     * 
     * @author snow.zhang
     * @param binary
     * @return
     * @since JDK 1.6
     */
    public static int binary2Decimalism(String binary) {

        return binary2Decimalism(Integer.parseInt(binary));
    }

    /**
     * 
     * decimalism2Binary:十进制转成二进制 <br/>
     * 
     * @author snow.zhang
     * @param binary
     * @return
     * @since JDK 1.6
     */
    public static int decimalism2Binary(int binary) {
        String binaryString = Integer.toBinaryString(binary);
        return Integer.parseInt(binaryString);
    }

    /**
     * 
     * decimalism2Binary:十进制转成二进制 <br/>
     * 
     * @author snow.zhang
     * @param binary
     * @return
     * @since JDK 1.6
     */
    public static int decimalism2Binary(String binary) {
        String binaryString = Integer.toBinaryString(Integer.parseInt(binary));
        return Integer.parseInt(binaryString);
    }

    /**
     * 
     * setStatusOperate:得到设置状态码<br/>
     * 
     * @author snow.zhang
     * @param setStatusBinary 需要设置状态的二进制码
     * @param oldStatusBinary 原先状态的二进制码
     * @return
     * @since JDK 1.6
     */
    public static int setStatusOperate(int setStatusBinary, int oldStatusBinary) {
        int setStatusDecimalism = binary2Decimalism(setStatusBinary);
        int oldStatusDecimalism = binary2Decimalism(oldStatusBinary);
        int returnDecimalism = setStatusDecimalism | oldStatusDecimalism;// 二进制 位或操作
        return ConvertUtil.decimalism2Binary(returnDecimalism);
    }
    /**
     * 
     * setStatusOperate:设置状态码 <br/>  
     *  
     * @author snow.zhang
     * @param passportStatusList
     * @param oldStatusList
     * @return  
     * @since JDK 1.6
     */
    public static int setStatusOperate(List<PassportStatus> passportStatusList, List<PassportStatus> oldStatusList) {
        return setStatusOperate(getValueByStatus(passportStatusList), getValueByStatus(oldStatusList));
    }
    /**
     * 
     * setStatusOperate:设置单个 <br/>  
     *  
     * @author snow.zhang
     * @param passportStatus
     * @param oldStatusList
     * @return  
     * @since JDK 1.6
     */
    public static int setStatusOperate(PassportStatus passportStatus, List<PassportStatus> oldStatusList) {
        return setStatusOperate(passportStatus.getValue(), getValueByStatus(oldStatusList));
    }
    /**
     * 
     * isExist:是否存在异常 <br/>  
     *  isExistException(1110,10)  存在
     * @author snow.zhang
     * @param setStatusBinary
     * @param oldStatusBinary
     * @return  
     * @since JDK 1.6
     */
    public static boolean isExistException(int setStatusBinary, int oldStatusBinary) {
        if(oldStatusBinary==0){
           if(setStatusBinary == 0){
               return true;
           }else{
               return false;
           }
        }
        int setStatusDecimalism = binary2Decimalism(setStatusBinary);
        int oldStatusDecimalism = binary2Decimalism(oldStatusBinary);
        int returnDecimalism = setStatusDecimalism & oldStatusDecimalism;// 二进制 位或操作
        return returnDecimalism==oldStatusDecimalism;
    }
    /**
     * 
     * isExistException:查看异常是否存在<br/>  
     *  
     * @author snow.zhang
     * @param passportStatusList
     * @param oldStatusBinary
     * @return  
     * @since JDK 1.6
     */
    public static boolean isExistException(List<PassportStatus> passportStatusList, int oldStatusBinary) {
       return isExistException(getValueByStatus(passportStatusList),oldStatusBinary);
    }
    /**
     * 
     * cancelStatusOperate:取消状态.同位数中逢1变0 <br/>
     * 
     * @author snow.zhang
     * @param setStatusBinary 需要设置的状态
     * @param oldStatusBinary 原先的状态
     * @return
     * @since JDK 1.6
     */
    public static int cancelStatusOperate(int setStatusBinary, int oldStatusBinary) {
        int[] oldStatusBinaryArray = binary2binaryArray(oldStatusBinary);
        int[] setStatusBinaryArray = binary2binaryArray(setStatusBinary);
        int returnStatusBinary = 0;
        for (int i = 0; i < setStatusBinaryArray.length; i++) {
            // 逢1为0 撤消操作
            returnStatusBinary += (1 - setStatusBinaryArray[i]) * oldStatusBinaryArray[i] * (Math.pow(10, i));
        }

        return returnStatusBinary;
    }
    /**
     * 
     * cancelStatusOperate:取消状态 <br/>  
     *  
     * @author snow.zhang
     * @param passportStatusList
     * @param oldStatusList
     * @return  
     * @since JDK 1.6
     */
    public static int cancelStatusOperate(List<PassportStatus> passportStatusList,List<PassportStatus> oldStatusList ) {
        return cancelStatusOperate(getValueByStatus(passportStatusList), getValueByStatus(oldStatusList));
    }
    public static int cancelStatusOperate(PassportStatus passportStatus,List<PassportStatus> oldStatusList ) {
        return cancelStatusOperate(passportStatus.getValue(), getValueByStatus(oldStatusList));
    }
    
    /**
     * 通过值得到状态
     * getStatus:(这里用一句话描述这个方法的作用). <br/>  
     *  
     * @author snow.zhang
     * @param value
     * @return  
     * @since JDK 1.6
     */
    public static List<PassportStatus> getStatus(int value){
        List<PassportStatus> passportStatusList = new ArrayList<PassportStatus>();
        for (PassportStatus passportStatus : PassportStatus.values()) {
            if(ConvertUtil.isExistException(value, passportStatus.getValue())){
                passportStatusList.add(passportStatus);
            }
        }
        return passportStatusList;
    }
    /**
     * 
     * getValueByStatus: <br/>  
     *  
     * @author snow.zhang
     * @param status
     * @return  
     * @since JDK 1.6
     */
    public static int getValueByStatus(List<PassportStatus> status){
        int value = 0;
        for (PassportStatus passportStatus : status) {
          value += passportStatus.getValue();
      }
        return value;
    }
    public static void main(String[] args) {
       /** System.err.println(binary2Decimalism(10010));
        System.err.println(cancelStatusOperate(10000111, 10010011));
        System.err.println(binary2Decimalism("100"));
        System.err.println(decimalism2Binary(100));
        System.err.println(cancelStatusOperate(100, 110));**/
        System.err.println(isExistException(100,10));
    }

}
