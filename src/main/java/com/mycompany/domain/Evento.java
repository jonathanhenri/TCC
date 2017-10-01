package com.mycompany.domain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import com.mycompany.anotacao.ListarPageAnotacao;

@Entity
@Table(name = "EVENTO")
public class Evento extends AbstractBean<Evento> {
	private static final long serialVersionUID = 1L;

	@ManyToOne(optional = true,fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="ID_ADMINISTRACAO",nullable=true)
	private Administracao administracao;
	
	@Column(name = "DATA_INICIO", nullable = true)
	private Date dataInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Transient
	private Date dataAuxiliar;
	
	@ListarPageAnotacao(filtro = true,nomeColuna = "Professor")
	@Column(name = "PROFESSOR", nullable = true, length = 200)
	private String professor;
	
	@ListarPageAnotacao(filtro = true,nomeColuna = "Local")
	@Column(name = "LOCAL", nullable = true, length = 100)
	private String local;
	
	@ListarPageAnotacao(filtro = true,nomeColuna="Observação")
	@Column(name = "OBSERVACAO", nullable = true, length = 600)
	private String observacao;
	
	@Column(name = "DESCRICAO", nullable = false, length = 600)
	private String descricao;
	
	@ListarPageAnotacao(nomeColuna="Tipo de evento",filtro = true)
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_TIPO_EVENTO",nullable = true)
	private TipoEvento tipoEvento;
	
	@ListarPageAnotacao(nomeColuna="Matéria",filtro = true)
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_MATERIA",nullable = true)
	private Materia materia;
	
	@ManyToOne(fetch=FetchType.LAZY,optional = true)
	@JoinColumn(name="ID_AGENDA",nullable = true)
	private Agenda agenda;
	
	@ListarPageAnotacao(nomeColuna="Origem do evento",filtro = true)
	@ManyToOne(optional = true,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_ORIGEM_EVENTO",nullable = true)
	private OrigemEvento origemEvento;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy = "evento",cascade = CascadeType.ALL)
	@Column(name = "ID_EVENTO")
	private Set<RelacaoPeriodo> listaPeriodosPertecentes;
	
	@Column(name = "REPETIR_EVENTO", nullable = true)
	private Boolean repetirEvento;
	
	@Column(name = "REPETIR_TODOS_DIAS", nullable = true)
	private Boolean repetirTodosDias;
	
	@Column(name = "REPETIR_TODA_SEGUNDA", nullable = true)
	private Boolean repetirTodaSegunda;
	
	@Column(name = "REPETIR_TODA_TERCA", nullable = true)
	private Boolean repetirTodaTerca;
	
	@Column(name = "REPETIR_TODA_QUARTA", nullable = true)
	private Boolean repetirTodaQuarta;
	
	@Column(name = "REPETIR_TODA_QUINTA", nullable = true)
	private Boolean repetirTodaQuinta;
	
	@Column(name = "REPETIR_TODA_SEXTA", nullable = true)
	private Boolean repetirTodaSexta;
	
	@Column(name = "REPETIR_TODA_SABADO", nullable = true)
	private Boolean repetirTodoSabado;
	
	@Column(name = "REPETIR_TODA_DOMINGO", nullable = true)
	private Boolean repetirTodoDomingo;
	
	
	@Id
	@Column(name = "ID_EVENTO")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	
	public void setDataAuxiliar(Date dataAuxiliar) {
		this.dataAuxiliar = dataAuxiliar;
	}
	
	public Date getDataAuxiliar() {
		return dataAuxiliar;
	}
	public Boolean getRepetirEvento() {
		return repetirEvento;
	}

	public void setRepetirEvento(Boolean repetirEvento) {
		this.repetirEvento = repetirEvento;
	}

	public Boolean getRepetirTodosDias() {
		return repetirTodosDias;
	}

	public void setRepetirTodosDias(Boolean repetirTodosDias) {
		this.repetirTodosDias = repetirTodosDias;
	}

	public Boolean getRepetirTodaSegunda() {
		return repetirTodaSegunda;
	}

	public void setRepetirTodaSegunda(Boolean repetirTodaSegunda) {
		this.repetirTodaSegunda = repetirTodaSegunda;
	}

	public Boolean getRepetirTodaTerca() {
		return repetirTodaTerca;
	}

	public void setRepetirTodaTerca(Boolean repetirTodaTerca) {
		this.repetirTodaTerca = repetirTodaTerca;
	}

	public Boolean getRepetirTodaQuarta() {
		return repetirTodaQuarta;
	}

	public void setRepetirTodaQuarta(Boolean repetirTodaQuarta) {
		this.repetirTodaQuarta = repetirTodaQuarta;
	}

	public Boolean getRepetirTodaQuinta() {
		return repetirTodaQuinta;
	}

	public void setRepetirTodaQuinta(Boolean repetirTodaQuinta) {
		this.repetirTodaQuinta = repetirTodaQuinta;
	}

	public Boolean getRepetirTodaSexta() {
		return repetirTodaSexta;
	}

	public void setRepetirTodaSexta(Boolean repetirTodaSexta) {
		this.repetirTodaSexta = repetirTodaSexta;
	}

	public Boolean getRepetirTodoSabado() {
		return repetirTodoSabado;
	}

	public void setRepetirTodoSabado(Boolean repetirTodoSabado) {
		this.repetirTodoSabado = repetirTodoSabado;
	}

	public Boolean getRepetirTodoDomingo() {
		return repetirTodoDomingo;
	}

	public void setRepetirTodoDomingo(Boolean repetirTodoDomingo) {
		this.repetirTodoDomingo = repetirTodoDomingo;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}
	
	public Materia getMateria() {
		return materia;
	}
	
	public OrigemEvento getOrigemEvento() {
		return origemEvento;
	}

	public void setOrigemEvento(OrigemEvento origemEvento) {
		this.origemEvento = origemEvento;
	}
	
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	public Agenda getAgenda() {
		return agenda;
	}
	
	public void setListaPeriodosPertecentes(
			Set<RelacaoPeriodo> listaPeriodosPertecentes) {
		this.listaPeriodosPertecentes = listaPeriodosPertecentes;
	}
	
	public Set<RelacaoPeriodo> getListaPeriodosPertecentes() {
		return listaPeriodosPertecentes;
	}
	
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getNomeClass() {
		return "Evento";
	}
	
	@Override
	public Serializable getIdentifier() {
		return id;
	}

	@Override
	public Class<Evento> getJavaType() {
		return Evento.class;
	}

}
