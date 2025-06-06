package com.ecom.app;

public class Product {
	private int product_id;
	private String name;
	private double price;
	private int quantity;
	
	
	public Product(int product_id, String name, double price, int quantity) {
		
		this.product_id = product_id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "Product [product_id=" + product_id + ", name=" + name + ", price=" + price + ", quantity=" + quantity
				+ "]";
	}
	
	

}
