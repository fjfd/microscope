package com.vipshop.microscope.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    public static final int MD5_LENGTH = 16;

    public static byte[] md5sum(String s) {
        MessageDigest d;
        try {
            d = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available!", e);
        }

        return d.digest(s.getBytes());
    }

}