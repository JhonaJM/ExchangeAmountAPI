package com.test.exchange.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class BearerToken extends AbstractAuthenticationToken {

	final private String token;
	
	public BearerToken(String token) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.token = token;
	}
	
	public String getCredentials() {

		return this.token;
	}

	@Override
	public String getPrincipal() {
		// TODO Auto-generated method stub
		return this.token;
	}

}
