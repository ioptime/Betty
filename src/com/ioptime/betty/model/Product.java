package com.ioptime.betty.model;

import java.util.Comparator;

public class Product {
	int productId;
	String model;
	String image;
	String dateAvailable;
	int status;
	float price;
	String dateAdded;
	String productName;
	String description;
	int storeId;
	String location;
	String storeName;
	String url;
	boolean inWish;

	public Product(int productId, boolean inWish) {
		super();
		this.productId = productId;
		this.inWish = inWish;
	}

	public Product(int productId, String model, String location, String image,
			String dateAvailable, int status, String dateAdded,
			String productName, String description, int storeId,
			String storeName, String url, float price, boolean inWish) {
		super();
		this.productId = productId;
		this.model = model;
		this.image = image;
		this.location = location;
		this.dateAvailable = dateAvailable;
		this.status = status;
		this.dateAdded = dateAdded;
		this.productName = productName;
		this.description = description;
		this.storeId = storeId;
		this.storeName = storeName;
		this.url = url;
		this.price = price;
		this.inWish = inWish;
	}

	public boolean getInWish() {
		return inWish;
	}

	public void setInWIsh(boolean wish) {
		this.inWish = wish;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDateAvailable() {
		return dateAvailable;
	}

	public void setDateAvailable(String dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public static Comparator<Product> c = new Comparator<Product>() {
		public int compare(Product u1, Product u2) {
			return Integer.compare(u1.getProductId(), u2.getProductId());
		}
	};
}
