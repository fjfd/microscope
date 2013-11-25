package com.vipshop.microscope.web.server.cons;

import com.vipshop.microscope.common.cfg.Configuration;

/**
 * 描述：系统常量
 *
 * @author: dashu
 * @since: 13-4-1
 */
public class Constant {

	private static final Configuration config = Configuration.getConfiguration("web.properties");

    public static String ROOTPATH = config.getString("root_path");

    public static final String CRLF = "\r\n";

}
