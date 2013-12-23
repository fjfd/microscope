package com.vipshop.micorscope.framework.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 描述：
 *
 * @author: dashu
 * @since: 13-3-4
 */
public class StaticHandle extends Handle {

    /**
     * 静态路径
     */
    private String staticPath;

    public StaticHandle(Session session) {
        super(session);
    }

    @SuppressWarnings("resource")
	@Override
    public void run() {

        // 获取channel
        SocketChannel channel = session.getChannel();
        if (!channel.isOpen())
            return;

        File file = new File(Sysconst.ROOTPATH, session.getRequestPath());
        FileChannel from = null;
        try {
            from = new FileInputStream(file).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.clear();

            // 返回头信息
            StringBuffer sb = new StringBuffer();
            sb.append("HTTP/1.1 200 OK").append(Sysconst.CRLF);
            sb.append("Content-Type: ").append(session.getMime()).append(Sysconst.CRLF);
            sb.append("Content-Length: ").append(file.length()).append(Sysconst.CRLF);
            sb.append(Sysconst.CRLF);
            System.out.println(sb.toString());
            bb.put(sb.toString().getBytes());
            bb.flip();
            channel.write(bb);

            bb = from.map(FileChannel.MapMode.READ_ONLY, 0, from.size());
            channel.write(bb);
        } catch (FileNotFoundException e) {
            // 404
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.clear();
            StringBuffer sb = new StringBuffer();
            sb.append("HTTP/1.1 404 Not Found").append(Sysconst.CRLF);
            sb.append("Content-Type: ").append("text/html").append(Sysconst.CRLF);
            sb.append(Sysconst.CRLF);
            sb.append("404");
            bb.put(sb.toString().getBytes());
            bb.flip();
            try {
                channel.write(bb);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sb.append(Sysconst.CRLF);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }
}
