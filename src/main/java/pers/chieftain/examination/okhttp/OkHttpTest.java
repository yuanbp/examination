package pers.chieftain.examination.okhttp;

import com.alibaba.fastjson.JSON;
import com.hikvision.artemis.sdk.constant.HttpHeader;
import com.hikvision.artemis.sdk.constant.HttpMethod;
import com.hikvision.artemis.sdk.constant.SystemHeader;
import com.hikvision.artemis.sdk.util.MessageDigestUtil;
import com.hikvision.artemis.sdk.util.SignUtil;
import io.itit.itf.okhttp.FastHttpClient;
import io.itit.itf.okhttp.Response;
import io.itit.itf.okhttp.ssl.X509TrustManagerImpl;
import okhttp3.Call;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author chieftain
 * @date 2020-01-20 04:48
 */
public class OkHttpTest {

    final static String HTTP_SCHEMA = "https";
    final static String HOST = "218.29.98.126:10443";
    final static String ARTEMIS_PATH = "/artemis";
    final static String DEVICE_SEARCH = "/api/resource/v1/encodeDevice/search";
    final static String CAMERA_LIST = "/api/resource/v1/camera/advance/cameraList";
    final static String APP_KEY = "25154851";
    final static String APP_SECRET = "iiYGPHA2wanTo95D7MQ3";
    final static SSLContext sslContext = SSLHelper.getSslContextForCertificateFile("/cert/192.168.10.227.cer");

    public static void main(String[] args) throws Exception {
        Map<String, String> httpHeader = initHeader();
        Map<String, String> httpBody = new HashMap<>();
        httpBody.put("pageNo", "1");
        httpBody.put("pageSize", "100");

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(SSLHelper.sslSocketFactory(sslContext), new X509TrustManagerImpl())
                .connectTimeout(3, TimeUnit.SECONDS)
                .callTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.SECONDS))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .retryOnConnectionFailure(true);
        RequestBody requestBody = RequestBody.create(JSON.toJSONString(httpBody).getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(HTTP_SCHEMA.concat("://").concat(HOST).concat(ARTEMIS_PATH).concat(DEVICE_SEARCH))
                .post(requestBody)
                .headers(Headers.of(httpHeader))
                .build();
        OkHttpClient okHttpClient = builder.build();
        Call call = okHttpClient.newCall(request);
        Response response = new Response(call.execute());
        System.out.println(response.string());
        RequestBody requestBody2 = RequestBody.create(JSON.toJSONString(httpBody).getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json"));
        Request request2 = new Request.Builder()
                .url(HTTP_SCHEMA.concat("://").concat(HOST).concat(ARTEMIS_PATH).concat(DEVICE_SEARCH))
                .post(requestBody2)
                .headers(Headers.of(httpHeader))
                .build();
        OkHttpClient okHttpClient2 = builder.build();
        Call call2 = okHttpClient2.newCall(request2);
        Response response2 = new Response(call2.execute());
        System.out.println(response2.string());
    }

    public static Map<String, String> initHeader () {
        Map<String, String> httpHeader = new LinkedHashMap<>();
        httpHeader.put(HttpHeader.HTTP_HEADER_ACCEPT, "*/*");
        httpHeader.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, "application/json");
        httpHeader.put(SystemHeader.X_CA_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        httpHeader.put(SystemHeader.X_CA_NONCE, UUID.randomUUID().toString());
        httpHeader.put(SystemHeader.X_CA_KEY, APP_KEY);
        httpHeader.put(SystemHeader.X_CA_SIGNATURE, SignUtil.sign(APP_SECRET, HttpMethod.POST, ARTEMIS_PATH.concat(DEVICE_SEARCH), httpHeader, null, null, null));
        httpHeader.forEach((k, v) -> {
            httpHeader.put(k, MessageDigestUtil.utf8ToIso88591(v));
        });
        return httpHeader;
    }

    private static void fastOkHttp() throws Exception {
        Map<String, String> httpHeader = initHeader();
        Map<String, String> httpBody = new HashMap<>();
        httpBody.put("pageNo", "1");
        httpBody.put("pageSize", "100");

        Response response = FastHttpClient.newBuilder()
                .sslContext(sslContext)
                .build()
                .post()
                .url(HTTP_SCHEMA.concat("://").concat(HOST).concat(ARTEMIS_PATH).concat(DEVICE_SEARCH))
                .addHeaders(httpHeader)
                .addHeader(SystemHeader.X_CA_SIGNATURE, SignUtil.sign(APP_SECRET, HttpMethod.POST, ARTEMIS_PATH.concat(DEVICE_SEARCH), httpHeader, null, null, null))
                .body(httpBody.toString())
                .build()
                .execute();
        System.out.println(response.string());
    }
}
