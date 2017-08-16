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

@Entity
@Table(name = "CONFIGURACAO")
public class Configuracao extends AbstractBean<Configuracao>{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_CONFIGURACAO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO")
	private Administracao administracao;
	
	@Column(name = "COMPARTILHAR_EVENTO", nullable = true)
	private Boolean compartilharEvento;
	
	@Column(name = "COMPARTILHAR_TIPO_EVENTO", nullable = true)
	private Boolean compartilharTipoEvento;
	
	@Column(name = "COMPARTILHAR_ORIGEM_EVENTO", nullable = true)
	private Boolean compartilharOrigemEvento;
	
	@Column(name = "COMPARTILHAR_MATERIA", nullable = true)
	private Boolean compartilharMateria;
	
	@Column(name = "COMPARTILHAR_AULA", nullable = true)
	private Boolean compartilharAula;
	
	@Column(name = "COMPARTILHAR_AGENDA", nullable = true)
	private Boolean compartilharAgenda;
	
	@Column(name = "COMPARTILHAR_PERFIL_ACESSO", nullable = true)
	private Boolean compartilharPerfilAcesso;
	
	
	
	
	@Column(name = "SINCRONIZAR_EVENTO", nullable = true)
	private Boolean sincronizarEvento;
	
	@Column(name = "SINCRONIZAR_TIPO_EVENTO", nullable = true)
	private Boolean sincronizarTipoEvento;
	
	@Column(name = "SINCRONIZAR_ORIGEM_EVENTO", nullable = true)
	private Boolean sincronizarOrigemEvento;
	
	@Column(name = "SINCRONIZAR_MATERIA", nullable = true)
	private Boolean sincronizarMateria;
	
	@Column(name = "SINCRONIZAR_AULA", nullable = true)
	private Boolean sincronizarAula;
	
	@Column(name = "SINCRONIZAR_AGENDA", nullable = true)
	private Boolean sincronizarAgenda;

	@Column(name = "SINCRONIZAR_PERFIL_ACESSO", nullable = true)
	private Boolean sincronizarPerfilAcesso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Administracao getAdministracao() {
		return administracao;
	}

	public void setAdministracao(Administracao administracao) {
		this.administracao = administracao;
	}

	public Boolean getCompartilharEvento() {
		return compartilharEvento;
	}

	public void setCompartilharEvento(Boolean compartilharEvento) {
		this.compartilharEvento = compartilharEvento;
	}

	public Boolean getCompartilharTipoEvento() {
		return compartilharTipoEvento;
	}

	public void setCompartilharTipoEvento(Boolean compartilharTipoEvento) {
		this.compartilharTipoEvento = compartilharTipoEvento;
	}

	public Boolean getCompartilharOrigemEvento() {
		return compartilharOrigemEvento;
	}

	public void setCompartilharOrigemEvento(Boolean compartilharOrigemEvento) {
		this.compartilharOrigemEvento = compartilharOrigemEvento;
	}

	public Boolean getCompartilharMateria() {
		return compartilharMateria;
	}

	public void setCompartilharMateria(Boolean compartilharMateria) {
		this.compartilharMateria = compartilharMateria;
	}

	public Boolean getCompartilharAula() {
		return compartilharAula;
	}

	public void setCompartilharAula(Boolean compartilharAula) {
		this.compartilharAula = compartilharAula;
	}

	public Boolean getCompartilharAgenda() {
		return compartilharAgenda;
	}

	public void setCompartilharAgenda(Boolean compartilharAgenda) {
		this.compartilharAgenda = compartilharAgenda;
	}

	public Boolean getCompartilharPerfilAcesso() {
		return compartilharPerfilAcesso;
	}

	public void setCompartilharPerfilAcesso(Boolean compartilharPerfilAcesso) {
		this.compartilharPerfilAcesso = compartilharPerfilAcesso;
	}

	public Boolean getSincronizarEvento() {
		return sincronizarEvento;
	}

	public void setSincronizarEvento(Boolean sincronizarEvento) {
		this.sincronizarEvento = sincronizarEvento;
	}

	public Boolean getSincronizarTipoEvento() {
		return sincronizarTipoEvento;
	}

	public void setSincronizarTipoEvento(Boolean sincronizarTipoEvento) {
		this.sincronizarTipoEvento = sincronizarTipoEvento;
	}

	public Boolean getSincronizarOrigemEvento() {
		return sincronizarOrigemEvento;
	}

	public void setSincronizarOrigemEvento(Boolean sincronizarOrigemEvento) {
		this.sincronizarOrigemEvento = sincronizarOrigemEvento;
	}

	public Boolean getSincronizarMateria() {
		return sincronizarMateria;
	}

	public void setSincronizarMateria(Boolean sincronizarMateria) {
		this.sincronizarMateria = sincronizarMateria;
	}

	public Boolean getSincronizarAula() {
		return sincronizarAula;
	}

	public void setSincronizarAula(Boolean sincronizarAula) {
		this.sincronizarAula = sincronizarAula;
	}

	public Boolean getSincronizarAgenda() {
		return sincronizarAgenda;
	}

	public void setSincronizarAgenda(Boolean sincronizarAgenda) {
		this.sincronizarAgenda = sincronizarAgenda;
	}

	public Boolean getSincronizarPerfilAcesso() {
		return sincronizarPerfilAcesso;
	}

	public void setSincronizarPerfilAcesso(Boolean sincronizarPerfilAcesso) {
		this.sincronizarPerfilAcesso = sincronizarPerfilAcesso;
	}

	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<Configuracao> getJavaType() {
		return Configuracao.class;
	}	
	
}
