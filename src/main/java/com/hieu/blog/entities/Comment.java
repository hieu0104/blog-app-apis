package com.hieu.blog.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 
 * @author LeChiHieu
 * 
 *         the class is Comment , contains properties : id, content
 *
 */
@Entity
@Table(name = "comments")

public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;

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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	private String content;

	@ManyToOne
	private Post post;
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(int commentId, String content, Post post) {
		super();
		this.commentId = commentId;
		this.content = content;
		this.post = post;
	}
	
	
}
