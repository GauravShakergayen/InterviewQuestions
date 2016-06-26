package com.sapient.model;

public enum Station {

	A1, A2, A3, A4, A5, A6, A7, A8, A9, A10;

	public int distance(Station other) {
		System.out.println(other.ordinal()+"***********"+this.ordinal());
		return Math.abs(other.ordinal() - this.ordinal());
	}
}
