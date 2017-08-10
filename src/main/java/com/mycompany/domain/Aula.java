package com.mycompany.domain;

import java.io.Serializable;
import java.sql.Date;

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

/*
 * No futuro ter um crud de hora de materia para facilitar o cadastro do mesmo horario de aula em varias grades
 * TER UM CRUD DE CADASTRO DE PROFESSORES
 * 
 */
@Entity
@Table(name = "AULA")
public class Aula extends AbstractBean<Aula> {
	private static final long serialVersionUID = 1L;

	@ListarPageAnotacao(identificadorEstrangeiro = true,nomeColuna ="Matéria")
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATERIA",nullable = false)
	private Materia materia;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AGENDA",nullable = false)
	private Agenda agenda;
	
	@ListarPageAnotacao
	@Column(name = "PROFESSOR", nullable = true, length = 200)
	private String professor;
	
	@Column(name = "DATA_INICIO", nullable = true)
	private Date dataInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Column(name = "LOCAL", nullable = true, length = 100)
	private String local;
	
	@ListarPageAnotacao
	@Column(name = "NOME", nullable = true, length = 100)
	private String nome;
	
	@Column(name = "OBSERVACAO", nullable = true, length = 600)
	private String observacao;
	
	@Column(name = "PERIODO", nullable = false)
	private Integer periodo;

	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	@Id
	@Column(name = "ID_AULA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	public Agenda getAgenda() {
		return agenda;
	}
	@Override
	public Class<Aula> getJavaType() {
		return Aula.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	public Date getDataFim() {
		return dataFim;
	}
	
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
