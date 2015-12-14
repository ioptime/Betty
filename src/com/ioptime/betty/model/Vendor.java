package com.ioptime.betty.model;

public class Vendor {
	int user_id;
	String firstname;
	String lastname;
	String email;
	String telephone;
	String topic;
	String logoURl;
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogoURl() {
		return logoURl;
	}

	public void setLogoURl(String logoURl) {
		this.logoURl = logoURl;
	}

	public Vendor() {
	}

	public Vendor(int user_id, String firstname, String lastname, String email,
			String telephone, String topic, String logoURl, String userName) {
		super();
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.telephone = telephone;
		this.topic = topic;
		this.logoURl = logoURl;
		this.userName = userName;

	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String teleString) {
		this.telephone = teleString;
	}

}
