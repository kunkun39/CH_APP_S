package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import com.changhong.common.utils.CHStringUtils;
import com.changhong.common.utils.JodaUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by IntelliJ IDEA.
 * User: dangwei
 * Date: 15-9-30
 * Time: 下午1:38
 * To change this template use File | Settings | File Templates.
 */
public class ClientBootImage extends EntityBase{

    private int id;

    private String actualFileName;


    /***********************************************GET/SET******************************************************/

    public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

    public String getActualFileName() {
        return actualFileName;
    }

    public void setActualFileName(String actualFileName) {
        this.actualFileName = actualFileName;
    }

}

