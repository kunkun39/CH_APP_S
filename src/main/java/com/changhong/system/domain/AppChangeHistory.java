package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午2:47
 */
public class AppChangeHistory extends EntityBase {

    private MarketApp app;

    private User user;

    private List<AppChangeDetails> fieldDetails;

    public void addChangeDetails(AppChangeDetails details) {
        if (fieldDetails == null) {
            fieldDetails = new ArrayList<AppChangeDetails>();
        }
        fieldDetails.add(details);
    }

    public MarketApp getApp() {
        return app;
    }

    public void setApp(MarketApp app) {
        this.app = app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AppChangeDetails> getFieldDetails() {
        return fieldDetails;
    }

    public void setFieldDetails(List<AppChangeDetails> fieldDetails) {
        this.fieldDetails = fieldDetails;
    }
}
