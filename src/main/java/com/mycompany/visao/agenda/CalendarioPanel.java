package com.mycompany.visao.agenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.Util;
import com.mycompany.visao.cadastro.evento.EventoEditForm;
import com.mycompany.visao.cadastro.evento.EventoPanel;

public class CalendarioPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private Agenda agenda;
	private Evento evento;
	private WebMarkupContainer divListagem;
	private HashMap<Date, List<Evento>> hashMapEventoAgrupado;
	protected ModalWindow modalIncluirEditar;

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
	
	private void criarFormEventosCalendario(){
		divListagem = new WebMarkupContainer("divListagem");
		divListagem.setOutputMarkupId(true);
		
		Form<Agenda> formListagem = new Form<Agenda>("form");
		formListagem.setOutputMarkupId(true);
		
		formListagem.add(criarListViewEventosCalendario());
		formListagem.add(criarButtonIncluir(formListagem));
		formListagem.add(criarModalIncluirEditar());
	
		divListagem.add(formListagem);
		add(divListagem);
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
				
				WebMarkupContainer divConteinerItens = new WebMarkupContainer("containerItens");
				divConteinerItens.add(new AttributeModifier("data-target", "#"+evento.getId()));
				divConteinerItens.add(criarLinkExcluirEvento(evento));
				
				divConteinerItens.add(new Label("descricao", evento.getDescricao()));
		
				divConteinerItens.add(new Label("dataInicio", Util.getDateFormat(evento.getDataInicio())));
				divConteinerItens.add(new Label("dataFim", Util.getDateFormat(evento.getDataFim())));
				divConteinerItens.add(new Label("local",evento.getLocal()));
				
				WebMarkupContainer divContainerModal = new WebMarkupContainer("containerModal");
				divContainerModal.add(new AttributeModifier("id", evento.getId()));
				divContainerModal.add(new Label("observacao",evento.getObservacao()));
				divContainerModal.add(new Label("descricao2", evento.getDescricao()));
				item.add(divContainerModal);	
				
			
				item.add(divConteinerItens);	
			}
		};
		listViewPermissaoAcesso.add(new AttributeModifier("id", "#myModal"));
		listViewPermissaoAcesso.setOutputMarkupId(true);
		return listViewPermissaoAcesso;
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
