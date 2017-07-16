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

/*
 * No futuro ter um crud de hora de materia para facilitar o cadastro do mesmo horario de aula em varias grades
 * TER UM CRUD DE CADASTRO DE PROFESSORES
 * 
 */
@Entity
@Table(name = "AULA")
public class Aula extends AbstractBean<Aula> {
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATERIA",nullable = false)
	private Materia materia;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AGENDA",nullable = false)
	private Agenda agenda;
	
	@Column(name = "PROFESSOR", nullable = true, length = 200)
	private String professor;
	
	@Column(name = "DATA_INICIO", nullable = true)
	private Date dataHoraInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataHoraFim;
	
	@Column(name = "LOCAL", nullable = true, length = 200)
	private String local;
	
	@Column(name = "OBSERVACAO", nullable = true, length = 200)
	private String observacao;
	

	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	@Id
	@Column(name = "ID_AULA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
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

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
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
