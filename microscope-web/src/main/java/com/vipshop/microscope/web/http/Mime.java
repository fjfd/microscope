package com.vipshop.microscope.web.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：获取请求的mime信息
 *
 * @author: dashu
 * @since: 13-3-1
 */
public class Mime {

    private static Mime mime;
    private static Map<String, String> mimes;

    private Mime(){
        mimes = new HashMap<String, String>();
        mimes.put("doc","application/msword");
        mimes.put("exe","application/octet-stream");
        mimes.put("js","application/javascript");
        mimes.put("zip","application/zip");
        mimes.put("aiff","audio/aiff");
        mimes.put("midi","audio/midi");
        mimes.put("mp3","audio/mpeg3");
        mimes.put("wav","audio/wav");
        mimes.put("gif","image/gif");
        mimes.put("jpg","image/jpeg");
        mimes.put("png","image/png");
        mimes.put("tiff","image/tiff");
        mimes.put("css","text/css");
        mimes.put("html","text/html; charset=UTF-8");
        mimes.put("txt","text/plain; charset=UTF-8");
        mimes.put("xml","text/xml");
        mimes.put("avi","video/avi");
        mimes.put("mov","video/quicktime");
        mimes.put("mpeg","video/mpeg");
        mimes.put("mp4","video/mp4");
    }

    public synchronized static String get(String fileType){
        if(mime == null){
            mime = new Mime();
        }
        return mimes.get(fileType);
    }

}
