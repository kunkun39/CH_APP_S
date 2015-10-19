package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-9-21
 * Time: 上午11:22
 */
public class BoxRecommendRecord extends EntityBase {

    private int changeUserId;

    private String changeUsername;

    private boolean commit;

    public void addChangeLock(int userId, String username) {
        this.changeUserId = userId;
        this.changeUsername = username;
        this.commit = false;
    }

    public void releaseChangeLock() {
        this.changeUserId = -1;
        this.changeUsername = "";
        this.commit = true;
    }

    public int getChangeUserId() {
        return changeUserId;
    }

    public void setChangeUserId(int changeUserId) {
        this.changeUserId = changeUserId;
    }

    public String getChangeUsername() {
        return changeUsername;
    }

    public void setChangeUsername(String changeUsername) {
        this.changeUsername = changeUsername;
    }

    public boolean isCommit() {
        return commit;
    }

    public void setCommit(boolean commit) {
        this.commit = commit;
    }
}
