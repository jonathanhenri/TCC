package com.mycompany.domain;

import java.io.Serializable;

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
@Table(name = "PERMISSAO_ACESSO")
public class PermissaoAcesso extends AbstractBean<PermissaoAcesso> {
	private static final long serialVersionUID = 1L;

	//TODO TRABALHAR NESSAS PERMISSÕES
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@Column(name = "ACAO", nullable = false, length = 300)
	private Integer acao;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_PERFIL_ACESSO",nullable = true)
	private PerfilAcesso perfilAcesso;

	@Column(name = "CASO_DE_USO", nullable = false, length = 300)
	private Class<?> casoDeUso;
	
	@Id
	@Column(name = "ID_PERMISSAO_ACESSO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}
	
	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	
	public PerfilAcesso getPerfilAcesso() {
		return perfilAcesso;
	}

	@Override
	public Class<PermissaoAcesso> getJavaType() {
		return PermissaoAcesso.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAcao() {
		return acao;
	}

	public void setAcao(Integer acao) {
		this.acao = acao;
	}

	public Class<?> getCasoDeUso() {
		return casoDeUso;
	}

	public void setCasoDeUso(Class<?> casoDeUso) {
		this.casoDeUso = casoDeUso;
	}

	
}
