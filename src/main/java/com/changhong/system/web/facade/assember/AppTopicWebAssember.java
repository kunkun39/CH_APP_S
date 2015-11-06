package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.AppTopic;
import com.changhong.system.domain.CategoryIcon;
import com.changhong.system.web.facade.dto.AppTopicDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:41
 */
public class AppTopicWebAssember {

    public static AppTopic toAppTopicDomain(int topicId, String topicName) {
        AppTopic appTopic = null;
        if (topicId > 0) {
            appTopic = (AppTopic) EntityLoadHolder.getUserDao().findById(topicId, AppTopic.class);
            appTopic.setTopicName(topicName);
        } else {
            appTopic = new AppTopic(topicName);
        }

        return appTopic;
    }

    public static AppTopicDTO toAppTopicDTO(AppTopic topic) {
        final int id = topic.getId();
        final String topicName = topic.getTopicName();

        int topicIconId = -1;
        String topicIconName = "";
        CategoryIcon icon = topic.getCategoryIcon();
        if (icon != null) {
            topicIconId = icon.getId();
            topicIconName = icon.getActualFileName();
        }

        AppTopicDTO dto =  new AppTopicDTO(id, topicName, topicIconId, topicIconName);
        return dto;
    }

    public static List<AppTopicDTO> toAppTopicDTOList(List<AppTopic> topics) {
        List<AppTopicDTO> dtos = new ArrayList<AppTopicDTO>();
        if (topics != null) {
            for (AppTopic topic : topics) {
                dtos.add(toAppTopicDTO(topic));
            }
        }
        return dtos;
    }
}
