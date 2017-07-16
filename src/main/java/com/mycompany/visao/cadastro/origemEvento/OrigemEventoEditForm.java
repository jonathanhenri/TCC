package com.mycompany.visao.cadastro.origemEvento;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class OrigemEventoEditForm extends EditForm<OrigemEvento> {
	
	@SpringBean(name="origemEventoServico")
	private static IOrigemEventoServico origemEventoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private OrigemEvento origemEvento;
	
	public OrigemEventoEditForm(String id, OrigemEvento origemEvento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, origemEvento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.origemEvento = origemEvento;
	}
	
	public OrigemEventoEditForm(OrigemEvento origemEvento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", origemEvento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.origemEvento = origemEvento;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = origemEventoServico;
	}
	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		return textFieldNome;
	}
	
	private TextField<String> criarCampoCodigoCor(){
		TextField<String> textFieldCodigoCor = new TextField<String>("codigoCor");
		textFieldCodigoCor.setOutputMarkupId(true);
		return textFieldCodigoCor;
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
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoCurso());
		add(criarCampoCodigoCor());
	}
	
	private static final long serialVersionUID = 1L;

	
}
