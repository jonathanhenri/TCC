package com.mycompany.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class PerfilAcesso extends AbstractBean<PerfilAcesso> {
	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@Column(name = "ADMINISTRADOR", nullable = false, length = 300)
	private Boolean administrador;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	@Override
	public Serializable getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getJavaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
