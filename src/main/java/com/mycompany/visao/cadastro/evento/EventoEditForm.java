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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Evento;
import com.mycompany.domain.Materia;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.ICursoServico;
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
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private WebMarkupContainer divRepetirEvento;
	
	private Integer periodoAux = 0;
	private WebMarkupContainer divPeriodos;
	private NumberTextField<Integer> campoNumberPeriodoAux;
	private List<RelacaoPeriodo> listaPeriodosSelecionados;
	private DropDownChoice<Materia> tipoRadioChoiceMateria;
	private DropDownChoice<OrigemEvento> tipoRadioChoiceOrigemEvento;
	private DropDownChoice<TipoEvento> tipoRadioChoiceTipoEvento;
	private Boolean origemCalendario = false;
	
	private Form<Evento> formEvento;
	private Evento eventoAux = new Evento();
	
	public EventoEditForm(Evento evento,Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		getAbstractBean().setAgenda(agenda);
		
		if(getAbstractBean().getId() == null){
			getAbstractBean().setRepetirEvento(false);
		}
	}
	
	public EventoEditForm(Evento evento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar,Boolean origemCalendario) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		if(getAbstractBean().getId() == null){
			getAbstractBean().setRepetirEvento(false);
		}
		this.origemCalendario = origemCalendario;
	}
	
	public EventoEditForm(Evento evento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", evento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		if(getAbstractBean().getId() == null){
			getAbstractBean().setRepetirEvento(false);
		}
	}
	
	
	private DropDownChoice<Curso> criarCampoCurso(){
		IChoiceRenderer<Curso> choiceRenderer = new ChoiceRenderer<Curso>("nome", "id");
		
		LoadableDetachableModel<List<Curso>> cursos = new LoadableDetachableModel<List<Curso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Curso> load() {
				List<Curso> cursos = new ArrayList<Curso>();
				
				cursos = cursoServico.search(new Search(Curso.class));
				
				if(cursos!=null && cursos.size() == 1){
					if(getAbstractBean().getAdministracao() == null){
						getAbstractBean().setAdministracao(new Administracao());
					}
					getAbstractBean().getAdministracao().setCurso(cursos.get(0));
				}
				return cursos;
			}
		};
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(serviceComum.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_CURSO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
			
			@Override
			public boolean isEnabled() {
				return !origemCalendario;
			}
		};
		tipoRadioChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(tipoRadioChoiceTipoEvento);
				target.add(tipoRadioChoiceOrigemEvento);
				target.add(tipoRadioChoiceMateria);
			}
		});
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
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
				List<Evento> eventosRecorrentes = new ArrayList<Evento>();
				Search search = new Search(Evento.class);
				search.addFilterNull("agenda");
				eventosRecorrentes = eventoServico.search(search);
				
				return eventosRecorrentes;
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
				if(getEventoAux()!=null){
					eventoNovo = getEventoAux().clonar(false);
					eventoNovo.setAgenda(getAbstractBean().getAgenda());
				}
				
				if(origemCalendario){
					eventoNovo.setAdministracao(Util.clonar(getAbstractBean().getAdministracao(),false));
				}else{
					eventoNovo.setAdministracao(null);
				}
				
				setAbstractBean(eventoNovo);
				setModelObject(eventoNovo);
				formEvento.modelChanged();
				target.add(formEvento);
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
				
				Search search = new Search(Materia.class);
				if(getAbstractBean().getAdministracao()!=null && getAbstractBean().getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id", getAbstractBean().getAdministracao().getCurso().getId());
				}
				
				tiposEvento = materiaServico.search(search);
				
				return tiposEvento;
			}
		};
		
		tipoRadioChoiceMateria = new DropDownChoice<Materia>("materia", materias,choiceRenderer);
		tipoRadioChoiceMateria.setNullValid(true);
		tipoRadioChoiceMateria.setOutputMarkupId(true);
		
		return tipoRadioChoiceMateria;
	}
	
	private DropDownChoice<OrigemEvento> criarCampoOrigemEvento(){
		IChoiceRenderer<OrigemEvento> choiceRenderer = new ChoiceRenderer<OrigemEvento>("nome", "id");
		LoadableDetachableModel<List<OrigemEvento>> origensEventos = new LoadableDetachableModel<List<OrigemEvento>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<OrigemEvento> load() {
				List<OrigemEvento> tiposEvento = new ArrayList<OrigemEvento>();
				
				Search search = new Search(OrigemEvento.class);
				if(getAbstractBean().getAdministracao()!=null && getAbstractBean().getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id", getAbstractBean().getAdministracao().getCurso().getId());
				}
				
				tiposEvento = origemEventoServico.search(search);
				
				return tiposEvento;
			}
		};
		
		tipoRadioChoiceOrigemEvento = new DropDownChoice<OrigemEvento>("origemEvento", origensEventos,choiceRenderer);
		tipoRadioChoiceOrigemEvento.setNullValid(true);
		tipoRadioChoiceOrigemEvento.setOutputMarkupId(true);
		
		return tipoRadioChoiceOrigemEvento;
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
				
				Search search = new Search(TipoEvento.class);
				if(getAbstractBean().getAdministracao()!=null && getAbstractBean().getAdministracao().getCurso()!=null){
					search.addFilterEqual("administracao.curso.id", getAbstractBean().getAdministracao().getCurso().getId());
				}
				tiposEvento = tipoEventoServico.search(search);
				
				return tiposEvento;
			}
		};
		
		tipoRadioChoiceTipoEvento = new DropDownChoice<TipoEvento>("tipoEvento", tiposEventos,choiceRenderer);
		tipoRadioChoiceTipoEvento.setNullValid(true);
		tipoRadioChoiceTipoEvento.setOutputMarkupId(true);
		
		return tipoRadioChoiceTipoEvento;
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
		TextField<String> textFieldDescricao = new TextField<String>("descricao");
		textFieldDescricao.setOutputMarkupId(true);
		textFieldDescricao.add(StringValidator.lengthBetween(1, 600));
		return textFieldDescricao;
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
		
		formEvento = new Form<Evento>("formTeste");
		formEvento.add(criarCampoDescricao());
		formEvento.add(criarCampoCurso());
		formEvento.add(criarButtonAdicionarPeriodo());
		formEvento.add(criarCampoPeriodoAux());
		formEvento.add(criarListViewPeriodos());
		formEvento.add(criarCampoObservacao());
		formEvento.add(criarCampoLocal());
		formEvento.add(criarCampoProfessor());
		formEvento.add(criarCampoOrigemEvento());
		formEvento.add(criarCampoTipoEvento());
		formEvento.add(criarCampoMateria());
		formEvento.add(criarDivRepetirEvento());
		formEvento.add(criarCampoRepetirEvento());
		formEvento.add(criarListEventosRecorrentes());
		formEvento.setOutputMarkupId(true);
		addOrReplace(formEvento);
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
