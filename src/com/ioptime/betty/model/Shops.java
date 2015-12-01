package com.ioptime.betty.model;

public class Shops {
	public Shops(int id, String name, String url, String ssl, boolean follow) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.ssl = ssl;
		this.isFollowed = follow;
	}

	int id;
	String name;
	String url;
	String ssl;
	boolean isFollowed;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isFollowed() {
		return isFollowed;
	}

	public void setFollowed(Boolean follow) {
		this.isFollowed = follow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}
}
