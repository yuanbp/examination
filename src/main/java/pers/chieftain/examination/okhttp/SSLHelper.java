package pers.chieftain.examination.okhttp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SSLHelper.class.getSimpleName());

    public static SSLContext getSslContextForCertificateFile(String fileName) {
        try {
            KeyStore keyStore = SSLHelper.getKeyStore(fileName);
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext;
        } catch (Exception e) {
            String msg = "Cannot load certificate from file";
            LOG.error(msg, e);
            throw new RuntimeException(msg);
        }
    }

    private static KeyStore getKeyStore(String fileName) {
        KeyStore keyStore = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca;
            try (InputStream inputStream = new FileInputStream(fileName)) {
                ca = cf.generateCertificate(inputStream);
                LOG.debug("ca={}", ((X509Certificate) ca).getSubjectDN());
            }

            String keyStoreType = KeyStore.getDefaultType();
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (Exception e) {
            LOG.error("Error during getting keystore", e);
        }
        return keyStore;
    }

    public static SSLSocketFactory sslSocketFactory (SSLContext sslContext) {
        SSLSocketFactory sslSocketFactory=null;
        try {
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}