package com.changhong.system.web.facade.dto;

import com.changhong.common.utils.CHStringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午10:37
 */
public class ClientDTO implements Serializable {

    private int id = -1;

    private String macFrom;

    private String macTo;

    private String note;

    private String createTime;

    public ClientDTO() {
    }

    public ClientDTO(int id, String macFrom, String macTo, String note, String createTime) {
        this.id = id;
        this.macFrom = macFrom;
        this.macTo = macTo;
        this.note = note;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
