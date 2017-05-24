package com.mycompany.domain;

import java.io.Serializable;

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

	@ListarPageAnotacao
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao(nomeColuna = "Curso")
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@ListarPageAnotacao(nomeColuna="Código cor")
	@Column(name = "CODIGO_COR", nullable = true,length = 50)
	private String codigoCor;
	
	@Id
	@Column(name = "ID_ORIGEM_EVENTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public void setCodigoCor(String codigoCor) {
		this.codigoCor = codigoCor;
	}
	public String getCodigoCor() {
		return codigoCor;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Curso getCurso() {
		return curso;
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
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
