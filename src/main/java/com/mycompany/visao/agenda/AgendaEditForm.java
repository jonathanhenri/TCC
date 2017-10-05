package com.mycompany.visao.agenda;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Curso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.EditForm;

public class AgendaEditForm extends EditForm<Agenda> {
	
	@SpringBean(name="agendaServico")
	private static IAgendaServico agendaServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private Integer periodoAux = 0;
	private WebMarkupContainer divPeriodos;
	private NumberTextField<Integer> campoNumberPeriodoAux;
	private List<RelacaoPeriodo> listaPeriodosSelecionados;
	
	public AgendaEditForm(String id, Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, agenda,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
	}
	
	public AgendaEditForm(Agenda agenda,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", agenda,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = agendaServico;
	}
	
	
	@Override
	protected void beforeSave() {
		getAbstractBean().setListaPeriodosPertecentes(Util.toSet(listaPeriodosSelecionados));
		super.beforeSave();
	}
	private TextField<String> criarCampodescricao(){
		TextField<String> textFieldDescricao = new TextField<String>("descricao");
		textFieldDescricao.setOutputMarkupId(true);
		textFieldDescricao.add(StringValidator.lengthBetween(0, 300));
		return textFieldDescricao;
	}
	
	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(0, 300));
		return textFieldNome;
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
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
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
	protected void adicionarCampos() {
		if(getAbstractBean().getListaPeriodosPertecentes()!=null && getAbstractBean().getListaPeriodosPertecentes().size()>0){
			listaPeriodosSelecionados = Util.toList(getAbstractBean().getListaPeriodosPertecentes());
		}else{
			listaPeriodosSelecionados = new ArrayList<RelacaoPeriodo>();
		}
		add(criarButtonAdicionarPeriodo());
		add(criarCampoPeriodoAux());
		add(criarListViewPeriodos());
		add(criarCampodescricao());
		add(criarCampoNome());
		add(criarCampoCurso());
	}
	
	public void setPeriodoAux(Integer periodoAux) {
		this.periodoAux = periodoAux;
	}
	public Integer getPeriodoAux() {
		return periodoAux;
	}
	
	private static final long serialVersionUID = 1L;

	
}
