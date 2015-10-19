package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午2:48
 */
public class AppChangeDetails extends EntityBase {

    private String field;

    private String description;

    public AppChangeDetails() {
    }

    public AppChangeDetails(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
