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
@Table(name = "TIPO_EVENTO")
public class TipoEvento extends AbstractBean<TipoEvento> {
	private static final long serialVersionUID = 1L;

	@ListarPageAnotacao
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao(nomeColuna = "Curso")
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@ListarPageAnotacao(nomeColuna = "Materia")
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATERIA",nullable = true)
	private Materia materia;
	
	@Column(name = "CODIGO_COR", nullable = true)
	private String codigoCor;
	
	@Id
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
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	@Override
	public Serializable getIdentifier() {
		return id	;
	}

	@Override
	public Class<TipoEvento> getJavaType() {
		return TipoEvento.class;
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
