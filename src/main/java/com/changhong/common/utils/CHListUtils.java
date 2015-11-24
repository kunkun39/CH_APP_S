package com.changhong.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 15-10-25
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class CHListUtils {
    public static <T> List<T> getSameElement(List<T> list1,List<T> list2) {
        List<T> list = new ArrayList<T>();
        for(T element:list2) {
            if(list1.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    public static <T> boolean hasElement(List<T> list) {
        if(list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static <T> List<T> removeSameElement(List<T> list1,List<T> list2) {
        for(T element:list2) {
            if(list1.contains(element)) {
                list1.remove(element);
            }
        }
        return list1;
    }

    public static boolean listIsEmpty(List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}
}
