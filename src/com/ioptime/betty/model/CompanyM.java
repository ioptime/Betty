package com.ioptime.betty.model;

/**
 * For messages company name and customer are using the same structure , stores
 * have an extra id which will be 0 for customers
 * */
public class CompanyM {

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public CompanyM(int store_id, String name, int user_id) {
		super();
		this.store_id = store_id;
		this.name = name;
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	int store_id;
	String name;
	int user_id;

}
