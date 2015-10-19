package com.changhong.system.web.facade.assember;

import com.changhong.common.repository.EntityLoadHolder;
import com.changhong.system.domain.AppCategory;
import com.changhong.system.domain.CategoryIcon;
import com.changhong.system.web.facade.dto.AppCategoryDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:41
 */
public class AppCategoryWebAssember {

    public static AppCategory toAppCategoryDomain(int categoryId, String categoryName, int parentId) {
        AppCategory category = null;
        if (categoryId > 0) {
            category = (AppCategory) EntityLoadHolder.getUserDao().findById(categoryId, AppCategory.class);
            category.setCategoryName(categoryName);
        } else {
            category = new AppCategory(categoryName);
        }

        if (parentId > 0) {
            AppCategory parent = new AppCategory();
            parent.setId(parentId);
            category.setParent(parent);
        }
        return category;
    }

    public static AppCategoryDTO toAppCategoryDTO(AppCategory category, boolean includeChildren) {
        final int id = category.getId();
        final String categoryName = category.getCategoryName();
        AppCategory parent = category.getParent();
        final int parentId = parent == null ? -1 : parent.getId();

        int categoryIconId = -1;
        String categoryIconName = "";
        CategoryIcon icon = category.getCategoryIcon();
        if (icon != null) {
            categoryIconId = icon.getId();
            categoryIconName = icon.getActualFileName();
        }

        boolean includeChild = false;
        List<AppCategory> childrenD = category.getChildren();
        if (childrenD != null && !childrenD.isEmpty()) {
            includeChild = true;
        }

        AppCategoryDTO dto =  new AppCategoryDTO(id, categoryName, parentId, categoryIconId, categoryIconName, includeChild);

        if (includeChildren) {
            List<AppCategory> children = childrenD;
            if (children != null) {
                dto.setChildren(toAppCategoryDTOList(children, false));
            }
        }
        return dto;
    }

    public static List<AppCategoryDTO> toAppCategoryDTOList(List<AppCategory> categories, boolean includeChildren) {
        List<AppCategoryDTO> dtos = new ArrayList<AppCategoryDTO>();
        if (categories != null) {
            for (AppCategory category : categories) {
                dtos.add(toAppCategoryDTO(category, includeChildren));
            }
        }
        return dtos;
    }
}
