package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-29
 * Time: 下午5:35
 */
public class AppTopic extends EntityBase {

    private String topicName;

    private CategoryIcon categoryIcon;

    public AppTopic() {
    }

    public AppTopic(int id) {
        setId(id);
    }

    public AppTopic(String topicName) {
        this.topicName = topicName;
    }

    /****************************************************GET/SET*******************************************************/

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public CategoryIcon getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(CategoryIcon categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    @Override
    public boolean equals(Object o) {
        AppTopic topic = (AppTopic) o;

        return this.getId() == topic.getId();
    }
}
