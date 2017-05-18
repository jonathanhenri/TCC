package com.mycompany.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


// VERIFICAR SE VAI SER CRIADO UMA GRADE DE HORARIOS SEPARADA
//@Entity
//@Table(name = "GRADE_HORARIO")
public class GradeHorario extends AbstractBean<GradeHorario>{
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@Column(name = "DESCRICAO", nullable = false, length = 200)
	private String descricao;
	
	@Column(name = "OBSERVACAO", nullable = true, length = 2000)
	private String observacao;
	
	@Column(name = "PADRAO", nullable = true)
	private Boolean padrao;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ALUNO")
	private Aluno alunoCriou;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;	
	
	//TODO PEGAR RELACIONAMENTO
	private Set<Aula> listaHoraMaterias;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public void setPadrao(Boolean padrao) {
		this.padrao = padrao;
	}
	
	public Boolean getPadrao() {
		return padrao;
	}
	public Curso getCurso() {
		return curso;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public String getObservacao() {
		return observacao;
	}
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Aluno getAlunoCriou() {
		return alunoCriou;
	}

	public void setAlunoCriou(Aluno alunoCriou) {
		this.alunoCriou = alunoCriou;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<GradeHorario> getJavaType() {
		return GradeHorario.class;
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
