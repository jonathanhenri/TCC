package com.mycompany.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

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
@Table(name = "CURSO")
public class Curso extends AbstractBean<Curso> {
	private static final long serialVersionUID = 1L;
	
	public static Integer MODALIDADE_ANUAL = 0;
	public static Integer MODALIDADE_SEMESTRAL = 1;
	
	/*
	 * A ordem dos atributos irar ser a ordem das colunas
	 */
	@ListarPageAnotacao(nomeColuna = "Numero",filtro = true)
	@Id
	@Column(name = "ID_CURSO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao
	@Column(name = "DURACAO", nullable = false)
	private Integer duracao;
	
	@Column(name = "MODALIDADE", nullable = true)
	private Integer modalidade;
	
	@Column(name = "QUANTIDADE_PERIODO", nullable = true)
	private Integer quantidadePeriodo;
	
//	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	@JoinColumn(name="ID_ARQUIVO")
//	private Arquivo logo;
	
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}
	

	public void setQuantidadePeriodo(Integer quantidadePeriodo) {
		this.quantidadePeriodo = quantidadePeriodo;
	}
	
	public Integer getQuantidadePeriodo() {
		return quantidadePeriodo;
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
	public Class<Curso> getJavaType() {
		return Curso.class;
	}

	@Override
	public Long getId() {
		return id;
	}

	public static List<Integer> getListaModalidades(){
		return Arrays.asList(MODALIDADE_ANUAL,MODALIDADE_SEMESTRAL);
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

	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public Integer getModalidade() {
		return modalidade;
	}

	public void setModalidade(Integer modalidade) {
		this.modalidade = modalidade;
	}
}
