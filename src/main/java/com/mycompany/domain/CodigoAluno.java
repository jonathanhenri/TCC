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

@Entity
@Table(name = "CODIGO_ALUNO")
public class CodigoAluno extends AbstractBean<CodigoAluno> {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CODIGO", nullable = false, length = 100)
	private String codigo;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO")
	private Curso curso;
	
	@Column(name = "ATIVO", nullable = false)
	private Boolean ativo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
	}
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<CodigoAluno> getJavaType() {
		return CodigoAluno.class;
	}

}
