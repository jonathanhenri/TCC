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
import javax.persistence.Transient;

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "CODIGO_ALUNO")
public class CodigoAluno extends AbstractBean<CodigoAluno> {
	private static final long serialVersionUID = 1L;
	
	@ListarPageAnotacao(filtro = true)
	@Column(name = "CODIGO", nullable = false, length = 100)
	private String codigo;
	
	@Column(name = "DATA_CRIACAO", nullable = false)
	private Date dataCriacao;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="PERFIL_ACESSO")
	private PerfilAcesso perfilAcesso;
	
	@ListarPageAnotacao(filtro = true)
	@Column(name = "ATIVO", nullable = false)
	private Boolean ativo;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Id
	@Column(name = "ID_CODIGO_ALUNO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	//Usados para auxiliar a geracao de alunos
	@Transient
	private Integer quantidadeAlunosAux;
	
	@Transient
	private Curso cursoAux;
	
	
	public void setPerfilAcesso(PerfilAcesso perfilAcesso) {
		this.perfilAcesso = perfilAcesso;
	}
	
	public PerfilAcesso getPerfilAcesso() {
		return perfilAcesso;
	}
	
	public Integer getQuantidadeAlunosAux() {
		return quantidadeAlunosAux;
	}

	public void setQuantidadeAlunosAux(Integer quantidadeAlunosAux) {
		this.quantidadeAlunosAux = quantidadeAlunosAux;
	}

	public Curso getCursoAux() {
		return cursoAux;
	}

	public void setCursoAux(Curso cursoAux) {
		this.cursoAux = cursoAux;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	@Override
	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
		
	}
	@Override
	public Administracao getAdministracao() {
		return administracao;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public Boolean getAtivo() {
		return ativo;
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
	public String getNomeClass() {
		return "Código Aluno";
	}
	@Override
	public Class<CodigoAluno> getJavaType() {
		return CodigoAluno.class;
	}

}
