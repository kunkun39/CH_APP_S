package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;

/**
 * User: Jack Wang
 * Date: 15-8-11
 * Time: 上午9:24
 */
public class Client extends EntityBase {

    private String macFrom;

    private String macTo;

    private String note;

    public Client() {
    }

    public Client(String macFrom, String macTo, String note) {
        this.macFrom = macFrom;
        this.macTo = macTo;
        this.note = note;
    }

    public String getMacFrom() {
        return macFrom;
    }

    public void setMacFrom(String macFrom) {
        this.macFrom = macFrom;
    }

    public String getMacTo() {
        return macTo;
    }

    public void setMacTo(String macTo) {
        this.macTo = macTo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
