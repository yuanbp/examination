package pers.chieftain.examination.encryptionanddecryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * 非对称加密
 * @author chieftain
 * @date 2020/5/6 10:54
 */
public class AsymmetricCryptoTest {

    public static void main(String[] args) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
//        RSA rsa = new RSA(loadPrivateKey(), loadPublicKey());
//        //获得私钥
//        rsa.getPrivateKey();
//        rsa.getPrivateKeyBase64();
//        //获得公钥
//        rsa.getPublicKey();
//        rsa.getPublicKeyBase64();
//
//        //公钥加密，私钥解密
//        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//        BASE64Encoder base64Encoder = new BASE64Encoder();
//        String encryptBase64Str = base64Encoder.encode(encrypt);
//        System.out.println(encryptBase64Str);
//
//        BASE64Decoder base64Decoder = new BASE64Decoder();
//        byte[] dencryptBase64Str = base64Decoder.decodeBuffer(encryptBase64Str);
//
////        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
//        byte[] decrypt = rsa.decrypt(dencryptBase64Str, KeyType.PrivateKey);
//        System.out.println(new String(decrypt, CharsetUtil.CHARSET_UTF_8));
//
//        //私钥加密，公钥解密
//        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
//        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
//
//        // hash 签名,将内容计算出hash,然后对hash使用私钥加密,对方使用公钥解密得到hash,然后计算收到的内容hash,进行比对是否一致,从而判断内容是否篡改
//        String msg = "我是一段测试aaaa";
//        String hash = String.valueOf(msg.hashCode());
//        byte[] encrypt3 = rsa.encrypt(StrUtil.bytes(hash, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
//        String encryptBase64Str2 = base64Encoder.encode(encrypt3);
//
//        byte[] dencryptBase64Str2 = base64Decoder.decodeBuffer(encryptBase64Str2);
//        byte[] decrypt3 = rsa.decrypt(dencryptBase64Str2, KeyType.PublicKey);
//        String decryptHash = new String(decrypt3, StandardCharsets.UTF_8);
//        System.out.println(hash.equals(decryptHash));

        getPriInP12();
        getPubInCert();
    }

    public static PrivateKey loadPrivateKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        String password = null;

        // 实例化密钥库，默认JKS类型
        KeyStore ks = KeyStore.getInstance("PKCS12");
        // 获得密钥库文件流
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("keys/metinform_key_pair.p12");
        // 加载密钥库
        char[] pwd = StringUtils.isBlank(password) ? null : password.toCharArray();
        ks.load(is, pwd);
        // 关闭密钥库文件流
        is.close();

        //私钥
        Enumeration aliases = ks.aliases();
        String keyAlias = null;
        if (aliases.hasMoreElements()) {
            keyAlias = (String) aliases.nextElement();
        }
        Key key = ks.getKey(keyAlias, pwd);
        if (key instanceof PrivateKey) {
            return (PrivateKey) key;
        }
        return null;
    }

    public static PublicKey loadPublicKey() throws CertificateException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("keys/metinform_key_pair.cer");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        return cert.getPublicKey();

    }

    /**
     * 没有密码的keystore 获取证书为null 需要手动导出 证书
     * jceks 必须要密码
     * keystore 的密码 和 key pair 的密码必须一致
     *
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    public static void getPriInP12() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
//        String password = null;
        String password = "123456";

        // 实例化密钥库，默认JKS类型
        KeyStore ks = KeyStore.getInstance("PKCS12");
//        KeyStore ks = KeyStore.getInstance("JCEKS");
        // 获得密钥库文件流
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("keys/metinform_p12.p12");
        // 加载密钥库
        char[] pwd = StringUtils.isBlank(password) ? null : password.toCharArray();
        ks.load(is, pwd);
        // 关闭密钥库文件流
        assert is != null;
        is.close();

        //私钥
        Enumeration aliases = ks.aliases();
        String keyAlias = null;
        if (aliases.hasMoreElements()) {
            keyAlias = (String) aliases.nextElement();
            System.out.println("p12's alias----->" + keyAlias);
        }
        PrivateKey privateKey = (PrivateKey) ks.getKey(keyAlias, pwd);
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        System.out.println("私钥------------->" + privateKeyStr);

        Certificate certificate = ks.getCertificate(keyAlias);
        String publicKeyStr = Base64.encodeBase64String(certificate.getPublicKey().getEncoded());
        System.out.println("公钥------------->" + publicKeyStr);
    }

    public static void getPubInCert() throws CertificateException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("keys/metinform_key_pair.cer");
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
        PublicKey publicKey = cert.getPublicKey();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");
    }
}
