package com.sample.model;

import java.sql.Timestamp;

public class Board {
	private int id;
	private int categoryId;
	private String title;
	private String content;
	private int readCount;
	private boolean delFg;
	private int insId;
	private Timestamp insDt;
	private int updId;
	private Timestamp updDt;
	
	private String previewImg;
	private String username;
	private String profile;
	
	public Board() {}

	public Board(int id, int categoryId, String title, String content
			, int readCount, boolean delFg, int insId, Timestamp insDt, int updId
			, Timestamp updDt, String previewImg, String username, String profile) {
		this.id = id;
		this.categoryId = categoryId;
		this.title = title;
		this.content = content;
		this.readCount = readCount;
		this.delFg = delFg;
		this.insId = insId;
		this.insDt = insDt;
		this.updId = updId;
		this.updDt = updDt;
		this.previewImg = previewImg;
		this.username = username;
		this.profile = profile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public boolean isDelFg() {
		return delFg;
	}

	public void setDelFg(boolean delFg) {
		this.delFg = delFg;
	}

	public int getInsId() {
		return insId;
	}

	public void setInsId(int insId) {
		this.insId = insId;
	}

	public Timestamp getInsDt() {
		return insDt;
	}

	public void setInsDt(Timestamp insDt) {
		this.insDt = insDt;
	}

	public int getUpdId() {
		return updId;
	}

	public void setUpdId(int updId) {
		this.updId = updId;
	}

	public Timestamp getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Timestamp updDt) {
		this.updDt = updDt;
	}

	public String getPreviewImg() {
		return previewImg;
	}

	public void setPreviewImg(String previewImg) {
		this.previewImg = previewImg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
}
