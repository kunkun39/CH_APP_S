package com.changhong.system.web.facade.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午2:47
 */
public class AppChangeHistoryDTO {

    private String changePerson;

    private String changeDate;

    private List<AppChangeDetailsDTO> fieldDetails;

    public AppChangeHistoryDTO(String changePerson, String changeDate) {
        this.changePerson = changePerson;
        this.changeDate = changeDate;
    }

    public void addChangeDetails(AppChangeDetailsDTO details) {
        if (fieldDetails == null) {
            fieldDetails = new ArrayList<AppChangeDetailsDTO>();
        }
        fieldDetails.add(details);
    }

    public String getChangePerson() {
        return changePerson;
    }

    public void setChangePerson(String changePerson) {
        this.changePerson = changePerson;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public List<AppChangeDetailsDTO> getFieldDetails() {
        return fieldDetails;
    }

    public void setFieldDetails(List<AppChangeDetailsDTO> fieldDetails) {
        this.fieldDetails = fieldDetails;
    }
}
