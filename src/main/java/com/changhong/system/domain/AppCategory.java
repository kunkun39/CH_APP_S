package com.changhong.system.domain;

import com.changhong.common.domain.EntityBase;
import org.apache.log4j.Category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Jack Wang
 * Date: 15-7-29
 * Time: 下午5:35
 */
public class AppCategory extends EntityBase {

    private String categoryName;

    private CategoryIcon categoryIcon;

    private AppCategory parent;

    private List<AppCategory> children;

    public AppCategory() {
    }

    public AppCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isLeaf() {
        if (children == null || children.isEmpty()) {
            return true;
        }
        return false;
    }

    public List<AppCategory> getAllCategoryBelow() {
		List<AppCategory> all = new ArrayList<AppCategory>();
		List<AppCategory> children = this.getChildren();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				AppCategory category = (AppCategory) i.next();
				all.addAll(category.getAllCategoryBelow());
			}
		}
		return all;
	}

    public List<AppCategory> getAllCategoryBelowWithItself() {
		List<AppCategory> all = new ArrayList<AppCategory>();
        all.add(this);
		List<AppCategory> children = this.getChildren();
		if (children != null) {
			all.addAll(children);
			Iterator i = children.iterator();
			while (i.hasNext()) {
				AppCategory category = (AppCategory) i.next();
				all.addAll(category.getAllCategoryBelow());
			}
		}
		return all;
	}

    public List getAllCategoryAbove() {
		List<AppCategory> all = new ArrayList<AppCategory>();
		AppCategory parent = this.getParent();
		if (parent != null) {
			all.addAll(parent.getAllCategoryAbove());
			all.add(parent);
		}
		return all;
	}

    public List<AppCategory> getAllSitesAboveWithItself() {
		List<AppCategory> all = new ArrayList<AppCategory>();
		AppCategory parent = this.getParent();
		if (parent != null) {
			all.addAll(parent.getAllCategoryAbove());
			all.add(parent);
		}
        all.add(this);
		return all;
	}

    public String getFullCategoryPath() {
        List<AppCategory> all = getAllSitesAboveWithItself();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < all.size(); i++) {
            AppCategory category = all.get(i);
            if (i == 0) {
                builder.append(category.getCategoryName());
            } else {
                builder.append(" -> " + category.getCategoryName());
            }
        }

        return builder.toString();
    }

    /****************************************************GET/SET*******************************************************/

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryIcon getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(CategoryIcon categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public AppCategory getParent() {
        return parent;
    }

    public void setParent(AppCategory parent) {
        this.parent = parent;
    }

    public List<AppCategory> getChildren() {
        return children;
    }

    public void setChildren(List<AppCategory> children) {
        this.children = children;
    }
}
