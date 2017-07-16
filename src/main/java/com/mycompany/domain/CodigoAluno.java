package com.mycompany.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "CODIGO_ALUNO")
public class CodigoAluno extends AbstractBean<CodigoAluno> {
	private static final long serialVersionUID = 1L;
	
	@ListarPageAnotacao(filtro = true)
	@Column(name = "CODIGO", nullable = false, length = 100)
	private String codigo;
	
	
	@ListarPageAnotacao(filtro = true)
	@Column(name = "ATIVO", nullable = false)
	private Boolean ativo;
	
	@Transient
	private List<CodigoAluno> listaCodigosAlunosGerados;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Id
	@Column(name = "ID_CODIGO_ALUNO")
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
	
	
	public void setListaCodigosAlunosGerados(
			List<CodigoAluno> listaCodigosAlunosGerados) {
		this.listaCodigosAlunosGerados = listaCodigosAlunosGerados;
	}
	
	public List<CodigoAluno> getListaCodigosAlunosGerados() {
		return listaCodigosAlunosGerados;
	}
	
	public void addListaCodigoAluno(CodigoAluno codigoAluno){
		if(listaCodigosAlunosGerados == null){
			listaCodigosAlunosGerados = new ArrayList<CodigoAluno>();
		}
		
		listaCodigosAlunosGerados.add(codigoAluno);
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
	public Class<CodigoAluno> getJavaType() {
		return CodigoAluno.class;
	}

}
