package com.mycompany.visao.geradorCodigo;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class CodigoAlunoEditForm extends EditForm<CodigoAluno> {
	
	@SpringBean(name="codigoAlunoServico")
	private static ICodigoAlunoServico codigoAlunoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	@SpringBean(name="alunoServico")
	private  IAlunoServico alunoServico;
	
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
	protected void setServicoComum() {
		serviceComum = codigoAlunoServico;
	}

	@Override
	protected Boolean isEnabledEditForm() {
		return false;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoCodigo());
		add(criarCampoAtivo());
		add(criarCampoAluno());
		add(criarCampoCurso());
	}
	
	private TextField<String> criarCampoAluno(){
		TextField<String> textFieldAuno = new TextField<String>("aluno.nome");
		textFieldAuno.setOutputMarkupId(true);
		return textFieldAuno;
	}
	

	private TextField<String> criarCampoCodigo(){
		TextField<String> textFieldCodigo = new TextField<String>("codigo");
		textFieldCodigo.setOutputMarkupId(true);
		return textFieldCodigo;
	}
	
	private RadioGroup<Boolean> criarCampoAtivo(){
		 RadioGroup<Boolean> radioGroup = new RadioGroup<Boolean>("ativo");
		 
		 Radio<Boolean> radioSim = new Radio<Boolean>("sim",new Model<Boolean>(true));
		 Radio<Boolean> radioNao = new Radio<Boolean>("nao",new Model<Boolean>(false));
		 
		 radioGroup.add(radioSim);
		 radioGroup.add(radioNao);
		 
		 return radioGroup;
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
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("curso", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	
	private static final long serialVersionUID = 1L;

	
	
}
