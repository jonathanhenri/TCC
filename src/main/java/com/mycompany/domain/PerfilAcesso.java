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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "PERFIL_ACESSO")
public class PerfilAcesso extends AbstractBean<PerfilAcesso> {
	private static final long serialVersionUID = 1L;

	@ListarPageAnotacao
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="PERFIL_PERMISSOES", joinColumns=
  	 {@JoinColumn(name="ID_PERFIL_ACESSO")}, inverseJoinColumns=
  	  {@JoinColumn(name="ID_PERMISSAO_ACESSO")})
	private Set<PermissaoAcesso> permissoesAcesso;
	

	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	@Id
	@Column(name = "ID_PERFIL_ACESSO")
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
	public String getNomeClass() {
		return "Perfil Acesso";
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
