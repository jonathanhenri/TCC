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
@Table(name = "MATERIA")
public class Materia extends AbstractBean<Materia> {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao
	@Column(name = "PERIODO", nullable = false)
	private Integer periodo;	
	
	@Id
	@Column(name = "ID_MATERIA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
		
	@Override
	public Class<Materia> getJavaType() {
		return Materia.class;
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
