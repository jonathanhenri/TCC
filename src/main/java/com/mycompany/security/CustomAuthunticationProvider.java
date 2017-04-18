package com.mycompany.security;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aluno;
import com.mycompany.persistence.interfaces.IAlunoDAO;

@SuppressWarnings("all")
public class CustomAuthunticationProvider implements AuthenticationProvider{

	IAlunoDAO alunoDAO;
	
//	RoleHierarchy roleHierarchy;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		if (((String) authentication.getPrincipal()).trim().equals("") || ((String) authentication.getCredentials()).trim().equals("")) {
			throw new BadCredentialsException("login.invalido");
		}

		Aluno aluno = null;

		try {
			Aluno user = new Aluno();
			user.setCpf((String) authentication.getPrincipal());
			user.setSenha((String) authentication.getCredentials());
			
			
			aluno = alunoDAO.alunoTrue(user);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new AuthenticationServiceException("Currently we are unable to process your request. Kindly try again later.");
		}

		if (aluno == null) {
			throw new BadCredentialsException("login.invalido");
		}
		
		return new UsernamePasswordAuthenticationToken(aluno, authentication.getCredentials(), aluno.getAuthorities());
	}

	public boolean supports(Class arg0) {
		return true;
	}

	public void setAlunoDAO(IAlunoDAO alunoDAO) {
		this.alunoDAO = alunoDAO;
	}
	
//	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
//		this.roleHierarchy = roleHierarchy;
//	}
}