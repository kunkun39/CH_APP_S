package com.changhong.system.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 下午2:33
 */
public class AppFile extends Document {

    public AppFile() {
    }

    public AppFile(MultipartFile file, String actualFilePath) {
        super(file, actualFilePath);
    }
}
