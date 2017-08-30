package com.mycompany.visao.agenda;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
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
	private WebMarkupContainer divEvento;
	private Form<Evento> formEvento;
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
		criarFormEvento();
		criarFormEventosCalendario();
		
	}
	
	private ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(600);
		modalIncluirEditar.setInitialWidth(900);
		modalIncluirEditar.setCloseButtonCallback(null);
		return modalIncluirEditar;
	}
	
	private void popularHashMapEventoAgrupado(){
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
	
		divListagem.add(formListagem);
		add(divListagem);
	}
	
	private void criarFormEvento(){
		divEvento = new WebMarkupContainer("divEvento");
		divEvento.setOutputMarkupId(true);
		
		formEvento = new Form<Evento>("formEvento",new CompoundPropertyModel<Evento>(evento));
		formEvento.setOutputMarkupId(true);
		formEvento.add(	criarModalIncluirEditar());
		formEvento.add(criarButtonNovo(formEvento));
		divEvento.add(formEvento);
		
		addOrReplace(divEvento);
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
				Evento dataDoEvento = (Evento) item.getModelObject();
				
				item.add(new Label("descricao", dataDoEvento.getDescricao()));
				item.add(new Label("dataInicio", Util.getDateFormat(dataDoEvento.getDataInicio())));
				item.add(new Label("dataFim", Util.getDateFormat(dataDoEvento.getDataFim())));
				
			}
		};
		
		return listViewPermissaoAcesso;
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
		
		return listViewPermissaoAcesso;
	}

	private AjaxButton criarButtonNovo(Form<Evento> form){
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
