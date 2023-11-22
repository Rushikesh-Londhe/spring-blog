package com.springblog.payloads;

public class JwtAuthResponse {

	private String token;

	
	
	public JwtAuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtAuthResponse(String token2) {
		super();
		this.token = token2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
