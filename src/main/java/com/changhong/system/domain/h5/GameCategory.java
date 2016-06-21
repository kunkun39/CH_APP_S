package com.changhong.system.domain.h5;

/**
 * User: Jack Wang
 * Date: 16-6-21
 * Time: 下午6:07
 */
public enum GameCategory {

    XIU_XIAN("休闲"),
    SHE_JI("射击"),
    ACTION("动作");

    private String description;

    GameCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
