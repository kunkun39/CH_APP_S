package com.changhong.system.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:06
 */
public class CategoryIcon extends Document {

    public CategoryIcon() {
    }

    public CategoryIcon(MultipartFile file, String actualFilePath) {
        super(file, actualFilePath);
    }
}
