package com.sample.model;

import java.sql.Timestamp;

public class User {
	private int id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private boolean emailChk;
	private String address;
	private String profile;
	private Timestamp insDt;
	
	public User() {}
	
	public User(int id, String username, String password, String salt, String email, boolean emailChk, String address,String profile, Timestamp insDt) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.emailChk = emailChk;
		this.address = address;
		this.profile = profile;
		this.insDt = insDt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEmailChk() {
		return emailChk;
	}

	public void setEmailChk(boolean emailChk) {
		this.emailChk = emailChk;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Timestamp getInsDt() {
		return insDt;
	}

	public void setInsDt(Timestamp insDt) {
		this.insDt = insDt;
	}
	
}
