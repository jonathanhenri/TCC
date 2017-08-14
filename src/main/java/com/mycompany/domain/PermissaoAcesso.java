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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PERMISSAO_ACESSO")
public class PermissaoAcesso extends AbstractBean<PermissaoAcesso> {
	private static final long serialVersionUID = 1L;

	public static Integer OPERACAO_INCLUIR = 0;
	public static Integer OPERACAO_PESQUISAR = 1;
	public static Integer OPERACAO_ALTERAR = 2;
	public static Integer OPERACAO_EXCLUIR = 3;
	
	public static Integer PERMISSAO_ALUNO_INCLUIR = 0;
	public static Integer PERMISSAO_ALUNO_PESQUISAR = 1;
	public static Integer PERMISSAO_ALUNO_ALTERAR = 2;
	public static Integer PERMISSAO_ALUNO_EXCLUIR = 3;
	
	//TODO TRABALHAR NESSAS PERMISSÕES
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@Column(name = "OPERACAO", nullable = false)
	private Integer operacao;
	
	@Column(name = "PERMISSAO", nullable = false)
	private Integer permissao;
	
	@Column(name = "CODIGO", nullable = false)
	private Integer codigo;

	@Column(name = "CASO_DE_USO", nullable = false, length = 300)
	private Class<?> casoDeUso;
	
//	@ManyToMany(fetch=FetchType.LAZY)
//	@JoinColumn(name="ID_PERFIL_ACESSO",nullable = true)
//	private PerfilAcesso perfilAcesso;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;

	public PermissaoAcesso(){
	}
	
	public PermissaoAcesso(String nome,Integer permissao,Integer Codigo,Class<?> casoDeUso,Integer operacao) {
		setNome(nome);
		setPermissao(permissao);
		setCodigo(Codigo);
		setCasoDeUso(casoDeUso);
		setOperacao(operacao);
		
	}
	
	@Id
	@Column(name = "ID_PERMISSAO_ACESSO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public Integer getPermissao() {
		return permissao;
	}
	public void setPermissao(Integer permissao) {
		this.permissao = permissao;
	}
	public Integer getOperacao() {
		return operacao;
	}
	public void setOperacao(Integer operacao) {
		this.operacao = operacao;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
//	public PerfilAcesso getPerfilAcesso() {
//		return perfilAcesso;
//	}
//	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
//		this.perfilAcesso = perfilAcesso;
//	}
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
	
	
	@Override
	public Serializable getIdentifier() {
		return id;
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

	public Class<?> getCasoDeUso() {
		return casoDeUso;
	}

	public void setCasoDeUso(Class<?> casoDeUso) {
		this.casoDeUso = casoDeUso;
	}
}
