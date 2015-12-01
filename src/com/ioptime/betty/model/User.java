package com.ioptime.betty.model;

import java.util.ArrayList;

public class User {

	public User() {
		super();
	}

	public User(int customer_id, int store_id, String firstname,
			String lastname, String email, String telephone, String fax,
			String cart, ArrayList<Product> wishlist,
			ArrayList<Shops> followList, String newsletter, int address_id,
			int customer_group_id, String ip, int status, int approved,
			String logo, String pay_status, String topic) {
		super();
		this.customer_id = customer_id;
		this.store_id = store_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telephone = telephone;
		this.fax = fax;
		this.cart = cart;
		this.wishlist = wishlist;
		this.followList = followList;
		this.newsletter = newsletter;
		this.address_id = address_id;
		this.customer_group_id = customer_group_id;
		this.ip = ip;
		this.status = status;
		this.approved = approved;
		this.logo = logo;
		this.pay_status = pay_status;
		this.topic = topic;

	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getStore_id() {
		return store_id;
	}

	public void setStore_id(int store_id) {
		this.store_id = store_id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCart() {
		return cart;
	}

	public void setCart(String cart) {
		this.cart = cart;
	}

	public ArrayList<Product> getWishlist() {
		if (wishlist != null)
			return wishlist;
		else
			return new ArrayList<Product>();
	}

	public void setWishlist(ArrayList<Product> wishlist) {
		this.wishlist = wishlist;
	}

	public ArrayList<Shops> getFollowList() {
		return followList;
	}

	public void setFollowList(ArrayList<Shops> followList) {
		this.followList = followList;
	}

	public String getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}

	public int getAddress_id() {
		return address_id;
	}

	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}

	public int getCustomer_group_id() {
		return customer_group_id;
	}

	public void setCustomer_group_id(int customer_group_id) {
		this.customer_group_id = customer_group_id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getApproved() {
		return approved;
	}

	public void setApproved(int approved) {
		this.approved = approved;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	int customer_id;
	int store_id;
	String firstname;
	String lastname;
	String email;
	String telephone;
	String fax;
	String cart;
	ArrayList<Product> wishlist = new ArrayList<Product>();
	ArrayList<Shops> followList = new ArrayList<Shops>();
	String newsletter;
	int address_id;
	int customer_group_id;
	String ip;
	int status;
	int approved;
	String logo;
	String pay_status;
	String topic;
}
