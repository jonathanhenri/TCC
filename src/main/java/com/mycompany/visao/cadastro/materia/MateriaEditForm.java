package com.mycompany.visao.cadastro.materia;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class MateriaEditForm extends EditForm<Materia> {
	
	@SpringBean(name="materiaServico")
	private static IMateriaServico materiaServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private Materia materia;
	
	public MateriaEditForm(String id, Materia materia,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, materia,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.materia = materia;
	}
	
	public MateriaEditForm(Materia materia,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", materia,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.materia = materia;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = materiaServico;
	}
	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 300));
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
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("periodo");
		duracao.setOutputMarkupId(true);
		return duracao;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoPeriodo());
		add(criarCampoCurso());
	}
	
	private static final long serialVersionUID = 1L;

	
}
