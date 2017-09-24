package com.mycompany.visao.agenda;

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
import com.mycompany.domain.Agenda;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.IAgendaServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class AgendaEditForm extends EditForm<Agenda> {
	
	@SpringBean(name="agendaServico")
	private static IAgendaServico agendaServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
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
				
				return cursos;
			}
		};
		
		final DropDownChoice<Curso> tipoRadioChoice = new DropDownChoice<Curso>("administracao.curso", cursos,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> periodo = new NumberTextField<Integer>("periodo");
		periodo.setOutputMarkupId(true);
		return periodo;
	}
	
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoPeriodo());
		add(criarCampodescricao());
		add(criarCampoNome());
		add(criarCampoCurso());
	}
	
	private static final long serialVersionUID = 1L;

	
}
