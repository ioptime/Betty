package com.ioptime.betty.model;

public class Category {
	int categoryId;
	String image;
	int parentId;
	int top;
	int column;
	int sortOrder;
	int status;
	String dateAdded;
	String dateModified;
	int languageId;
	String name;
	String description;
	String metaDescription;
	String metaKeyword;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public int getLanguageId() {
		return languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public Category(int categoryId, String image, int parentId, int top,
			int column, int sortOrder, int status, String dateAdded,
			String dateModified, int languageId, String name,
			String description, String metaDescription, String metaKeyword) {
		super();
		this.categoryId = categoryId;
		this.image = image;
		this.parentId = parentId;
		this.top = top;
		this.column = column;
		this.sortOrder = sortOrder;
		this.status = status;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.languageId = languageId;
		this.name = name;
		this.description = description;
		this.metaDescription = metaDescription;
		this.metaKeyword = metaKeyword;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	public String getMetaKeyword() {
		return metaKeyword;
	}

	public void setMetaKeyword(String metaKeyword) {
		this.metaKeyword = metaKeyword;
	}
}
