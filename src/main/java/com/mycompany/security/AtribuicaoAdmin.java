package com.mycompany.security;

import java.io.Serializable;

import org.springframework.security.GrantedAuthority;

public class AtribuicaoAdmin implements GrantedAuthority{

	private static final long serialVersionUID = 1L;

	public AtribuicaoAdmin() {
    	super();
    }

	public String getAuthority() {
		return "ADMIN";
	}

	public int compareTo(Object o) {
		return 0;
	}

	public Serializable getIdentifier() {
		return 1;
	}

}