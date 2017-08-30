package com.mycompany.visao.agenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.AttributeAppender;
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
		formListagem.add(criarButtonNovo(formListagem));
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
				
				item.add(new Label("descricao", evento.getDescricao()));
				item.add(new Label("dataInicio", Util.getDateFormat(evento.getDataInicio())));
				item.add(new Label("dataFim", Util.getDateFormat(evento.getDataFim())));
				item.add(new Label("local",evento.getLocal()));
				
				WebMarkupContainer divTeste = new WebMarkupContainer("testeIdModal");
				divTeste.add(new AttributeAppender("id", "#myModal"));
				divTeste.add(new Label("observacao",evento.getObservacao()));
				item.add(divTeste);
				
				item.add(criarLinkExcluirEvento(evento));
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
				agenda.getEventos().remove(evento);
				agendaServico.save(agenda);
				eventoServico.remove(evento);
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
				item.add(new Label("mon", Util.getMesAbreviadoDate(dataDoEvento)));
				item.add(new Label("day", Util.getDiaSemanaDate(dataDoEvento)));
				
				item.add(criarListViewEventosDoDiaCalendario(dataDoEvento));
			}
		};
		listViewPermissaoAcesso.setOutputMarkupId(true);
		return listViewPermissaoAcesso;
	}

	private AjaxButton criarButtonNovo(Form<Agenda> form){
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
