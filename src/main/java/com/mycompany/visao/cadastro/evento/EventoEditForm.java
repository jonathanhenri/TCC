package com.mycompany.visao.cadastro.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.ColorTextField;
import com.mycompany.visao.comum.EditForm;

public class EventoEditForm extends EditForm<Evento> {
	
	@SpringBean(name="eventoServico")
	private  IEventoServico eventoServico;

	@SpringBean(name="materiaServico")
	private  IMateriaServico materiaServico;
	
	@SpringBean(name="tipoEventoServico")
	private  ITipoEventoServico tipoEventoServico;
	
	@SpringBean(name="origemEventoServico")
	private  IOrigemEventoServico origemEventoServico;
	
	private WebMarkupContainer divRepetirEvento;
	
	
	private Evento evento;
	
	public EventoEditForm(Evento evento,Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.evento = evento;
		this.evento.setAgenda(agenda);
	}
	
	public EventoEditForm(Evento evento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.evento = evento;
	}
	
	
	@Override
	protected void setServicoComum() {
		serviceComum = eventoServico;
	}
	
	private CheckBox criarCheckBoxRepetirDomingo(){
		CheckBox checkBox = new CheckBox("repetirTodoDomingo"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	
	private CheckBox criarCheckBoxRepetirSabado(){
		CheckBox checkBox = new CheckBox("repetirTodoSabado"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	private CheckBox criarCheckBoxRepetirSexta(){
		CheckBox checkBox = new CheckBox("repetirTodaSexta"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	private CheckBox criarCheckBoxRepetirQuinta(){
		CheckBox checkBox = new CheckBox("repetirTodaQuinta"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	
	private CheckBox criarCheckBoxRepetirQuarta(){
		CheckBox checkBox = new CheckBox("repetirTodaQuarta"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	
	private CheckBox criarCheckBoxRepetirTerca(){
		CheckBox checkBox = new CheckBox("repetirTodaTerca"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	private CheckBox criarCheckBoxRepetirTodosDias(){
		CheckBox checkBox = new CheckBox("repetirTodosDias"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	
	
	private CheckBox criarCheckBoxRepetirSegunda(){
		CheckBox checkBox = new CheckBox("repetirTodaSegunda"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?evento.getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	private RadioGroup<Boolean> criarCampoRepetirEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("repetirEvento");
		radioGroupAtivo.add(new Radio<Boolean>("repetirEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "repetirEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("repetirEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "repetirEventoNao")));
		radioGroupAtivo.add(new AjaxFormChoiceComponentUpdatingBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(divRepetirEvento);
			}
		});
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	private WebMarkupContainer criarDivRepetirEvento(){
		divRepetirEvento = new WebMarkupContainer("divRepetirEvento");
//		divRepetirEvento.add(criarCheckBoxRepetirTodosDias());
		divRepetirEvento.add(criarCheckBoxRepetirSegunda());
		divRepetirEvento.add(criarCheckBoxRepetirTerca());
		divRepetirEvento.add(criarCheckBoxRepetirQuarta());
		divRepetirEvento.add(criarCheckBoxRepetirQuinta());
		divRepetirEvento.add(criarCheckBoxRepetirSexta());
		divRepetirEvento.add(criarCheckBoxRepetirSabado());
		divRepetirEvento.add(criarCheckBoxRepetirDomingo());
		
		
		divRepetirEvento.add(criarCampoDataFim());
		divRepetirEvento.add(criarCampoDataInicio());
		
		divRepetirEvento.setOutputMarkupId(true);
		divRepetirEvento.setOutputMarkupPlaceholderTag(true);
		
		return divRepetirEvento;
	}
	
	private DropDownChoice<Evento> criarListEventosRecorrentes(){
		IChoiceRenderer<Evento> choiceRenderer = new ChoiceRenderer<Evento>("descricao", "id"){
			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(Evento object) {
				return super.getDisplayValue(object)+" - "+Util.getDateFormat(object.getDataInicio());
			}
			
		};
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
		
		final DropDownChoice<Evento> tipoRadioChoice = new DropDownChoice<Evento>("eventos",new PropertyModel<Evento>(this, "evento") ,eventos,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(getAbstractBean().getId()!=null){
					return false;
				}
				return true;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Evento eventoNovo = evento.clonar(false);
				eventoNovo.setAdministracao(null);
				setAbstractBean(eventoNovo);
				setModelObject(eventoNovo);
				target.add(getRootForm());
				
			}
			
		});
		
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
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
	
	private DateTimeField criarCampoDataFim(){
		DateTimeField dataFim = new DateTimeField("dataFim",new PropertyModel<Date>(getAbstractBean(), "dataFim")){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?!evento.getRepetirEvento():true;
			}
		};
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	private DateTimeField criarCampoDataInicio(){
		DateTimeField dataFim = new DateTimeField("dataInicio",new PropertyModel<Date>(getAbstractBean(), "dataInicio")){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return evento.getRepetirEvento()!=null?!evento.getRepetirEvento():true;
			}
		};
		dataFim.setOutputMarkupId(true);
		return dataFim;
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
	
	private TextField<String> criarCampoProfessor(){
		TextField<String> textFieldNome = new TextField<String>("professor");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 600));
		return textFieldNome;
	}
	
	private TextField<String> criarCampoLocal(){
		TextField<String> textFieldNome = new TextField<String>("local");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 600));
		return textFieldNome;
	}
	
	
	private TextArea<String> criarCampoObservacao(){
		TextArea<String> textArea = new TextArea<String>("observacao");
		textArea.setOutputMarkupId(true);
		return textArea;
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
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoDescricao());
		add(criarCampoObservacao());
		add(criarCampoLocal());
		add(criarCampoProfessor());
		add(criarCampoOrigemEvento());
		add(criarCampoTipoEvento());
		add(criarCampoMateria());
		add(criarDivRepetirEvento());
		add(criarCampoRepetirEvento());
		add(criarCampoCodigoCor());
		add(criarListEventosRecorrentes());
		add(criarCampoPeriodo());
	}
	
	@Override
	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target) {
		return super.validarRegrasAntesSalvarEditar(target);
	}
	private static final long serialVersionUID = 1L;

	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public Evento getEvento() {
		return evento;
	}
	
}
