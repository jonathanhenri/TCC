package com.mycompany.domain;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name = "EVENTO")
public class Evento extends AbstractBean<Evento> {
	private static final long serialVersionUID = 1L;

	@Column(name = "DATA_INICIO", nullable = false)
	private Date dataInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Column(name = "DESCRICAO", nullable = false, length = 600)
	private String descricao;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_EVENTO",nullable = false)
	private TipoEvento tipoEvento;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORIGEM_EVENTO",nullable = false)
	private OrigemEvento origemEvento;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ALUNO")
	private Aluno alunoCriou;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public OrigemEvento getOrigemEvento() {
		return origemEvento;
	}

	public void setOrigemEvento(OrigemEvento origemEvento) {
		this.origemEvento = origemEvento;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	
	public Integer getPeriodo() {
		return periodo;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Curso getCurso() {
		return curso;
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
	public Class<Evento> getJavaType() {
		return Evento.class;
	}

}
