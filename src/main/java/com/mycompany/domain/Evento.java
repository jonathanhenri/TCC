package com.mycompany.domain;

import java.io.Serializable;
import java.util.Date;

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

@Entity
@Table(name = "EVENTO")
public class Evento extends AbstractBean<Evento> {
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO",nullable=false)
	private Administracao administracao;
	
	@Column(name = "DATA_INICIO", nullable = false)
	private Date dataInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Column(name = "CODIGO_COR", nullable = true, length = 50)
	private String codigoCor;
	
	@ListarPageAnotacao
	@Column(name = "DESCRICAO", nullable = false, length = 600)
	private String descricao;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_EVENTO",nullable = true)
	private TipoEvento tipoEvento;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATERIA",nullable = true)
	private Materia materia;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_AGENDA",nullable = true)
	private Agenda agenda;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORIGEM_EVENTO",nullable = true)
	private OrigemEvento origemEvento;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;	
	
	@Id
	@Column(name = "ID_EVENTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
	
	public void setCodigoCor(String codigoCor) {
		this.codigoCor = codigoCor;
	}
	
	public String getCodigoCor() {
		return codigoCor;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public Materia getMateria() {
		return materia;
	}
	
	public OrigemEvento getOrigemEvento() {
		return origemEvento;
	}

	public void setOrigemEvento(OrigemEvento origemEvento) {
		this.origemEvento = origemEvento;
	}
	
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	public Agenda getAgenda() {
		return agenda;
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
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
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
