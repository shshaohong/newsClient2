package com.edu.feicui.newsclient;


import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by Administrator on 2016-12-9.
 */

public class Md5 {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static MessageDigest messageDigest;

    static{
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String md5(String password){
        //messageDigest.update(password.getBytes());
        //byte[] data = messageDigest.digest();
        byte[] data = messageDigest.digest(password.getBytes());
        return byteToHex(data);
    }

    public static String md5(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;

            while((len = fis.read(buffer)) > 0){
                messageDigest.update(buffer, 0, len);
            }
            fis.close();
            buffer = messageDigest.digest();
            String result = byteToHex(buffer);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String byteToHex(byte[] data,int offset,int length){
        StringBuffer sb = new StringBuffer();
        for(int i = offset;i < offset + length;i++){
            byte b = data[i];
            String s = ""+HEX_DIGITS[(b & 0xf0)>>>4] + HEX_DIGITS[b & 0x0f];
            sb.append(s);
        }
        return sb.toString();
    }

    private static String byteToHex(byte[] data){
        StringBuffer sb = new StringBuffer();
        for(byte b : data){
            String s = ""+HEX_DIGITS[(b & 0xf0)>>>4] + HEX_DIGITS[b & 0x0f];
            sb.append(s);
        }
        return sb.toString();
    }
}
