package com.mycompany.visao.cadastro.aluno;

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
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.domain.RelacaoPeriodo;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IPerfilAcessoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.EditForm;
import com.mycompany.visao.comum.PassowordTextFieldPersonalizado;

public class AlunoEditForm extends EditForm<Aluno> {
	@SpringBean(name="alunoServico")
	private static IAlunoServico alunoServico;
	
	private String senhaAux;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private Integer periodoAux = 0;
	private WebMarkupContainer divPeriodos;
	private NumberTextField<Integer> campoNumberPeriodoAux;
	private List<RelacaoPeriodo> listaPeriodosSelecionados;
	
	@SpringBean(name="perfilAcessoServico")
	private  IPerfilAcessoServico perfilAcessoServico;
	
	
	public AlunoEditForm(Aluno aluno,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", aluno,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		senhaAux = aluno.getSenha()!=null? aluno.getSenha():"";
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = alunoServico;
	}

	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(0, 300));
		return textFieldNome;
	}
	
	
	private TextField<String> criarCampoLogin(){
		TextField<String> textField = new TextField<String>("login");
		textField.setOutputMarkupId(true);
		textField.add(StringValidator.lengthBetween(0, 40));
		return textField;
	}
	
	
	private PassowordTextFieldPersonalizado criarCampoSenha(){
		PassowordTextFieldPersonalizado passwordTextField = new PassowordTextFieldPersonalizado("senha",false);
		passwordTextField.setOutputMarkupId(true);
		passwordTextField.add(StringValidator.lengthBetween(0, 50));
		return passwordTextField;
	}
	
	@Override
	protected void beforeSave() {
		if(getAbstractBean().getSenha() == null){
			getAbstractBean().setSenha(senhaAux);
		}else if(getAbstractBean().getSenha().equals(senhaAux)){
			getAbstractBean().setSenha(senhaAux);
		}
		
		getAbstractBean().setListaPeriodosPertecentes(Util.toSet(listaPeriodosSelecionados));
		
		super.beforeSave();
	}
	
	private DropDownChoice<PerfilAcesso> criarCampoPerfilAcesso(){
		IChoiceRenderer<PerfilAcesso> choiceRenderer = new ChoiceRenderer<PerfilAcesso>("nome", "id");
		LoadableDetachableModel<List<PerfilAcesso>> perfis = new LoadableDetachableModel<List<PerfilAcesso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<PerfilAcesso> load() {
				List<PerfilAcesso> perfis = new ArrayList<PerfilAcesso>();
				perfis = perfilAcessoServico.search(new Search(PerfilAcesso.class));
				return perfis;
			}
		};
		
		final DropDownChoice<PerfilAcesso> tipoRadioChoice = new DropDownChoice<PerfilAcesso>("perfilAcesso", perfis,choiceRenderer){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				if(!Util.possuiPermissao(serviceComum.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR)){
					return false;
				}
				return true;
			}
		};
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private DropDownChoice<Curso> criarCampoCurso(){
		IChoiceRenderer<Curso> choiceRenderer = new ChoiceRenderer<Curso>("nome", "id");
		
		LoadableDetachableModel<List<Curso>> cursos = new LoadableDetachableModel<List<Curso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Curso> load() {
				List<Curso> cursos = new ArrayList<Curso>();
				
				cursos = cursoServico.search(new Search(Curso.class));
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
		};
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
		add(criarCampoNome());
		add(criarCampoLogin());
		add(criarCampoSenha());
		add(criarCampoCurso());
		add(criarCampoPerfilAcesso());
	}
	
	public void setPeriodoAux(Integer periodoAux) {
		this.periodoAux = periodoAux;
	}
	public Integer getPeriodoAux() {
		return periodoAux;
	}
	
	private static final long serialVersionUID = 1L;

	
}
