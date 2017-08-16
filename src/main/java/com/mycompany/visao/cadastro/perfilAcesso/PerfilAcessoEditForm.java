package com.mycompany.visao.cadastro.perfilAcesso;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IPerfilAcessoServico;
import com.mycompany.services.interfaces.IPermissaoAcessoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.EditForm;

public class PerfilAcessoEditForm extends EditForm<PerfilAcesso> {
	
	@SpringBean(name="perfilAcessoServico")
	private static IPerfilAcessoServico perfilAcessoServico;
	
	
	@SpringBean(name="permissaoAcessoServico")
	private static IPermissaoAcessoServico permissaoAcessoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private PerfilAcesso perfilAcesso;
	
	private List<PermissaoAcesso> permissoesSelecionados;
	private CheckGroup<PermissaoAcesso> checkGroup;
	
	public PerfilAcessoEditForm(String id, PerfilAcesso perfilAcesso,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, perfilAcesso,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.perfilAcesso = perfilAcesso;
	}
	
	public PerfilAcessoEditForm(PerfilAcesso perfilAcesso,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", perfilAcesso,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.perfilAcesso = perfilAcesso;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = perfilAcessoServico;
	}
	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
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
				
				return cursos;
			}
		};
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private ListView<PermissaoAcesso> criarListViewPermissoesAcesso(){
		LoadableDetachableModel<List<PermissaoAcesso>> loadPermissao = new LoadableDetachableModel<List<PermissaoAcesso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<PermissaoAcesso> load() {
				return permissaoAcessoServico.search(new Search(PermissaoAcesso.class));
			}
		};
			
		ListView<PermissaoAcesso> listViewPermissaoAcesso = new ListView<PermissaoAcesso>("permissoesAcesso",loadPermissao) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<PermissaoAcesso> item) {
				PermissaoAcesso permissao = (PermissaoAcesso) item.getModelObject();		
				
				final Check<PermissaoAcesso> chkSeleciona = new Check<PermissaoAcesso>("chkSeleciona", item.getModel(), checkGroup);
				chkSeleciona.add(new AjaxEventBehavior("onchange") {
					private static final long serialVersionUID = 1L;

					@Override
					protected void onEvent(AjaxRequestTarget target) {
						if(permissoesSelecionados != null){
							PermissaoAcesso objetoEncontrado = null;
							
				        	for(PermissaoAcesso permissaoTemp:permissoesSelecionados){
				        		if(permissaoTemp.getId().equals(((PermissaoAcesso)chkSeleciona.getModelObject()).getId())){
				        			objetoEncontrado = permissaoTemp;
				        			break;
				        		}
				        	}
				        	
				        	if(objetoEncontrado != null){
				        		permissoesSelecionados.remove(objetoEncontrado);
				        	}else{
				        		permissoesSelecionados.add((PermissaoAcesso)chkSeleciona.getModelObject());
				        	}
			        	}
					}
				});
				chkSeleciona.setOutputMarkupId(true);
				
				if(permissoesSelecionados.size()>0){
					for(PermissaoAcesso entradaEstoqueTemp: permissoesSelecionados){
						if(entradaEstoqueTemp.getId().equals(((PermissaoAcesso)chkSeleciona.getModelObject()).getId())){
							chkSeleciona.add(new AttributeModifier("checked", "checked"));
							break;
						}
					}
				}
				
				item.add(chkSeleciona);
				
				item.add(new Label("nome", permissao.getNome()));
			}
		};
		
		return listViewPermissaoAcesso;
	}
	
	private void inicializarPermissoes(){
		perfilAcesso = (PerfilAcesso)getAbstractBean();
		permissoesSelecionados = new ArrayList<PermissaoAcesso>();
		
		if(perfilAcesso.getId() != null){
			if(perfilAcesso.getPermissoesAcesso()!=null && perfilAcesso.getPermissoesAcesso().size()>0){
				for(PermissaoAcesso permissaoAcesso:perfilAcesso.getPermissoesAcesso()){
					permissoesSelecionados.add(permissaoAcesso);
				}
			}
		}
	}
	
	@Override
	protected void beforeSave() {
		perfilAcesso.setPermissoesAcesso(Util.toSet(permissoesSelecionados));
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoCurso());
		inicializarPermissoes();
		checkGroup = new CheckGroup<PermissaoAcesso>("chkGroup", permissoesSelecionados);
		checkGroup.setOutputMarkupId(true);
		checkGroup.setRenderBodyOnly(false);
		checkGroup.add(criarListViewPermissoesAcesso());
		add(checkGroup);
		checkGroup.add(new CheckGroupSelector("chkGroupSelector", checkGroup).setOutputMarkupId(true));
		
	}
	
	private static final long serialVersionUID = 1L;

	
}
