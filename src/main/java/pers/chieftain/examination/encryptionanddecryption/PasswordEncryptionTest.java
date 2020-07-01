package pers.chieftain.examination.encryptionanddecryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * 口令加密
 * @author chieftain
 * @date 2020/5/7 10:45
 */
public class PasswordEncryptionTest {

    public static void main(String[] args) throws GeneralSecurityException {
        // 把BouncyCastle作为Provider添加到java.security:
        Security.addProvider(new BouncyCastleProvider());
        // 原文:
        String message = "Hello, world!";
        // 加密口令:
        String password = "hello12345";
        // 16 bytes随机Salt:
        byte[] salt = SecureRandom.getInstanceStrong().generateSeed(16);
        BASE64Encoder encoder = new BASE64Encoder();
        String saltStr = encoder.encode(salt);
//        System.out.printf("salt: %032x\n", new BigInteger(1, salt));
        System.out.println(saltStr);
        // 加密:
        byte[] data = message.getBytes(StandardCharsets.UTF_8);
//        byte[] encrypted = encrypt(password, salt, data);
        byte[] encrypted = encrypt(password, saltStr.getBytes(StandardCharsets.UTF_8), data);
        System.out.println("encrypted: " + encoder.encode(encrypted));
        // 解密:
//        byte[] decrypted = decrypt(password, salt, encrypted);
        byte[] decrypted = decrypt(password, saltStr.getBytes(StandardCharsets.UTF_8), encrypted);
        System.out.println("decrypted: " + new String(decrypted, StandardCharsets.UTF_8));
    }

    // 加密:
    public static byte[] encrypt(String password, byte[] salt, byte[] input) throws GeneralSecurityException, NoSuchAlgorithmException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory skeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
        SecretKey skey = skeyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeps = new PBEParameterSpec(salt, 1000);
        Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
        cipher.init(Cipher.ENCRYPT_MODE, skey, pbeps);
        return cipher.doFinal(input);
    }

    // 解密:
    public static byte[] decrypt(String password, byte[] salt, byte[] input) throws GeneralSecurityException {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory skeyFactory = SecretKeyFactory.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
        SecretKey skey = skeyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeps = new PBEParameterSpec(salt, 1000);
        Cipher cipher = Cipher.getInstance("PBEwithSHA1and128bitAES-CBC-BC");
        cipher.init(Cipher.DECRYPT_MODE, skey, pbeps);
        return cipher.doFinal(input);
    }
}
