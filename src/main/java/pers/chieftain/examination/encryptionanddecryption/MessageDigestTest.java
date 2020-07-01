package pers.chieftain.examination.encryptionanddecryption;

import cn.hutool.core.util.HexUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 计算文件或字符串hash
 *
 * @author chieftain
 * @date 2020/5/6 18:11
 */
public class MessageDigestTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("Hello".getBytes(StandardCharsets.UTF_8));
        md.update("World".getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest(); // 16 bytes: 68e109f0f40ca72a15e05cc22786f8e6
//        System.out.println(new BigInteger(1, result).toString(16));
        String hex = HexUtil.encodeHexStr(result);
        System.out.println(hex);
//        System.out.println(Base64Encoder.encode(result));

        // 计算文件的hash
        System.out.println(md5HashCode32(Files.newInputStream(new File("/Users/chieftain/Downloads/TheArchives/v2ray-macos.zip").toPath())));

//        String passwordToHash = "password";
//        byte[] salt = getSalt();
//        String securePassword = getSHASecurePassword(passwordToHash, salt);
//        System.out.println(securePassword);
//        String saltStr = new String(salt, StandardCharsets.UTF_8);
//        String securePassword2 = getSHASecurePassword(passwordToHash, saltStr.getBytes(StandardCharsets.UTF_8));
//        System.out.println(securePassword2);

        String passwordToHash = "password";
        // 获取随机盐
        String salt = getSaltStr();
        String securePassword = getSHASecurePassword(passwordToHash, salt.getBytes(StandardCharsets.UTF_8));
        System.out.println(securePassword);
        String securePassword2 = getSHASecurePassword(passwordToHash, salt.getBytes(StandardCharsets.UTF_8));
        System.out.println(securePassword2);

        getSHA256SecurePassword(passwordToHash);

        // HMAC hash算法
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = keyGen.generateKey();
        // 打印随机生成的key:
        byte[] skey = key.getEncoded();
        BASE64Encoder encoder = new BASE64Encoder();
        String hmacSalt = encoder.encode(skey);
//        String hmacSalt = Hex.encodeHexStr(skey);
//        String hmacSalt = new BigInteger(1, skey).toString(16);
        System.out.println(hmacSalt);
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        mac.update("123123".getBytes(StandardCharsets.UTF_8));
        byte[] result2 = mac.doFinal();
//        System.out.println(new BigInteger(1, result2).toString(16));
        System.out.println(HexUtil.encodeHexStr(result2));
//        System.out.println(Base64Encoder.encode(result2));

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] hkey = decoder.decodeBuffer(hmacSalt);
//        byte[] hkey = Hex.decodeHex(hmacSalt.toCharArray());
//        byte[] hkey = new BigInteger(hmacSalt, 16).toByteArray();
        SecretKey key2 = new SecretKeySpec(hkey, "HmacSHA256");
        mac.init(key2);
        mac.update("123123".getBytes(StandardCharsets.UTF_8));
        byte[] result3 = mac.doFinal();
//        System.out.println(new BigInteger(1, result3).toString(16));
        System.out.println(HexUtil.encodeHexStr(result3));
//        System.out.println(Base64Encoder.encode(result3));

        System.out.println(encoder.encode("123123".getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * 获取文件的md5值 ，有可能不是32位
     *
     * @param filePath 文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static String md5HashCode(String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        return md5HashCode(fis);
    }

    /**
     * 保证文件的MD5值为32位
     *
     * @param filePath 文件路径
     * @return
     * @throws FileNotFoundException
     */
    public static String md5HashCode32(String filePath) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePath);
        return md5HashCode32(fis);
    }

    /**
     * java获取文件的md5值
     *
     * @param fis 输入流
     * @return
     */
    public static String md5HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * java计算文件32位md5值
     *
     * @param fis 输入流
     * @return
     */
    public static String md5HashCode32(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();

            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            StringBuilder hexValue = new StringBuilder();
            /**
             * 如果小于16，那么val值的16进制形式必然为一位，
             * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
             * 此处高位补0。
             */
            for (byte md5Byte : md5Bytes) {
                int val = ((int) md5Byte) & 0xff;//解释参见最下方
                if (val < 16) {
                    /**
                     * 如果小于16，那么val值的16进制形式必然为一位，
                     * 因为十进制0,1...9,10,11,12,13,14,15 对应的 16进制为 0,1...9,a,b,c,d,e,f;
                     * 此处高位补0。
                     */
                    hexValue.append("0");
                }
                //这里借助了Integer类的方法实现16进制的转换
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getSHASecurePassword(String passwordToHash, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            //MessageDigest md = MessageDigest.getInstance("SHA-256");
            //MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    //Add salt
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private static String getSaltStr() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(salt);
    }

    private static String getSHA256SecurePassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            // 为MD5创建MessageDigest实例
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //添加密码字节以进行
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //将其转换为十六进制格式
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //得到完整的哈希密码在十六进制格式
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }


}
