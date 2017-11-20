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

@Entity
@Table(name = "CONTADOR_ACESSO")
public class ContadorAcesso extends AbstractBean<ContadorAcesso> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONTADOR_ACESSO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DATA_ACESSO")
	private Date dataAcesso;
	
	@ManyToOne(optional = true,fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	@Override
	public Serializable getIdentifier() {
		return id;
	}
	
	public ContadorAcesso() {
	}
	
	public ContadorAcesso(Date dataAcesso) {
		setDataAcesso(dataAcesso);
	}
	
	

	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}

	public Date getDataAcesso() {
		return dataAcesso;
	}

	public void setDataAcesso(Date dataAcesso) {
		this.dataAcesso = dataAcesso;
	}

	@Override
	public Class<ContadorAcesso> getJavaType() {
		return ContadorAcesso.class;
	}

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public String getNomeClass() {
		return "Contador Acesso";
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
