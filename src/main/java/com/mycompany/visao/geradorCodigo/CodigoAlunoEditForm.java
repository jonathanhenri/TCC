package com.mycompany.visao.geradorCodigo;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IPerfilAcessoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class CodigoAlunoEditForm extends EditForm<CodigoAluno> {
	
	@SpringBean(name="codigoAlunoServico")
	private static ICodigoAlunoServico codigoAlunoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	@SpringBean(name="alunoServico")
	private  IAlunoServico alunoServico;
	
	@SpringBean(name="perfilAcessoServico")
	private  IPerfilAcessoServico perfilAcessoServico;
	
	private CodigoAluno codigoAluno;
	
	public CodigoAlunoEditForm(String id, CodigoAluno codigoAluno,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, codigoAluno,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.codigoAluno = codigoAluno;
	}
	
	public CodigoAlunoEditForm(CodigoAluno codigoAluno,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", codigoAluno,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.codigoAluno = codigoAluno;
	}
	
	@Override
	protected Boolean ignorarValidacaoCampoObrigatorio() {
		return true;
	}
	@Override
	protected void setServicoComum() {
		serviceComum = codigoAlunoServico;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoCurso());
		add(criarCampoPerfilAcesso());
		add(criarCampoQuantidadeAluno());
	}

	
	private DropDownChoice<PerfilAcesso> criarCampoPerfilAcesso(){
		IChoiceRenderer<PerfilAcesso> choiceRenderer = new ChoiceRenderer<PerfilAcesso>("nome", "id");
		LoadableDetachableModel<List<PerfilAcesso>> perfis = new LoadableDetachableModel<List<PerfilAcesso>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<PerfilAcesso> load() {
				List<PerfilAcesso> perfis = new ArrayList<PerfilAcesso>();
				perfis = perfilAcessoServico.search(new Search(PerfilAcesso.class));
				if(perfis!=null && perfis.size() == 1){
					getAbstractBean().setPerfilAcesso(perfis.get(0));
				}
				return perfis;
			}
		};
		
		final DropDownChoice<PerfilAcesso> tipoRadioChoice = new DropDownChoice<PerfilAcesso>("perfilAcesso", perfis,choiceRenderer);
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private NumberTextField<Integer> criarCampoQuantidadeAluno(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("quantidadeAlunosAux");
		duracao.setOutputMarkupId(true);
		return duracao;
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
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("cursoAux", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(false);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	
	private static final long serialVersionUID = 1L;

	
	
}
