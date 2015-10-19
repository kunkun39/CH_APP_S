package com.changhong.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:12
 */
public class EntityBase implements Serializable {

    private int id;

    private Date timestamp;

    protected EntityBase() {
        this.timestamp = new Date();
    }

    /**
     * *******************************************************GETTER/SETTER****************************************
     */

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
