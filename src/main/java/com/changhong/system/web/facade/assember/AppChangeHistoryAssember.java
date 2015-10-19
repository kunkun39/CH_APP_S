package com.changhong.system.web.facade.assember;

import com.changhong.common.utils.CHDateUtils;
import com.changhong.system.domain.AppChangeDetails;
import com.changhong.system.domain.AppChangeHistory;
import com.changhong.system.web.facade.dto.AppChangeDetailsDTO;
import com.changhong.system.web.facade.dto.AppChangeHistoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-9-1
 * Time: 下午5:00
 */
public class AppChangeHistoryAssember {

    public static AppChangeHistoryDTO toAppChangeHistoryDTO(AppChangeHistory history) {
        final String changeUser = history.getUser().getName();
        final String changeDate = CHDateUtils.getFullDateFormat(history.getTimestamp());
        AppChangeHistoryDTO historyDTO =  new AppChangeHistoryDTO(changeUser, changeDate);

        List<AppChangeDetails> fieldDetails = history.getFieldDetails();
        if(fieldDetails != null && !fieldDetails.isEmpty()) {
            for (AppChangeDetails fieldDetail : fieldDetails) {
                AppChangeDetailsDTO detailsDTO = new AppChangeDetailsDTO(fieldDetail.getField(), fieldDetail.getDescription());
                historyDTO.addChangeDetails(detailsDTO);
            }
        }
        return historyDTO;
    }

    public static List<AppChangeHistoryDTO> toAppChangeHistoryDTOList(List<AppChangeHistory> histories) {
        List<AppChangeHistoryDTO> dtos = new ArrayList<AppChangeHistoryDTO>();
        if (histories != null) {
            for (AppChangeHistory history : histories) {
                dtos.add(toAppChangeHistoryDTO(history));
            }
        }
        return dtos;
    }
}
