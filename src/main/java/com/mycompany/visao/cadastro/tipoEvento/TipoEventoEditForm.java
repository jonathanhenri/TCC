package com.mycompany.visao.cadastro.tipoEvento;

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
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class TipoEventoEditForm extends EditForm<TipoEvento> {
	
	@SpringBean(name="tipoEventoServico")
	private static ITipoEventoServico tipoEventoServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private TipoEvento tipoEvento;
	
	public TipoEventoEditForm(String id, TipoEvento tipoEvento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, tipoEvento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.tipoEvento = tipoEvento;
	}
	
	public TipoEventoEditForm(TipoEvento tipoEvento,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", tipoEvento,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.tipoEvento = tipoEvento;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = tipoEventoServico;
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
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("curso", cursos,choiceRenderer);
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
