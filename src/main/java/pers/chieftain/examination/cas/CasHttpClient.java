package pers.chieftain.examination.cas;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CasHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CasHttpClient.class);

    final String casServer = "http://127.0.0.1:8080/cas/login?zipcode=430000";
    final String casServerPrefix = "http://127.0.0.1:8080/cas";
    final String casDomain = "http://127.0.0.1:8080";
    final String service = "http://127.0.0.1:8080/mps";
    final String fullCasUrl = casServer + "&service=" + service;
    final String TGT_COOKIE_NAME = "CASTGC";
    final String ST_NAME = "ticket";

    private CasResponseBo<String> requestTGT(String fullCasUrl, String username, String passwd) {
        CasResponseBo<String> casResponseBo = new CasResponseBo<>();
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build()) {
            Map<String, Object> preMap = preLogin(client, fullCasUrl);
            //获取验证码
            getVerifyingCode(client, casDomain + preMap.get("captchaImgSrc").toString());

            //提醒用户并输入验证码
            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");
            String code;
            Scanner in = new Scanner(System.in);
            code = in.nextLine();
            in.close();
            HttpPost post = new HttpPost(fullCasUrl);
            post.setHeader("Cookie", preMap.get("jsessionid").toString());
            List<NameValuePair> preNvps = (List<NameValuePair>) preMap.get("nvps");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("username", username));
            nvps.add(new BasicNameValuePair("password", passwd));
            nvps.add(new BasicNameValuePair("authcode", code));
            nvps.add(new BasicNameValuePair("REMIT_KAPTCHA", "false"));
            nvps.addAll(preNvps);
            post.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Cookie cookie = getCookieValue(cookieStore, TGT_COOKIE_NAME);
                if (null == cookie) {
                    String jsonResponse = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                    if (StringUtils.isBlank(jsonResponse) && HttpStatus.SC_MOVED_TEMPORARILY == response.getStatusLine().getStatusCode()) {
                        casResponseBo.failure("长时间未操作,请重新登录");
                        return casResponseBo;
                    }
                    Document document = Jsoup.parse(jsonResponse);
                    String msg = document.getElementById("msg").text();
                    casResponseBo.failure(msg);
                    return casResponseBo;
                }
                String tgt = cookie.getValue();
                casResponseBo.success("处理成功", tgt);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            casResponseBo.failure("认证失败, 请登陆");
        }
        return casResponseBo;
    }

    private String getVerifyingCode(CloseableHttpClient client, String url) throws IOException {
        HttpGet getVerifyCode = new HttpGet(url);//验证码get
        HttpResponse response = client.execute(getVerifyCode);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        response.getEntity().writeTo(baos);
        return this.byteToBase64(baos.toByteArray());
    }

    private String byteToBase64(byte[] bytes) throws IOException {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    private CasResponseBo<Map<String, String>> requestSTByTGT(String fullCasUrl, String TGT) {
        CasResponseBo<Map<String, String>> casResponseBo = new CasResponseBo<>();
        CookieStore cookieStore = new BasicCookieStore();
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build()) {
            HttpPost httpPost = new HttpPost(fullCasUrl);
            String cookieVal = this.TGT_COOKIE_NAME + "=" + TGT;
            httpPost.setHeader("cookie", cookieVal);
            HttpResponse response = httpClient.execute(httpPost);
            String location = response.getHeaders("Location") == null ? "" : response.getHeaders("Location")[0].toString();
            if (StringUtils.isBlank(location)) {
                casResponseBo.failure("认证失败, 请登录");
                return casResponseBo;
            }
            Map<String, Object> locationParam = getParameter(location);
            Cas20ServiceTicketValidator ticketValidator = new Cas20ServiceTicketValidator(casServerPrefix);
            ticketValidator.setEncoding(Charset.forName("GB2312").name());
            Assertion assertion = ticketValidator.validate(String.valueOf(locationParam.get(ST_NAME)), service);
            List<String> keys = (List<String>) assertion.getPrincipal().getAttributes().get("name");
            List<String> vals = (List<String>) assertion.getPrincipal().getAttributes().get("value");
            Map<String, String> map = new HashMap<>();
            for (int i = 0, size = keys.size(); i < size; i++) {
                map.put(keys.get(i), vals.get(i));
            }
            casResponseBo.success("处理成功", map);
        } catch (IOException | TicketValidationException e) {
            LOGGER.error(e.getMessage(), e);
            casResponseBo.failure("认证失败, 请登陆");
        }
        return casResponseBo;
    }


    private Cookie getCookieValue(CookieStore cookieStore, String cookieName) {
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            return null;
        } else {
            for (Cookie cookie : cookies) {
                if (cookieName.equalsIgnoreCase(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    private Map<String, Object> preLogin(CloseableHttpClient httpclient, String url) throws IOException {
        Map<String, Object> map = new HashMap<>();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        Header[] headers = response.getHeaders("Set-Cookie");
        String jsessionid = headers[0].getValue().substring(0, headers[0].getValue().indexOf(";"));
        HttpEntity entity = response.getEntity();
        BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8));
        StringBuilder htmlBuilder = new StringBuilder();
        String tempLine = rd.readLine();
        while (tempLine != null) {
            if (StringUtils.isNotBlank(tempLine)) {
                htmlBuilder.append("\n").append(tempLine);
            }
            tempLine = rd.readLine();
        }
        Document document = Jsoup.parse(htmlBuilder.toString());
        Elements inputs = document.getElementsByTag("input");
        List<NameValuePair> nvps = new ArrayList<>();
        inputs.forEach(e -> {
            if ("hidden".equals(e.attr("type")) || "submit".equals(e.attr("type"))) {
                nvps.add(new BasicNameValuePair(e.attr("name"), e.val()));
            }
        });
        map.put("jsessionid", jsessionid);
        map.put("captchaImgSrc", document.getElementById("captchaImg").attr("src"));
        map.put("nvps", nvps);
        return map;
    }


    private javax.servlet.http.Cookie convertToServletCookie(Cookie cookie) {
        javax.servlet.http.Cookie retCookie = new javax.servlet.http.Cookie(cookie.getName(), cookie.getValue());
        retCookie.setComment(cookie.getComment());
        retCookie.setDomain(cookie.getDomain());
        retCookie.setHttpOnly(false);
        retCookie.setSecure(false);
        retCookie.setPath(cookie.getPath());
        retCookie.setVersion(cookie.getVersion());
        retCookie.setMaxAge((int) ((cookie.getExpiryDate().getTime() - System.currentTimeMillis()) / 1000));
        return retCookie;
    }

    public void setResponseCookie(HttpServletResponse response, Cookie cookie) {
        javax.servlet.http.Cookie retCookie = this.convertToServletCookie(cookie);
        response.addCookie(retCookie);
    }

    public String obtainTGTByRequest(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        for (javax.servlet.http.Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public Map<String, Object> getParameter(String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (String keyValue : keyValues) {
                    String key = keyValue.substring(0, keyValue.indexOf("="));
                    String value = keyValue.substring(keyValue.indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        CasHttpClient casHttpClient = new CasHttpClient();
        CasResponseBo<String> TGTResponse = casHttpClient.requestTGT(casHttpClient.fullCasUrl, "admin", "123456");
        if (!TGTResponse.isStatus()) {
            LOGGER.error(TGTResponse.getMsg());
            return;
        }
        String TGT = TGTResponse.getData();
        CasResponseBo<Map<String, String>> STResponse = casHttpClient.requestSTByTGT(casHttpClient.fullCasUrl, TGT);
        if (!STResponse.isStatus()) {
            LOGGER.error(STResponse.getMsg());
            return;
        }
        System.out.println(STResponse.getData());
    }

}