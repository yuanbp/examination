package com.chieftain.examination;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chieftain
 * @date 2019-11-09 18:40
 */
public class UrlFileDown {

    public static void main(String[] args) {
        try{
            downLoadFromUrl("https://tfs.alipayobjects.com/images/partner/TB1m6qRbsRCDuNjm2EsXXadrFXa",
                    "TB1m6qRbsRCDuNjm2EsXXadrFXa","/Users/chieftain/Downloads");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    static Map<String, String> map = new HashMap<>();

    static {
        map.put("audio/aac",".aac");
        map.put("application/x-abiword",".abw");
        map.put("application/x-freearc",".arc");
        map.put("video/x-msvideo",".avi");
        map.put("application/vnd.amazon.ebook",".azw");
        map.put("application/octet-stream",".bin");
        map.put("image/bmp",".bmp");
        map.put("application/x-bzip",".bz");
        map.put("application/x-bzip2",".bz2");
        map.put("application/x-csh",".csh");
        map.put("text/css",".css");
        map.put("text/csv",".csv");
        map.put("application/msword",".doc");
        map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",".docx");
        map.put("application/vnd.ms-fontobject",".eot");
        map.put("application/epub+zip",".epub");
        map.put("image/gif",".gif");
        map.put("text/html",".htm");
        map.put("image/vnd.microsoft.icon",".ico");
        map.put("text/calendar",".ics");
        map.put("application/java-archive",".jar");
        map.put("image/jpeg",".jpeg");
        map.put("text/javascript",".js");
        map.put("application/json",".json");
        map.put("application/ld+json",".jsonld");
        map.put("audio/midi",".mid");
        map.put("audio/x-midi",".mid");
        map.put("audio/mpeg",".mp3");
        map.put("video/mpeg",".mpeg");
        map.put("application/vnd.apple.installer+xml",".mpkg");
        map.put("application/vnd.oasis.opendocument.presentation",".odp");
        map.put("application/vnd.oasis.opendocument.spreadsheet",".ods");
        map.put("application/vnd.oasis.opendocument.text",".odt");
        map.put("audio/ogg",".oga");
        map.put("video/ogg",".ogv");
        map.put("application/ogg",".ogx");
        map.put("font/otf",".otf");
        map.put("image/png",".png");
        map.put("application/pdf",".pdf");
        map.put("application/vnd.ms-powerpoint",".ppt");
        map.put("application/vnd.openxmlformats-officedocument.presentationml.presentation",".pptx");
        map.put("application/x-rar-compressed",".rar");
        map.put("application/rtf",".rtf");
        map.put("application/x-sh",".sh");
        map.put("image/svg+xml",".svg");
        map.put("application/x-shockwave-flash",".swf");
        map.put("application/x-tar",".tar");
        map.put("image/tiff","tiff");
        map.put("font/ttf",".ttf");
        map.put("text/plain",".txt");
        map.put("application/vnd.visio",".vsd");
        map.put("audio/wav",".wav");
        map.put("audio/webm",".weba");
        map.put("video/webm",".webm");
        map.put("image/webp",".webp");
        map.put("font/woff",".woff");
        map.put("font/woff2",".woff2");
        map.put("application/xhtml+xml",".xhtml");
        map.put("application/vnd.ms-excel",".xls");
        map.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",".xlsx");
        map.put("application/xml",".xml");
        map.put("text/xml",".xml");
        map.put("application/vnd.mozilla.xul+xml",".xul");
        map.put("application/zip",".zip");
        map.put("video/3gpp",".3gp");
        map.put("audio/3gpp",".3gp");
        map.put("video/3gpp2",".3g2");
        map.put("audio/3gpp2",".3g2");
        map.put("application/x-7z-compressed",".7z");
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
//        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        String fileSuffix = map.get(conn.getContentType());
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+ File.separator+fileName.concat(fileSuffix));
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success");

    }



    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
