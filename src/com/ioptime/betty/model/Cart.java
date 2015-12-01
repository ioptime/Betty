package com.ioptime.betty.model;

public class Cart {
	int product_id;
	int user_id;
	int qty;

	public int getProductid() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Cart(int product_id, int user_id) {
		super();
		this.product_id = product_id;
		this.user_id = user_id;
		qty = 0;
	}

	public Cart(int product_id, int user_id, int qty) {
		super();
		this.product_id = product_id;
		this.user_id = user_id;
		this.qty = qty;
	}

	@Override
	public boolean equals(Object o) {
		return product_id == (((Cart) o).product_id);
	}

	@Override
	public int hashCode() {
		int hash = 13;
		hash = (31 * hash)
				+ (null == product_id + "_" + user_id ? 0 : (product_id + "_"
						+ user_id + "").hashCode());
		return hash;
	}
}
