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

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "MATERIA")
public class Materia extends AbstractBean<Materia> {
	private static final long serialVersionUID = 1L;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "materia")
	@Column(name = "ID_MATERIA")
	private Set<RelacaoPeriodo> listaPeriodosPertecentes;
	
	@Id
	@Column(name = "ID_MATERIA")
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
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}
	
	public void setListaPeriodosPertecentes(
			Set<RelacaoPeriodo> listaPeriodosPertecentes) {
		this.listaPeriodosPertecentes = listaPeriodosPertecentes;
	}
	public Set<RelacaoPeriodo> getListaPeriodosPertecentes() {
		return listaPeriodosPertecentes;
	}
	@Override
	public Class<Materia> getJavaType() {
		return Materia.class;
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
	public String getNomeClass() {
		return "Matéria";
	}
}
