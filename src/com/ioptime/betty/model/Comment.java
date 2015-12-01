package com.ioptime.betty.model;

public class Comment {
	int review_id;
	int product_id;
	int customer_id;
	String text;
	int rating;
	String company;
	int company_id;

	public Comment(int review_id, int product_id, int customer_id, String text,
			int rating, String company, int company_id, String author) {
		super();
		this.review_id = review_id;
		this.product_id = product_id;
		this.customer_id = customer_id;
		this.text = text;
		this.rating = rating;
		this.company = company;
		this.company_id = company_id;
		this.author = author;
	}

	String author;

	public int getReview_id() {
		return review_id;
	}

	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
