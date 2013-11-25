package com.vipshop.microscope.web.server.handle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

import com.vipshop.microscope.web.server.Session;
import com.vipshop.microscope.web.server.cons.Constant;

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

        File file = new File(Constant.ROOTPATH, session.getRequestPath());
        FileChannel from = null;
        try {
            from = new FileInputStream(file).getChannel();
            ByteBuffer bb = ByteBuffer.allocate(1024);
            bb.clear();

            // 返回头信息
            StringBuffer sb = new StringBuffer();
            sb.append("HTTP/1.1 200 OK").append(Constant.CRLF);
            sb.append("Content-Type: ").append(session.getMime()).append(Constant.CRLF);
            sb.append("Content-Length: ").append(file.length()).append(Constant.CRLF);
            sb.append(Constant.CRLF);
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
            sb.append("HTTP/1.1 404 Not Found").append(Constant.CRLF);
            sb.append("Content-Type: ").append("text/html").append(Constant.CRLF);
            sb.append(Constant.CRLF);
            sb.append("404");
            bb.put(sb.toString().getBytes());
            bb.flip();
            try {
                channel.write(bb);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            sb.append(Constant.CRLF);
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
