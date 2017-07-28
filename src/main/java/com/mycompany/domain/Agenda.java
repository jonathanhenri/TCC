package com.mycompany.domain;

import java.io.Serializable;
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

/*Pensar no futuro para selecionar quais tipos de evento receber e de quem receber.
 * 
 * 
 */
@Entity
@Table(name = "AGENDA")
public class Agenda extends AbstractBean<Agenda> {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "agenda")
	@JoinColumn(name="ID_EVENTO", nullable = true)
	private Set<Evento> eventos;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "agenda")
	@JoinColumn(name="ID_AULA")
	private Set<Aula> aulas;
	
	@Column(name = "DESCRICAO", nullable = true, length = 300)
	private String descricao;
	
	@Column(name = "PERIODO", nullable = true)
	private Integer periodo;	
	
	
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
	
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
}
