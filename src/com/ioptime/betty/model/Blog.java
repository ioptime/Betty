package com.ioptime.betty.model;

public class Blog {
	int blog_id;
	int status;
	int sort_order;

	public Blog(int blog_id, int status, int sort_order, String create_time,
			String update_time, String date, int language_id, String title,
			String intro_text, String text, String meta_description,
			String meta_keyword) {
		super();
		this.blog_id = blog_id;
		this.status = status;
		this.sort_order = sort_order;
		this.create_time = create_time;
		this.update_time = update_time;
		this.date = date;
		this.language_id = language_id;
		this.title = title;
		this.intro_text = intro_text;
		this.text = text;
		this.meta_description = meta_description;
		this.meta_keyword = meta_keyword;
	}

	String create_time;
	String update_time;
	String date;
	int language_id;
	String title;
	String intro_text;
	String text;
	String meta_description;
	String meta_keyword;

	public int getBlog_id() {
		return blog_id;
	}

	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getLanguage_id() {
		return language_id;
	}

	public void setLanguage_id(int language_id) {
		this.language_id = language_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro_text() {
		return intro_text;
	}

	public void setIntro_text(String intro_text) {
		this.intro_text = intro_text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMeta_description() {
		return meta_description;
	}

	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}

	public String getMeta_keyword() {
		return meta_keyword;
	}

	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
	}
}
