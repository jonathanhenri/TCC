package com.mycompany.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;

public class CustomAuthenticationEntryPoint extends AuthenticationProcessingFilterEntryPoint {

	@Override
	public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		super.commence(request, response, authException);
	}

}