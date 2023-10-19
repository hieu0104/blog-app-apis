package com.hieu.blog.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentDTO {

	public CommentDTO(int commentId, String content) {
		super();
		this.commentId = commentId;
		this.content = content;
	}

	public CommentDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private int commentId;

	private String content;
}
