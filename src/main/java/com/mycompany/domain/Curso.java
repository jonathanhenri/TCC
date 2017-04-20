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
	@ListarPageAnotacao(nomeColuna = "Numero")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ListarPageAnotacao(identificadorEstrangeiro = true)
	@Column(name = "NOME", nullable = false, length = 300)
	private String nome;
	
	@ListarPageAnotacao
	@Column(name = "DURACAO", nullable = false)
	private Integer duracao;
	
	@Column(name = "MODALIDADE", nullable = false)
	private Integer modalidade;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ARQUIVO")
	private Arquivo logo;
	
	
	
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	public void setLogo(Arquivo logo) {
		this.logo = logo;
	}
	
	public Arquivo getLogo() {
		return logo;
	}
	@Override
	public Class<Curso> getJavaType() {
		return Curso.class;
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
