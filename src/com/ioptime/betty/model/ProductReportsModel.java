package com.ioptime.betty.model;

public class ProductReportsModel {
	int productID;
	String productName;
	String pModel;
	int viewed;
	String perecent;
	int Quantity;
	String OrderID[];

	public ProductReportsModel(int productID, String productName,
			String pModel, int viewed, String perecent, int quantity,
			String[] orderID) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.pModel = pModel;
		this.viewed = viewed;
		this.perecent = perecent;
		Quantity = quantity;
		OrderID = orderID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getpModel() {
		return pModel;
	}

	public void setpModel(String pModel) {
		this.pModel = pModel;
	}

	public int getViewed() {
		return viewed;
	}

	public void setViewed(int viewed) {
		this.viewed = viewed;
	}

	public String getPerecent() {
		return perecent;
	}

	public void setPerecent(String perecent) {
		this.perecent = perecent;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public String[] getOrderID() {
		return OrderID;
	}

	public void setOrderID(String[] orderID) {
		OrderID = orderID;
	}
}
