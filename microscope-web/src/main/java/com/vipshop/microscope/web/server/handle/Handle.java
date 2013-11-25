package com.vipshop.microscope.web.server.handle;

import com.vipshop.microscope.web.server.Session;

public abstract class Handle implements Runnable{

    protected Session session;

    public Handle(Session session) {
        this.session = session;
    }

}
