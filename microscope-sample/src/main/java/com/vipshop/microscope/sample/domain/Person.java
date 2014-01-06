package com.vipshop.microscope.sample.domain;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = 5077948463225788371L;

	private int id;
	private String username;
	private String password;
	private String email;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
