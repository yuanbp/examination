package pers.chieftain.examination.des;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * <p>
 * ClassName: DESUtils
 * </p>
 * <p>
 * Description: DES加/解密工具类
 * </p>
 * <p>
 * Author: colick
 * </p>
 * <p>
 * Date: 2015-11-17
 * </p>
 */
public class DESUtils {
    private static Log log = LogFactory.getLog(DESUtils.class);

    // 加解密统一使用的编码方式
    private final static String ENCODING = "utf-8";

    private final static String DES = "DES";
    private final static String DES3 = "DESede";

    // 密钥
    private static String secretKey = "mobilewinx@easipass@1234";

    // 向量
    private static String iv = "01234567";

    public static String getSecretKey() {
        return secretKey;
    }

    public static void setSecretKey(String secretKey) {
        DESUtils.secretKey = secretKey;
    }

    public static String getIv() {
        return iv;
    }

    public static void setIv(String iv) {
        DESUtils.iv = iv;
    }

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */

    public static String des3EncodeCBC(String plainText) throws Exception {
        return Base64Utils.encode(des3EncodeCBC(secretKey.getBytes() , iv.getBytes() , plainText.getBytes(ENCODING)));
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String des3DecodeCBC(String encryptText) throws Exception {
        return new String(des3DecodeCBC(secretKey.getBytes() , iv.getBytes() , Base64Utils.decode(encryptText)), ENCODING);
    }


    /**
     * DES加密
     */
    public static byte[] desEncode(byte[] key, byte[] data) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey desKey = keyFactory.generateSecret(desKeySpec);
            SecureRandom random = new SecureRandom();
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.ENCRYPT_MODE , desKey , random);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * DES解密
     */
    public static byte[] desDecode(byte[] key, byte[] data) {
        try {
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            SecretKey desKey = keyFactory.generateSecret(desKeySpec);
            SecureRandom random = new SecureRandom();
            Cipher cipher = Cipher.getInstance(DES);
            cipher.init(Cipher.DECRYPT_MODE , desKey , random);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * 3DES ECB模式加密
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data) {
        try {
            SecretKey desKey = new SecretKeySpec(key, DES3);    //生成密钥
            Cipher cipher = Cipher.getInstance(DES3 + "/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * 3DES ECB模式解密
     */
    public static byte[] des3DecodeECB(byte[] key, byte[] data) {
        try {
            SecretKey desKey = new SecretKeySpec(key, DES3);    //生成密钥
            Cipher cipher = Cipher.getInstance(DES3 + "/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }


    /**
     * 3DES CBC模式加密
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] iv, byte[] data) {
        try {
            SecretKey desKey = new SecretKeySpec(key, DES3);    //生成密钥
            Cipher cipher = Cipher.getInstance(DES3 + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, desKey, ips);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * 3DES CBC模式解密
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] iv, byte[] data) {
        try {
            SecretKey desKey = new SecretKeySpec(key, DES3);    //生成密钥
            Cipher cipher = Cipher.getInstance(DES3 + "/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, desKey, ips);
            return cipher.doFinal(data);
        } catch (Exception e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * 根据字符串生成密钥字节数组
     *
     * @param keyStr 密钥字符串
     */
    private static byte[] build3DesKey(String keyStr) {
        try {
            byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
            byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
            if (key.length > temp.length) {
                //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
                System.arraycopy(temp, 0, key, 0, temp.length);
            } else {
                //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
                System.arraycopy(temp, 0, key, 0, key.length);
            }
            return key;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage() , e);
            return null;
        }
    }

    /**
     * 转换成十六进制字符串
     * @return
     *
     * lee on 2017-08-09 10:54:19
     */
    public static byte[] hex(String key){
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i=0;i<24;i++){
            enk[i] = bkeys[i];
        }
        return enk;
    }

}
