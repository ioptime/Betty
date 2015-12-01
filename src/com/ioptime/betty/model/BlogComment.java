package com.ioptime.betty.model;

public class BlogComment {
	int comment_id;
	int blog_id;

	public BlogComment(int comment_id, int blog_id, String text, int status,
			String created, String user, String email) {
		super();
		this.comment_id = comment_id;
		this.blog_id = blog_id;
		this.text = text;
		this.status = status;
		this.created = created;
		this.user = user;
		this.email = email;
	}

	String text;
	int status;
	String created;
	String user;
	String email;

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
