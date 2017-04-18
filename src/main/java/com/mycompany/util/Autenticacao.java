package com.mycompany.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


public class Autenticacao extends Authenticator {
	
	private String usuario;
	private String senha;
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {   
        return new PasswordAuthentication(usuario, senha);   
    }   

}