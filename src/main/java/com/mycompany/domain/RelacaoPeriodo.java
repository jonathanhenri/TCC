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

@Entity
@Table(name = "RELACAO_PERIODO")
public class RelacaoPeriodo extends AbstractBean<RelacaoPeriodo>{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Column(name = "PERIODO",nullable=false)
	private Integer periodo;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_ALUNO",nullable = true)
	private Aluno aluno;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_MATERIA",nullable = true)
	private Materia materia;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_EVENTO",nullable = true)
	private Evento evento;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_AGENDA",nullable = true)
	private Agenda agenda;
	
	
	@Id
	@Column(name = "ID_RELACAO_PERIODO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public RelacaoPeriodo(){
		
	}
	
	public RelacaoPeriodo(Integer periodo,Materia materia){
		setPeriodo(periodo);
		setMateria(materia);
	}
	
	public RelacaoPeriodo(Integer periodo,Evento evento){
		setPeriodo(periodo);
		setEvento(evento);
	}
	
	public RelacaoPeriodo(Integer periodo,Agenda agenda){
		setPeriodo(periodo);
		setAgenda(agenda);
	}
	
	public RelacaoPeriodo(Integer periodo,Aluno aluno){
		setPeriodo(periodo);
		setAluno(aluno);
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<RelacaoPeriodo> getJavaType() {
		return RelacaoPeriodo.class;
	}

	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
	}

	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
}
