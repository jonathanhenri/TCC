package com.mycompany.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "ARQUIVO")
@Entity
public class Arquivo extends AbstractBean<Arquivo>{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_ARQUIVO")
	private Long id;
	
	@Column(name = "NOME_ARQUIVO", nullable = true, length = 100)
	private String nomeArquivo;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	
	@Lob
	@Column(name = "BYTES",nullable = false, length = 100000)
	@Basic(fetch = FetchType.LAZY)
	private byte[] bytes = {};
	
	public Arquivo(){
	}
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
	
	public Arquivo(byte[] bytes){
		this.bytes = bytes;
	}
	
	public Serializable getIdentifier() {
		return this.id;
	}

	public Class<Arquivo> getJavaType() {
		return Arquivo.class;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	@Override
	public String getNomeClass() {
		return "Arquivo";
	}
	
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
}