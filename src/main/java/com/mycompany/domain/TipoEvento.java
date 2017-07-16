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

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "TIPO_EVENTO")
public class TipoEvento extends AbstractBean<TipoEvento> {
	private static final long serialVersionUID = 1L;

	@ListarPageAnotacao
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao
	@Column(name = "CODIGO_COR", nullable = true, length = 50)
	private String codigoCor;
	
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Id
	@Column(name = "ID_TIPO_EVENTO")
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
	
	
	
	
	
	
	public void setCodigoCor(String codigoCor) {
		this.codigoCor = codigoCor;
	}
	
	public String getCodigoCor() {
		return codigoCor;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	@Override
	public Serializable getIdentifier() {
		return id	;
	}

	@Override
	public Class<TipoEvento> getJavaType() {
		return TipoEvento.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
