package com.mycompany.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

/*Pensar no futuro para selecionar quais tipos de evento receber e de quem receber.
 * 
 * 
 */
@Entity
@Table(name = "AGENDA")
public class Agenda extends AbstractBean<Agenda> {
	private static final long serialVersionUID = 1L;

	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "agenda")
	@JoinColumn(name="ID_EVENTO",nullable = false)
	private Set<Evento> eventos;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "agenda")
	@JoinColumn(name="ID_AULA",nullable = false)
	private Set<Aula> aulas;
	
	@Column(name = "DESCRICAO", nullable = true, length = 300)
	private String descricao;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;	
	

	//receber eventos vindo de outros lugares
	@Column(name = "RECEBER_EVENTOS", nullable = false)
	private Boolean receberEventos;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ALUNO")
	private Aluno aluno;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@Id
	@Column(name = "ID_AGENDA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<Agenda> getJavaType() {
		return Agenda.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Set<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}

	public Set<Aula> getAulas() {
		return aulas;
	}

	public void setAulas(Set<Aula> aulas) {
		this.aulas = aulas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Boolean getReceberEventos() {
		return receberEventos;
	}

	public void setReceberEventos(Boolean receberEventos) {
		this.receberEventos = receberEventos;
	}

	public Aluno getAluno() {
		return aluno;
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
	
	
}
