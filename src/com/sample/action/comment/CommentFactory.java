package com.sample.action.comment;

import com.sample.action.Action;

public class CommentFactory {
	public static Action getAction(String cmd) {
		if(cmd.equals("write")) {
			return new CommentWriteAction();
		} else if(cmd.equals("delete")) {
			return new CommentDeleteAction();
		} else if(cmd.equals("view")) {
			return new CommentViewAction();
		} else if(cmd.equals("update")) {
			return new CommentUpdateAction();
		} else if(cmd.equals("viewAll")) {
			return new CommentViewAllAction();
		} else {
			return null;
		}
	}
}
