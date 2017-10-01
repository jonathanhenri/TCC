package com.mycompany.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "ORIGEM_EVENTO")
public class OrigemEvento extends AbstractBean<OrigemEvento> {
	private static final long serialVersionUID = 1L;

	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	
	@Id
	@Column(name = "ID_ORIGEM_EVENTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<OrigemEvento> getJavaType() {
		return OrigemEvento.class;
	}
	
	@Override
	public String getNomeClass() {
		return "Origem Evento";
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
