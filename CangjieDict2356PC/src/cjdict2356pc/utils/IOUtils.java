package cjdict2356pc.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

    // 注意\\t之前還有一個字符，没有顯示出来，不要看漏了
    private static String blankPatn = "[﻿\\t\\n\\x0B\\f\\r]";

    public static List<String> readLines(String fileName) throws IOException {
        InputStream in = IOUtils.class.getResourceAsStream(fileName);
        return readLines(in);
    }

    public static List<String> readLines(InputStream in) {
        List<String> result = new ArrayList<String>();
        InputStreamReader isr = null;
        BufferedReader br = null;
        String str = null;
        try {
            isr = new InputStreamReader(in, "UTF-8");
            br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                if (!"".equals(str)) {
                    String line = str.trim(); // 去首尾
                    line = line.replaceAll("( )\\1+", "$1"); // 中间一个空
                    line = line.replaceAll(blankPatn, ""); // 去空白字符
                    result.add(line);
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    /**
     * 文件複製
     * 
     * @author t
     * @time 2016-12-18上午11:01:06
     */
    public static void copyFile(InputStream src, OutputStream dest)
            throws Exception {
        BufferedInputStream bis = new BufferedInputStream(src);
        BufferedOutputStream bfos = new BufferedOutputStream(dest);
        byte[] buffer = new byte[10240];
        int byteCount = 0;
        while ((byteCount = bis.read(buffer)) != -1) {
            bfos.write(buffer, 0, byteCount);
        }
        bfos.flush();
        bis.close();
        bfos.close();
    }

    /**
     * 是否相同文件
     * 
     * @author t
     * @throws Exception
     * @time 2017-1-26下午9:34:12
     */
    public static boolean isSameFile(InputStream src, InputStream dest)
            throws Exception {
        boolean same = true;
        BufferedInputStream file1 = new BufferedInputStream(src);
        BufferedInputStream file2 = new BufferedInputStream(dest);
        if (file1.available() != file2.available()) {
            same = false;
//        } else {
//            while (file1.read() != -1 && file2.read() != -1) {
//                if (file1.read() != file2.read()) {
//                    same = false;
//                    break;
//                }
//            }
        }
        file1.close();
        file2.close();
        return same;
    }
}
