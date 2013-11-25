package com.vipshop.microscope.web.server.handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.StringTokenizer;

import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.web.server.Session;
import com.vipshop.microscope.web.server.cons.Constant;

/**
 * 描述：处理请求<br>
 *     判断请求类型
 *
 * @author: dashu
 * @since: 13-3-4
 */
public class AnalyseHandle extends Handle {

    public AnalyseHandle(Session session) {
        super(session);
    }

    @Override
    public void run() {
        SocketChannel sc = (SocketChannel) session.getKey().channel();
        session.setChannel(sc);
        // 请求信息
        StringBuffer headSB = new StringBuffer();
        ByteBuffer bb = ByteBuffer.allocate(1024);
        int count = 0;

        try {
            // 读取请求头信息
            while (true) {
                bb.clear();
                count = sc.read(bb);
                if (count == 0) {
                    break;
                }
                if (count < 0) {
                    // 连接已经中断
                    session.close();
                    return;
                }
                bb.flip();
                byte[] data = new byte[count];
                bb.get(data, 0, count);
                headSB.append(new String(data));
            }
//            System.out.println("head:\n"+headSB.toString().trim());
            // 解析请求
            StringTokenizer st = new StringTokenizer(headSB.toString(), Constant.CRLF);

            String line = st.nextToken();

            // 请求方法路径等
            StringTokenizer http = new StringTokenizer(line, " ");
            session.setMethod(http.nextToken()); // 方法

            String get = http.nextToken();
            int index = get.indexOf('?');
            if (index > 0) {
                session.setRequestPath(get.substring(0, index)); // 路径
//                path = decoder.decode(get.substring(0, index), "UTF-8");
//                parameters = get.substring(index + 1);
            } else {
                session.setRequestPath(get);
//                path = decoder.decode(get, "UTF-8");
//                parameters = null;
            }

            line = st.nextToken();

            while (line != null && !line.equals("")) {
                int colon = line.indexOf(":");
                if (colon > -1) {
                    String key = line.substring(0, colon).toLowerCase();
                    String value = line.substring(colon + 1).trim();
                    session.head(key, value);
                }
                if (st.hasMoreElements()) {
                    line = st.nextToken();
                } else {
                    break;
                }
            }

            StaticHandle sh = new StaticHandle(session);
            ThreadPoolUtil.getHttpServerExecutor().execute(sh);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}