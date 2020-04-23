package com.chieftain.examination;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author chieftain
 * @date 2019-10-29 19:00
 */
public class JwtExam {

    public static void main(String[] args) throws Exception {
        String str = "app:ppa";
        System.out.println(str.split(":")[0]);
        RSA rsa = new RSA();
        InputStream privateKeyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("rsa_private_key_pkcs8.pem");
        rsa.loadPrivateKey(privateKeyStream);
        InputStream publicKeyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("rsa_public_key_2048.pub");
        rsa.loadPublicKey(publicKeyStream);
        try {
            Algorithm algorithm = Algorithm.RSA512(rsa.getPublicKey(), rsa.getPrivateKey());
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject("smartparkapp")
                    .withExpiresAt(new Date())
                    .withJWTId(UUID.randomUUID().toString())
                    .sign(algorithm);
            System.out.println(token);
            System.out.println(new String(Base64.getDecoder().decode(token.split("\\.")[0]), StandardCharsets.UTF_8));
            System.out.println(new String(Base64.getDecoder().decode(token.split("\\.")[1]), StandardCharsets.UTF_8));
//            TimeUnit.SECONDS.sleep(3);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .withSubject("smartparkapp")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(new String(Base64.getDecoder().decode(jwt.getHeader()), StandardCharsets.UTF_8));
            System.out.println(new String(Base64.getDecoder().decode(jwt.getPayload()), StandardCharsets.UTF_8));
            System.out.println(jwt.getHeader());
            System.out.println(jwt.getPayload());
            System.out.println(jwt.getSignature());
            System.out.println(jwt.getClaim("jti").asString());
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            exception.printStackTrace();
        }
    }
}
