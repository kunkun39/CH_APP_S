package com.changhong.system.domain.h5;

import com.changhong.system.domain.Document;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 下午2:33
 */
public class GameFile extends Document {

    public GameFile() {
    }

    public GameFile(MultipartFile file, String actualFilePath) {
        super(file, actualFilePath);
    }

    public void setInfo(GameFile appFile) {
        setActualFileName(appFile.getActualFileName());
        setActualFilePath(appFile.getActualFilePath());
        setUploadFileName(appFile.getUploadFileName());
        setUploadTime(appFile.getUploadTime());
    }
}
