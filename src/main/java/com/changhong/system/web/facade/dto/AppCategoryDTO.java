package com.changhong.system.web.facade.dto;

import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-30
 * Time: 上午9:39
 */
public class AppCategoryDTO {

    private int id;

    private String categoryName;

    private int categoryIconId;

    private String categoryIconName;

    private List<AppCategoryDTO> children;

    private int parentId;

    private boolean includeChild = true;

    public AppCategoryDTO() {
    }

    public AppCategoryDTO(int id, String categoryName, int parentId, int categoryIconId, String categoryIconName, boolean includeChild) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentId = parentId;
        this.categoryIconId = categoryIconId;
        this.categoryIconName = categoryIconName;
        this.includeChild = includeChild;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<AppCategoryDTO> getChildren() {
        return children;
    }

    public void setChildren(List<AppCategoryDTO> children) {
        this.children = children;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCategoryIconId() {
        return categoryIconId;
    }

    public void setCategoryIconId(int categoryIconId) {
        this.categoryIconId = categoryIconId;
    }

    public String getCategoryIconName() {
        return categoryIconName;
    }

    public void setCategoryIconName(String categoryIconName) {
        this.categoryIconName = categoryIconName;
    }

    public boolean isIncludeChild() {
        return includeChild;
    }

    public void setIncludeChild(boolean includeChild) {
        this.includeChild = includeChild;
    }
}
