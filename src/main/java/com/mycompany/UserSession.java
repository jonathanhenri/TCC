package com.mycompany;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class UserSession extends AuthenticatedWebSession {
	
	private static final long serialVersionUID = 1L;
	
	
	@SpringBean(name = "authenticationManager")
	private AuthenticationManager authenticationManager;
	
	private static final Logger logger = Logger.getLogger(UserSession.class);
	
	
	
	public UserSession(Request request) {
		super(request);
		injectDependencies(); 
        ensureDependenciesNotNull(); 
	}

	private void ensureDependenciesNotNull() { 
        if (authenticationManager == null) { 
            throw new IllegalStateException("AdminSession requires an authenticationManager."); 
        } 
    }
	
	private void injectDependencies() {
        Injector.get().inject(this);
    }
	
	@Override 
    public boolean authenticate(String login, String senha) { 
        boolean authenticated = false; 
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha)); 
            SecurityContextHolder.getContext().setAuthentication(authentication); 
            authenticated = authentication.isAuthenticated(); 
        } catch (AuthenticationException e) { 
            logger.warn("Usuário " + login + " falhou ao logar. Motivo: " + e.getMessage()); 
            authenticated = false;
            throw e;
        } 
        return authenticated; 
    }
	
	@Override 
    public Roles getRoles() { 
        Roles roles = new Roles(); 
        getRolesIfSignedIn(roles); 
        return roles; 
    } 
	
	private synchronized void getRolesIfSignedIn(Roles roles) { 
        if (isSignedIn()) { 
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 
            addRolesFromAuthentication(roles, authentication); 
        } 
    } 
 
    private void addRolesFromAuthentication(Roles roles, Authentication authentication) {
    	if (authentication == null || authentication.getAuthorities()==null) {
    		signOut();
    		return;
    	}
    	for (GrantedAuthority authority : authentication.getAuthorities()) {
    		roles.add(authority.getAuthority());
    	}
    }
    
    @Override
    public Locale getLocale() {
    	return new Locale("pt", "BR");
    }
}
