package com.example.apputils.utils;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @Description: Base64Util
 * @author: wangwc
 * @date: 2020/11/27 17:08
 */
public class Base64Util {
    public Base64Util() {
    }

    public static String byteToBase64String(byte[] binaryData) {
        String returnString = "";

        try {
            if (null != binaryData && binaryData.length > 0) {
                returnString = new String(Base64.encodeBase64(binaryData), "utf-8");
            }
        } catch (Exception var3) {
        }

        return returnString;
    }

    public static byte[] base64StringToByte(String stringData) {
        byte[] returnBytes = null;
        if (null != stringData && !"".equals(stringData)) {
            returnBytes = Base64.decodeBase64(stringData);
        }

        return returnBytes;
    }
}
