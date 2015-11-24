package com.changhong.system.web.facade.dto;

import java.io.Serializable;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:39
 */
public class AppTopicDTO implements Serializable {

    private int id;

    private String topicName;

    private int topicIconId;

    private String topicIconName;

    public AppTopicDTO() {
    }

    public AppTopicDTO(int id, String topicName, int topicIconId, String topicIconName) {
        this.id = id;
        this.topicName = topicName;
        this.topicIconId = topicIconId;
        this.topicIconName = topicIconName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getTopicIconId() {
        return topicIconId;
    }

    public void setTopicIconId(int topicIconId) {
        this.topicIconId = topicIconId;
    }

    public String getTopicIconName() {
        return topicIconName;
    }

    public void setTopicIconName(String topicIconName) {
        this.topicIconName = topicIconName;
    }
}
