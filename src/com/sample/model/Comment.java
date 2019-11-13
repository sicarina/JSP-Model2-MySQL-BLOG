package com.sample.model;

import java.sql.Timestamp;

public class Comment {
	private int id;
	private int boardId;
	private String content;
	private int commentId;
	private boolean delFg;
	private int insId;
	private Timestamp insDt;
	private int updId;
	private Timestamp updDt;
	
	private int replyCnt;
	
	private String username;
	private String profile;
	
	public Comment() {}

	
	
	public Comment(int id, int boardId, String content, int commentId
			, boolean delFg, int insId, Timestamp insDt, int updId, Timestamp updDt
			, int replyCnt, String username, String profile) {
		this.id = id;
		this.boardId = boardId;
		this.content = content;
		this.commentId = commentId;
		this.delFg = delFg;
		this.insId = insId;
		this.insDt = insDt;
		this.updId = updId;
		this.updDt = updDt;
		this.replyCnt = replyCnt;
		this.username = username;
		this.profile = profile;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
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

	public int getReplyCnt() {
		return replyCnt;
	}



	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
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
