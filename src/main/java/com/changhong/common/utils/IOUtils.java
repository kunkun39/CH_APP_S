package com.changhong.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Jack Wang
 * Date: 14-4-15
 * Time: 下午12:53
 */
public class IOUtils {

    public static void copyStringToFile(String content, File f) {
        FileWriter fw = null;

        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(content, 0, content.length() - 1);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
