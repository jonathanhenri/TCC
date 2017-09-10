package com.mycompany.visao.agenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import wicket.contrib.input.events.EventType;
import wicket.contrib.input.events.InputBehavior;
import wicket.contrib.input.events.key.KeyType;

import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.domain.FiltroDinamicoAgrupador;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.Util;
import com.mycompany.visao.cadastro.evento.EventoEditForm;
import com.mycompany.visao.cadastro.evento.EventoPanel;
import com.mycompany.visao.comum.ListagemFiltrosDinamicosPanel;

public class CalendarioPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private Agenda agenda;
	private Evento evento;
	private WebMarkupContainer divListagem;
	private HashMap<Date, List<Evento>> hashMapEventoAgrupado;
	protected ModalWindow modalIncluirEditar;
	protected ModalWindow modalFiltros;
	
	private FiltroDinamicoAgrupador filtroDinamicoAgrupador;
	
	private Boolean focusGained;
	@SpringBean(name="agendaServico")
	private  IAgendaServico agendaServico;

	@SpringBean(name="eventoServico")
	private  IEventoServico eventoServico;

	@SpringBean(name="materiaServico")
	private  IMateriaServico materiaServico;
	
	@SpringBean(name="tipoEventoServico")
	private  ITipoEventoServico tipoEventoServico;
	
	@SpringBean(name="origemEventoServico")
	private  IOrigemEventoServico origemEventoServico;
	
	public CalendarioPanel(String id,Agenda agenda) {
		super(id);
		this.agenda = agenda;
		focusGained = true;
		filtroDinamicoAgrupador = new FiltroDinamicoAgrupador();
		adicionarCampos();
	}
	
	private void adicionarCampos(){
		evento = new Evento();
		popularHashMapEventoAgrupado();
		criarFormEventosCalendario();
		
	}
	
	private ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(750);
		modalIncluirEditar.setInitialWidth(900);
		modalIncluirEditar.setCloseButtonCallback(null);
		return modalIncluirEditar;
	}
	
	private void popularHashMapEventoAgrupado(){
		agenda = (Agenda) agendaServico.searchFechId(agenda);
		hashMapEventoAgrupado = new HashMap<Date, List<Evento>>();
		
		List<Evento> listaEventos = Util.toList(agenda.getEventos());
		
		if(listaEventos!=null && listaEventos.size()>0){
			for(Evento evento:listaEventos){
				List<Evento> listaAux;
				if(hashMapEventoAgrupado.containsKey(evento.getDataInicio())){
					listaAux = hashMapEventoAgrupado.get(evento.getDataInicio());
				}else{
					listaAux = new ArrayList<Evento>();
				}
				listaAux.add(evento);
				hashMapEventoAgrupado.put(evento.getDataInicio(),listaAux);
			}
			
		}
	}
	
	private DateTimeField criarCampoDataFim(){
		DateTimeField dataFim = new DateTimeField("dataFim",new PropertyModel<Date>(evento, "dataFim"));
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	private DateTimeField criarCampoDataInicio(){
		DateTimeField dataFim = new DateTimeField("dataInicio",new PropertyModel<Date>(evento, "dataInicio"));
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	
	private ModalWindow criarModalFiltros() {
		modalFiltros = new ModalWindow("modalFiltros");
		modalFiltros.setOutputMarkupId(true);
		modalFiltros.setInitialHeight(400);
		modalFiltros.setInitialWidth(500);
		
		modalFiltros.setCloseButtonCallback(null);
		
		return modalFiltros;
	}
	
	private AjaxButton criarButtonListagemFiltrosDinamicos(){
		AjaxButton ajaxButton =  new AjaxButton("buttonListagemFiltrosDinamicos") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				List<FiltroDinamicoAtributo> listaAtributos = Reflexao.nomesAtributosFiltros(evento);
				if(listaAtributos!=null && listaAtributos.size() > 0){
					ListagemFiltrosDinamicosPanel filtrosDinamicosPanel = new ListagemFiltrosDinamicosPanel(modalFiltros.getContentId(),modalFiltros,filtroDinamicoAgrupador,listaAtributos) {
						private static final long serialVersionUID = 1L;
	
						@Override
						protected void executarAoPesquisar(AjaxRequestTarget target) {
							modalFiltros.close(target);
							target.add(divListagem);
							
							if(Util.getAlunoLogado().getListaMensagensSistema()!=null && Util.getAlunoLogado().getListaMensagensSistema().size()>0){
								for(Mensagem mensagem:Util.getAlunoLogado().getListaMensagensSistema()){
									Util.notify(target, mensagem.toString(), mensagem.getTipo());
								}
								Util.getAlunoLogado().setListaMensagensSistema(new ArrayList<Mensagem>());
							}
							
						}
					};
					getForm().add(filtrosDinamicosPanel);
					
					filtrosDinamicosPanel.setOutputMarkupId(true);
					modalFiltros.setContent(filtrosDinamicosPanel);
					modalFiltros.show(target);
				}else{
					Util.notifyInfo(target, "Não existe filtros adicionais.");
				}
			}
		};
		
		return ajaxButton;
	}
	
	private void criarFormEventosCalendario(){
		divListagem = new WebMarkupContainer("divListagem");
		divListagem.setOutputMarkupId(true);
		
		Form<Agenda> formListagem = new Form<Agenda>("form");
		formListagem.setOutputMarkupId(true);
		
		formListagem.add(criarListViewEventosCalendario());
		formListagem.add(criarButtonIncluir(formListagem));
		formListagem.add(criarModalIncluirEditar());
		formListagem.add(criarBotaoVoltar());
		formListagem.add(criarModalFiltros());
		formListagem.add(criarButtonListagemFiltrosDinamicos());
		
		formListagem.add(criarCampoDescricao());
		formListagem.add(criarCampoDataFim());
		formListagem.add(criarCampoDataInicio());
		divListagem.add(formListagem);
		add(divListagem);
	}
	
	private TextField<String> criarCampoDescricao(){
		TextField<String> textFieldNome = new TextField<String>("descricao", new PropertyModel<String>(evento, "descricao"));
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 600));
		return textFieldNome;
	}
	
	private WebMarkupContainer criarDivEsquerda(Evento evento,final Boolean visible){
		WebMarkupContainer containerEsquerda = new WebMarkupContainer("divEsquerda"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return visible;
			}
		};
		containerEsquerda.setOutputMarkupId(true);
		containerEsquerda.add(new Label("descricao",evento.getDescricao()));
		
		containerEsquerda.add(new Label("dataInicio", Util.formataDataComHoraSemLocale(evento.getDataInicio())));
		containerEsquerda.add(new Label("dataFim", Util.formataDataComHoraSemLocale(evento.getDataFim())));
		containerEsquerda.add(new Label("local",evento.getLocal()));
		containerEsquerda.add(criarLinkExcluirEvento(evento));
		containerEsquerda.add(criarLinkEditarEvento(evento));
		
		return containerEsquerda;
	}
	
	private WebMarkupContainer criarDivDireita(Evento evento,final Boolean visible){
		WebMarkupContainer containerEsquerda = new WebMarkupContainer("divDireita"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return visible;
			}
		};
		containerEsquerda.setOutputMarkupId(true);
		containerEsquerda.add(new Label("descricao",evento.getDescricao()));
		
		containerEsquerda.add(new Label("dataInicio", Util.getDateFormat(evento.getDataInicio())));
		containerEsquerda.add(new Label("dataFim", Util.getDateFormat(evento.getDataFim())));
		containerEsquerda.add(new Label("local",evento.getLocal()));
		containerEsquerda.add(criarLinkExcluirEvento(evento));
		containerEsquerda.add(criarLinkEditarEvento(evento));
		
		return containerEsquerda;
	}

	private ListView<Evento> criarListViewEventosDoDiaCalendario(final Date diaCalendario){
		LoadableDetachableModel<List<Evento>> loadEventosCalendario = new LoadableDetachableModel<List<Evento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Evento> load() {
				return Util.toList(hashMapEventoAgrupado.get(diaCalendario));
			}
		};
		
		ListView<Evento> listViewPermissaoAcesso = new ListView<Evento>("eventosDoDia",loadEventosCalendario) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Evento> item) {
				
				Evento evento = (Evento) item.getModelObject();
				item.add(criarDivEsquerda(evento,item.getIndex() % 2 == 0)); // par vai pra esquerda
				item.add(criarDivDireita(evento,!(item.getIndex() % 2 == 0))); // impar vai pra direita
			}
		};
		listViewPermissaoAcesso.setOutputMarkupId(true);
		return listViewPermissaoAcesso;
	}
	
	
	
	private AjaxLink<Evento> criarLinkEditarEvento(final Evento evento){
		AjaxLink<Evento> linkExcluirEvento = new AjaxLink<Evento>("linkEditarEvento") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				EventoPanel editPanel = new EventoPanel(modalIncluirEditar.getContentId());
				editPanel.setOutputMarkupId(true);
				
				EventoEditForm cadastroAlunoEditForm = new EventoEditForm((Evento)eventoServico.searchFechId(evento),editPanel,null,divListagem,modalIncluirEditar);
				cadastroAlunoEditForm.setOutputMarkupId(true);
				editPanel.add(cadastroAlunoEditForm);
				
				modalIncluirEditar.setContent(editPanel);
				modalIncluirEditar.show(target);
				
				target.add(divListagem);
			}
		};
		
		return linkExcluirEvento;
	}
	
	private AjaxLink<String> criarBotaoVoltar(){
		AjaxLink<String> botaoVoltar = new  AjaxLink<String>("voltar"){
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(focusGained){
					modalIncluirEditar.close(target);
				}
			}
			 
		 };
		 botaoVoltar.add(new InputBehavior(new KeyType[] { KeyType.Escape }, EventType.click));
		 return botaoVoltar;
	}
	
	
	private AjaxLink<Evento> criarLinkExcluirEvento(final Evento evento){
		AjaxLink<Evento> linkExcluirEvento = new AjaxLink<Evento>("linkExcluirEvento") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				Retorno retorno = eventoServico.remove(evento);
				if(retorno.getSucesso()){
					agenda.getEventos().remove(evento);
					retorno = agendaServico.save(agenda);;
				}
				
				 for(Mensagem mensagem:retorno.getListaMensagem()){
					 Util.notify(target, mensagem.toString(), mensagem.getTipo());
		        }
				 
				target.add(divListagem);
			}
		};
		
		return linkExcluirEvento;
	}
	private ListView<Date> criarListViewEventosCalendario(){
		LoadableDetachableModel<List<Date>> loadEventosCalendario = new LoadableDetachableModel<List<Date>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Date> load() {
				popularHashMapEventoAgrupado();
				return Util.toList(hashMapEventoAgrupado.keySet());
			}
		};
			
		ListView<Date> listViewPermissaoAcesso = new ListView<Date>("diaEventosCalendario",loadEventosCalendario) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Date> item) {
				Date dataDoEvento = (Date) item.getModelObject();
				
				AjaxLink<String> linkIncluirDia = criarButtonIncluirDiaEvento(dataDoEvento);
				linkIncluirDia.add(new Label("mon", Util.getMesAbreviadoDate(dataDoEvento)));
				linkIncluirDia.add(new Label("day", Util.getDiaSemanaDate(dataDoEvento)));
				
				item.add(linkIncluirDia);
				
				item.add(criarListViewEventosDoDiaCalendario(dataDoEvento));
			}
		};
		listViewPermissaoAcesso.setOutputMarkupId(true);
		return listViewPermissaoAcesso;
	}

	private AjaxLink<String> criarButtonIncluirDiaEvento(final Date date){
		AjaxLink<String> ajaxButton =  new AjaxLink<String>("buttonNovoEventoDia") {
			private static final long serialVersionUID = 1L;
			@Override
			
			public void onClick(AjaxRequestTarget target) {
				EventoPanel editPanel = new EventoPanel(modalIncluirEditar.getContentId());
				editPanel.setOutputMarkupId(true);
				
				Evento evento = new Evento();
				evento.setDataFim(date);
				evento.setDataInicio(date);
				evento.setAgenda(agenda);
				
				EventoEditForm cadastroAlunoEditForm = new EventoEditForm(evento,editPanel,null,divListagem,modalIncluirEditar);
				cadastroAlunoEditForm.setOutputMarkupId(true);
				editPanel.add(cadastroAlunoEditForm);
				
				modalIncluirEditar.setContent(editPanel);
				modalIncluirEditar.show(target);
			};
		};
		
		return ajaxButton;
	}
	
	
	private AjaxButton criarButtonIncluir(Form<Agenda> form){
		AjaxButton ajaxButton =  new AjaxButton("buttonNovoEvento",form) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				EventoPanel editPanel = new EventoPanel(modalIncluirEditar.getContentId());
				editPanel.setOutputMarkupId(true);
				getForm().add(editPanel);
				
				Evento evento = new Evento();
				evento.setAgenda(agenda);
				
				EventoEditForm cadastroAlunoEditForm = new EventoEditForm(evento,editPanel,null,divListagem,modalIncluirEditar);
				cadastroAlunoEditForm.setOutputMarkupId(true);
				editPanel.add(cadastroAlunoEditForm);
				
				modalIncluirEditar.setContent(editPanel);
				modalIncluirEditar.show(target);
			}
		};
		
		return ajaxButton;
	}
	
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	
	public Evento getEvento() {
		return evento;
	}
	
}
