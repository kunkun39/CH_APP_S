package com.changhong.system.domain.h5;

import com.changhong.system.domain.Document;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 14-4-10
 * Time: 下午3:06
 */
public class GamePosterShu extends Document {

    public GamePosterShu() {
    }

    public GamePosterShu(MultipartFile file, String actualFilePath) {
        super(file, actualFilePath);
    }
}
