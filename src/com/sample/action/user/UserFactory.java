package com.sample.action.user;

import com.sample.action.Action;

public class UserFactory {
	public static Action getAction(String cmd) {
		if(cmd.equals("join")) {
			return new UserJoinAction();
		} else if(cmd.equals("duplicate")) {
			return new UserDuplicateAction();
		} else if(cmd.equals("mail")) {
			return new UserMailAction();
		} else if(cmd.equals("login")) {
			return new UserLoginAction();
		} else if(cmd.equals("logout")) {
			return new UserLogoutAction();
		} else if(cmd.equals("update")) {
			return new UserUpdateAction();
		} else if(cmd.equals("password")) {
			return new UserPasswordAction();
		} else if(cmd.equals("send")) {
			return new UserSendAction();
		} else if(cmd.equals("profile")) {
			return new UserProfileAction();
		} else if(cmd.equals("profileDelete")) {
			return new UserProfileDeleteAction();
		} else if(cmd.equals("info")) {
			return new UserInfoAction();
		} else {
			return null;
		}
	}
}
