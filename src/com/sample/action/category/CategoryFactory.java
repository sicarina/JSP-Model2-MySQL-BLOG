package com.sample.action.category;

import com.sample.action.Action;

public class CategoryFactory {
	public static Action getAction(String cmd) {
		if(cmd.equals("list")) {
			return new CategoryListAction();
		} else {
			return null;
		}
	}
}
