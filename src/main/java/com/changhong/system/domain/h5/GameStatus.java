package com.changhong.system.domain.h5;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 下午2:34
 */
public enum GameStatus {
    CREAETED("待审核"),
    PASSED("已上架"),
    REJECTED("已拒绝"),
    OFFSHELVES("已下架");

    private String description;

    GameStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
