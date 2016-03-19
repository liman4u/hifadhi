package com.example.locateit.models;

public class ItemMenuList {
	private String icon = "";
	private String id = "";
	private String name = "";
	private String description = "";
	private String reference = "";
	private String price = "";
	
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		if(!price.equals("0")){
		this.price = price;
		}
	}
	@Override
	public String toString() {
		return "ItemMenuList [icon=" + icon + ", id=" + id + ", name=" + name
				+ ", reference=" + reference + ", types=" + price
				+ ", description=" + description + "]";
	}

	
}