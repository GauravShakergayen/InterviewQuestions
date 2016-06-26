package com.sapient.model;

public class Customer {

	private int cutomerId;
	
	private String customerName;
	
	public Customer(int cutomerId, String customerName) {
		super();
		this.cutomerId = cutomerId;
		this.customerName = customerName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCutomerId() {
		return cutomerId;
	}

	public void setCutomerId(int cutomerId) {
		this.cutomerId = cutomerId;
	}

}
