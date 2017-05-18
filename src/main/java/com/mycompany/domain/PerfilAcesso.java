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

@Entity
@Table(name = "PERFIL_ACESSO")
public class PerfilAcesso extends AbstractBean<PerfilAcesso> {
	private static final long serialVersionUID = 1L;

	//TODO TRABALHAR NESSAS PERMISSÕES
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@Column(name = "ADMINISTRADOR", nullable = false, length = 300)
	private Boolean administrador;
	
	@ManyToOne(optional = false,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CURSO",nullable = false)
	private Curso curso;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "perfilAcesso")
	@JoinColumn(name="ID_PERMISSAO_ACESSO",nullable = false)
	private Set<PermissaoAcesso> permissoesAcesso;
	
	@Id
	@Column(name = "ID_PERFIL_ACESSO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}

	public Set<PermissaoAcesso> getPermissoesAcesso() {
		return permissoesAcesso;
	}

	public void setPermissoesAcesso(Set<PermissaoAcesso> permissoesAcesso) {
		this.permissoesAcesso = permissoesAcesso;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<PerfilAcesso> getJavaType() {
		return PerfilAcesso.class;
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
