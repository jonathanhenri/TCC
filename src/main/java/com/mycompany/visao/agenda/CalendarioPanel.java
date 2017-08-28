package com.mycompany.visao.agenda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.Evento;
import com.mycompany.domain.TipoEvento;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.ColorTextField;

public class CalendarioPanel extends Panel {
	private static final long serialVersionUID = 1L;
	private Agenda agenda;
	private Evento evento;
	private WebMarkupContainer divListagem;
	private WebMarkupContainer divEvento;
	private Form<Evento> formEvento;

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
		criarFormEvento();
		criarFormEventosCalendario();
		
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
		formEvento.add(criarListEventosRecorrentes());
		formEvento.add(criarButtonIncluir(formEvento));
		formEvento.add(criarCampoDescricao());
		formEvento.add(criarCampoOrigemEvento());
		formEvento.add(criarCampoTipoEvento());
		formEvento.add(criarCampoDataFim());
		formEvento.add(criarCampoDataInicio());
		formEvento.add(criarCampoMateria());
		formEvento.add(criarCampoCodigoCor());
		formEvento.add(criarCampoPeriodo());
		divEvento.add(formEvento);
		
		addOrReplace(divEvento);
	}
	
	private ColorTextField criarCampoCodigoCor(){
		ColorTextField textFieldCodigoCor = new ColorTextField("codigoCor");
		textFieldCodigoCor.setOutputMarkupId(true);
		textFieldCodigoCor.add(StringValidator.lengthBetween(1, 40));
		return textFieldCodigoCor;
	}

	private DropDownChoice<Materia> criarCampoMateria(){
		IChoiceRenderer<Materia> choiceRenderer = new ChoiceRenderer<Materia>("nome", "id");
		LoadableDetachableModel<List<Materia>> materias = new LoadableDetachableModel<List<Materia>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Materia> load() {
				List<Materia> tiposEvento = new ArrayList<Materia>();
				
				tiposEvento = materiaServico.search(new Search(Materia.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<Materia> tipoRadioChoice = new DropDownChoice<Materia>("materia", materias,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<OrigemEvento> criarCampoOrigemEvento(){
		IChoiceRenderer<OrigemEvento> choiceRenderer = new ChoiceRenderer<OrigemEvento>("nome", "id");
		LoadableDetachableModel<List<OrigemEvento>> origensEventos = new LoadableDetachableModel<List<OrigemEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<OrigemEvento> load() {
				List<OrigemEvento> tiposEvento = new ArrayList<OrigemEvento>();
				
				tiposEvento = origemEventoServico.search(new Search(OrigemEvento.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<OrigemEvento> tipoRadioChoice = new DropDownChoice<OrigemEvento>("origemEvento", origensEventos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DateTextField criarCampoDataFim(){
		
		DatePicker datePicker = new DatePicker(){
			private static final long serialVersionUID = 1L;
			
			
			@Override
			protected boolean alignWithIcon() {
				return true;
			}
			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}			
		};
		DateTextField dataFim = new DateTextField("dataFim",new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataFim.add(datePicker);
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}

	private DateTextField criarCampoDataInicio(){
		
		DatePicker datePicker = new DatePicker(){
			private static final long serialVersionUID = 1L;
			
			
			@Override
			protected boolean alignWithIcon() {
				return true;
			}
			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}			
		};
		DateTextField dataInicio = new DateTextField("dataInicio",new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataInicio.add(datePicker);
		dataInicio.setOutputMarkupId(true);
		return dataInicio;
	}


	
	private DropDownChoice<TipoEvento> criarCampoTipoEvento(){
		IChoiceRenderer<TipoEvento> choiceRenderer = new ChoiceRenderer<TipoEvento>("nome", "id");
		LoadableDetachableModel<List<TipoEvento>> tiposEventos = new LoadableDetachableModel<List<TipoEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<TipoEvento> load() {
				List<TipoEvento> tiposEvento = new ArrayList<TipoEvento>();
				
				tiposEvento = tipoEventoServico.search(new Search(TipoEvento.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<TipoEvento> tipoRadioChoice = new DropDownChoice<TipoEvento>("tipoEvento", tiposEventos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private TextField<String> criarCampoDescricao(){
		TextField<String> textFieldNome = new TextField<String>("descricao");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 600));
		return textFieldNome;
	}
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> periodo = new NumberTextField<Integer>("periodo");
		periodo.setOutputMarkupId(true);
		return periodo;
	}
	
	private ListView<Evento> criarListViewEventosCalendario(){
		LoadableDetachableModel<List<Evento>> loadEventosCalendario = new LoadableDetachableModel<List<Evento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Evento> load() {
				return Util.toList(agenda.getEventos());
			}
		};
			
		ListView<Evento> listViewPermissaoAcesso = new ListView<Evento>("eventosCalendario",loadEventosCalendario) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<Evento> item) {
				Evento permissao = (Evento) item.getModelObject();
				
				DateFormat formata = new SimpleDateFormat("MMM");
				Label labelData = new Label("mon", formata.format(permissao.getDataInicio()));
				item.add(labelData);
				item.add(new Label("day", permissao.getDataInicio().getDay()));
				item.add(new Label("descricao", permissao.getDescricao()));
			}
		};
		
		return listViewPermissaoAcesso;
	}
	
	
	private DropDownChoice<Evento> criarListEventosRecorrentes(){
		IChoiceRenderer<Evento> choiceRenderer = new ChoiceRenderer<Evento>("descricao", "id");
		LoadableDetachableModel<List<Evento>> eventos = new LoadableDetachableModel<List<Evento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Evento> load() {
				List<Evento> tiposEvento = new ArrayList<Evento>();
				Search search = new Search(Evento.class);
				search.addFilterNull("agenda");
				tiposEvento = eventoServico.search(search);
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<Evento> tipoRadioChoice = new DropDownChoice<Evento>("eventos",new PropertyModel<Evento>(this, "evento") ,eventos,choiceRenderer);
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				formEvento.setModelObject(evento);
				formEvento.modelChanged();
				target.add(divEvento);
				
			}
		});
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}

	private AjaxButton criarButtonIncluir(Form<Evento> form){
		AjaxButton ajaxButton =  new AjaxButton("incluir",form) {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				Evento eventoNovo = formEvento.getModelObject().clonar(false);
				eventoNovo.setAdministracao(null);
				eventoNovo.setAgenda(agenda);
				Retorno retorno = eventoServico.persist(eventoNovo);
				
				if(retorno.getSucesso()){
					agenda.addEvento(eventoNovo);
					agendaServico.save(agenda);
				}
				
				 for(Mensagem mensagem:retorno.getListaMensagem()){
					 Util.notify(target, mensagem.toString(), mensagem.getTipo());
		        }
				 target.add(divListagem);
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
