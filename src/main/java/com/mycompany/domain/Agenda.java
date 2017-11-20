package com.mycompany.domain;

import java.io.Serializable;
import java.util.HashSet;
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

@Entity
@Table(name = "AGENDA")
public class Agenda extends AbstractBean<Agenda> {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(optional = true,fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "agenda")
	@Column(name = "ID_AGENDA")
	private Set<Evento> eventos;
	
	@Column(name = "DESCRICAO", nullable = true, length = 300)
	private String descricao;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "agenda",cascade = CascadeType.ALL)
	@Column(name = "ID_AGENDA")
	private Set<RelacaoPeriodo> listaPeriodosPertecentes;
	
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
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}

	public Set<Evento> getEventos() {
		return eventos;
	}
	
	public void addEvento(Evento evento){
		if(eventos == null){
			eventos = new HashSet<Evento>();
		}
		eventos.add(evento);
	}

	public void setEventos(Set<Evento> eventos) {
		this.eventos = eventos;
	}


	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setListaPeriodosPertecentes(
			Set<RelacaoPeriodo> listaPeriodosPertecentes) {
		this.listaPeriodosPertecentes = listaPeriodosPertecentes;
	}
	
	public Set<RelacaoPeriodo> getListaPeriodosPertecentes() {
		return listaPeriodosPertecentes;
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
	public String getNomeClass() {
		return "Agenda";
	}
	
}
