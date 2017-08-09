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
@Table(name = "ADMINISTRACAO")
public class Administracao implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_ADMINISTRACAO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ALUNO")
	private Aluno aluno;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO")
	private Curso curso;

	@Column(name = "ADMINISTRADOR_CAMPUS", nullable = true)
	private Boolean administradorCampus = false;
	
	public Aluno getAluno() {
		return aluno;
	}
	
	public void setAdministradorCampus(Boolean administradorCampus) {
		this.administradorCampus = administradorCampus;
	}

	public Boolean getAdministradorCampus() {
		return administradorCampus;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
