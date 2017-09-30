package com.mycompany.visao.cadastro.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DateTimeField;
import org.apache.wicket.extensions.yui.calendar.TimeField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
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
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
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
	
	private Integer periodoAux = 0;
	private WebMarkupContainer divPeriodos;
	private NumberTextField<Integer> campoNumberPeriodoAux;
	private List<RelacaoPeriodo> listaPeriodosSelecionados;
	
	private Evento eventoAux = new Evento();
	
	public EventoEditForm(Evento evento,Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		getAbstractBean().setAgenda(agenda);
		
		if(getAbstractBean().getId() == null){
			getAbstractBean().setRepetirEvento(false);
		}
	}
	
	public EventoEditForm(Evento evento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		if(getAbstractBean().getId() == null){
			getAbstractBean().setRepetirEvento(false);
		}
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
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
				return getAbstractBean().getRepetirEvento()!=null?getAbstractBean().getRepetirEvento():false;
			}
		};
		checkBox.setOutputMarkupId(true);
		return checkBox;
	}
	private RadioGroup<Boolean> criarCampoRepetirEvento() {
		RadioGroup<Boolean> radioGroupRepetirEvento = new RadioGroup<Boolean>("repetirEvento");
		radioGroupRepetirEvento.add(new Radio<Boolean>("repetirEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "repetirEventoSim")));
		radioGroupRepetirEvento.add(new Radio<Boolean>("repetirEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "repetirEventoNao")));
		radioGroupRepetirEvento.add(new AjaxFormChoiceComponentUpdatingBehavior() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(divRepetirEvento);
			}
		});
		radioGroupRepetirEvento.setOutputMarkupId(true);
		return radioGroupRepetirEvento;
	}
	
	
	private WebMarkupContainer criarDivRepetirEvento(){
		divRepetirEvento = new WebMarkupContainer("divRepetirEvento");
		divRepetirEvento.add(criarCheckBoxRepetirSegunda());
		divRepetirEvento.add(criarCheckBoxRepetirTerca());
		divRepetirEvento.add(criarCheckBoxRepetirQuarta());
		divRepetirEvento.add(criarCheckBoxRepetirQuinta());
		divRepetirEvento.add(criarCheckBoxRepetirSexta());
		divRepetirEvento.add(criarCheckBoxRepetirSabado());
		divRepetirEvento.add(criarCheckBoxRepetirDomingo());
		
		divRepetirEvento.add(criarCampoTimeInicio());
		divRepetirEvento.add(criarCampoTimeFim());
		
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
		
		final DropDownChoice<Evento> tipoRadioChoice = new DropDownChoice<Evento>("eventos",new PropertyModel<Evento>(this, "eventoAux") ,eventos,choiceRenderer){
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
				Evento eventoNovo = new Evento();
				eventoNovo.setRepetirEvento(false);
				if(getEventoAux()!=null){
					eventoNovo = getEventoAux().clonar(false);
				}
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
		DateTimeField dataFim = new DateTimeField("dataFim"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return getAbstractBean().getRepetirEvento()!=null?!getAbstractBean().getRepetirEvento():true;
			}
		};
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}
	
	private DateTimeField criarCampoDataInicio(){
		DateTimeField dataFim = new DateTimeField("dataInicio"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return getAbstractBean().getRepetirEvento()!=null?!getAbstractBean().getRepetirEvento():true;
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
	
	
	private TimeField criarCampoTimeFim(){
		TimeField timeField = new TimeField("timeFim", new PropertyModel<Date>(getAbstractBean(), "dataFim"));
		timeField.setOutputMarkupId(true);
		return timeField;
	}
	
	
	private TimeField criarCampoTimeInicio(){
		TimeField timeField = new TimeField("timeInicio", new PropertyModel<Date>(getAbstractBean(), "dataInicio"));
		timeField.setOutputMarkupId(true);
		return timeField;
	}
	
	private WebMarkupContainer criarListViewPeriodos(){
		divPeriodos = new WebMarkupContainer("divPeriodos");
		divPeriodos.setOutputMarkupId(true);
		LoadableDetachableModel<List<RelacaoPeriodo>> loadPermissao = new LoadableDetachableModel<List<RelacaoPeriodo>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<RelacaoPeriodo> load() {
				return listaPeriodosSelecionados;
			}
		};
			
		ListView<RelacaoPeriodo> listViewPeriodos = new ListView<RelacaoPeriodo>("listaPeriodosSelecionados",loadPermissao) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<RelacaoPeriodo> item) {
				RelacaoPeriodo periodo = (RelacaoPeriodo) item.getModelObject();		
				item.add(new Label("periodo", periodo.getPeriodo()));
				item.add(criarExcluirPeriodo(periodo));
			}
		};
		
		divPeriodos.add(listViewPeriodos);
		listViewPeriodos.setOutputMarkupId(true);
		return divPeriodos;
	}
	
	private NumberTextField<Integer> criarCampoPeriodoAux(){
		campoNumberPeriodoAux = new NumberTextField<Integer>("periodoAux",new PropertyModel<Integer>(this, "periodoAux"));
		campoNumberPeriodoAux.setOutputMarkupId(true);
		return campoNumberPeriodoAux;
	}
	
	
	private AjaxButton criarButtonAdicionarPeriodo(){
		AjaxButton ajaxButton = new AjaxButton("adicionarPeriodo",this) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target,Form<?> form) {
				if(getPeriodoAux()!=null && getPeriodoAux()>0){
					listaPeriodosSelecionados.add(new RelacaoPeriodo(getPeriodoAux(), getAbstractBean()));
					setPeriodoAux(0);
					campoNumberPeriodoAux.modelChanged();
					target.add(campoNumberPeriodoAux);
					target.add(divPeriodos);
				}else{
					Util.notifyInfo(target, "Período deve ser maior que zero");
				}
			}
		};
		
		return ajaxButton;
	}
	
	private AjaxLink<String> criarExcluirPeriodo(final RelacaoPeriodo relacaoPeriodo){
		AjaxLink<String> ajaxLink = new AjaxLink<String>("linkExcluirPeriodo") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if(listaPeriodosSelecionados!=null && listaPeriodosSelecionados.size()>0){
					listaPeriodosSelecionados.remove(relacaoPeriodo);
					target.add(divPeriodos);
				}
				
			}
		};
		
		return ajaxLink;
	}
	
	@Override
	protected void beforeSave() {
		getAbstractBean().setListaPeriodosPertecentes(Util.toSet(listaPeriodosSelecionados));
		super.beforeSave();
	}
	
	@Override
	protected void adicionarCampos() {
		if(getAbstractBean().getListaPeriodosPertecentes()!=null && getAbstractBean().getListaPeriodosPertecentes().size()>0){
			listaPeriodosSelecionados = Util.toList(getAbstractBean().getListaPeriodosPertecentes());
		}else{
			listaPeriodosSelecionados = new ArrayList<RelacaoPeriodo>();
		}
		add(criarButtonAdicionarPeriodo());
		add(criarCampoPeriodoAux());
		add(criarListViewPeriodos());
		add(criarCampoDescricao());
		add(criarCampoObservacao());
		add(criarCampoLocal());
		add(criarCampoProfessor());
		add(criarCampoOrigemEvento());
		add(criarCampoTipoEvento());
		add(criarCampoMateria());
		add(criarDivRepetirEvento());
		add(criarCampoRepetirEvento());
		add(criarListEventosRecorrentes());
	}
	
	@Override
	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target) {
		return super.validarRegrasAntesSalvarEditar(target);
	}
	private static final long serialVersionUID = 1L;
	
	public Evento getEventoAux() {
		return eventoAux;
	}
	
	public void setPeriodoAux(Integer periodoAux) {
		this.periodoAux = periodoAux;
	}
	public Integer getPeriodoAux() {
		return periodoAux;
	}
}
