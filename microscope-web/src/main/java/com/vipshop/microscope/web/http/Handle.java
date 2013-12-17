package com.vipshop.microscope.web.http;

/**
 * 描述：处理事务的类<br>
 * 实现了Runnable接口,抽象类，必须继承
 *
 * @author: dashu
 * @since: 13-3-4
 */
public abstract class Handle implements Runnable{

    protected Session session;

    public Handle(Session session) {
        this.session = session;
    }

}
