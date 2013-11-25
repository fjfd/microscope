package com.vipshop.microscope.web.server;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Session {

    /**
     * 请求Key
     */
    private SelectionKey key;
    /**
     * 通道
     */
    private SocketChannel channel;
    /**
     * 应答类型
     */
    private String mime;
    /**
     * 请求路径
     */
    private String requestPath;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求头信息
     */
    private Map<String, String> heads = new HashMap<String, String>();

    public Session(SelectionKey key) throws IOException {
        channel = ((ServerSocketChannel) key.channel()).accept();
        channel.configureBlocking(false);

        key = channel.register(key.selector(), SelectionKey.OP_READ, this);
        key.selector().wakeup();

        this.key = key;
    }

    /**
     * 放入头信息
     * @param key
     * @param value
     */
    public void head(String key, String value){
        heads.put(key, value);
    }

    /**
     * 取出头信息
     * @param key
     * @return
     */
    public String head(String key){
        return heads.get(key);
    }

    /**
     * 关闭通道
     */
    public void close(){
        if (channel != null && channel.isOpen())
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public String getMime() {
        if (mime == null) {
            int index = requestPath.indexOf(".");
            String m = requestPath.substring(index+1);
            mime = Mime.get(m);
        }
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
