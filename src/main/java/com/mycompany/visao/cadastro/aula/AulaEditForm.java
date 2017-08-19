package com.mycompany.visao.cadastro.aula;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Aula;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.services.interfaces.IAulaServico;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class AulaEditForm extends EditForm<Aula> {
	
	@SpringBean(name="aulaServico")
	private static IAulaServico aulaServico;
	
	@SpringBean(name="materiaServico")
	private static IMateriaServico materiaServico;
	
	@SpringBean(name="cursoServico")
	private  ICursoServico cursoServico;
	
	private Aula aula;
	
	public AulaEditForm(String id, Aula aula,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, aula,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.aula = aula;
	}
	
	public AulaEditForm(Aula aula,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", aula,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.aula = aula;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = aulaServico;
	}
	
	private TextArea<String> criarCampoObservacao(){
		TextArea<String> textArea = new TextArea<String>("observacao");
		textArea.setOutputMarkupId(true);
		textArea.add(StringValidator.lengthBetween(0, 600));
		return textArea;
	}
	

	private DateTextField criarCampoDataFim(){
		
		DatePicker datePicker = new DatePicker(){
			private static final long serialVersionUID = 1L;
			
			
			@Override
			protected boolean alignWithIcon() {
				return true;
			}
			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}			
		};
		DateTextField dataFim = new DateTextField("dataFim", new PropertyModel<Date>(getAbstractBean(), "dataFim"),new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataFim.add(datePicker);
		dataFim.setOutputMarkupId(true);
		return dataFim;
	}

	private DateTextField criarCampoDataInicio(){
		
		DatePicker datePicker = new DatePicker(){
			private static final long serialVersionUID = 1L;
			
			
			@Override
			protected boolean alignWithIcon() {
				return true;
			}
			@Override
			protected boolean enableMonthYearSelection() {
				return false;
			}			
		};
		DateTextField dataInicio = new DateTextField("dataInicio", new PropertyModel<Date>(getAbstractBean(), "dataInicio"),new PatternDateConverter("dd/MM/yyyy", true));
		datePicker.setAutoHide(true);		
		dataInicio.add(datePicker);
		dataInicio.setOutputMarkupId(true);
		return dataInicio;
	}
	
	private TextField<String> criarCampoLocal(){
		TextField<String> textFieldLocal = new TextField<String>("local");
		textFieldLocal.setOutputMarkupId(true);
		textFieldLocal.add(StringValidator.lengthBetween(0, 100));
		return textFieldLocal;
	}
	
	private TextField<String> criarCampoProfessor(){
		TextField<String> textFieldProfessor = new TextField<String>("professor");
		textFieldProfessor.setOutputMarkupId(true);
		textFieldProfessor.add(StringValidator.lengthBetween(0, 200));
		return textFieldProfessor;
	}
	
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(0, 100));
		return textFieldNome;
	}
	
	private DropDownChoice<Materia> criarCampoMateria(){
		IChoiceRenderer<Materia> choiceRenderer = new ChoiceRenderer<Materia>("nome", "id");
		LoadableDetachableModel<List<Materia>> materias = new LoadableDetachableModel<List<Materia>>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected List<Materia> load() {
				List<Materia> tiposEvento = new ArrayList<Materia>();
				
				tiposEvento = materiaServico.search(new Search(Materia.class));
				
				return tiposEvento;
			}
		};
		
		final DropDownChoice<Materia> tipoRadioChoice = new DropDownChoice<Materia>("materia", materias,choiceRenderer);
		tipoRadioChoice.setNullValid(true);
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
		add(criarCampoLocal());
		add(criarCampoDataFim());
		add(criarCampoDataInicio());
		add(criarCampoMateria());
		add(criarCampoObservacao());
		add(criarCampoProfessor());
	}
	
	private static final long serialVersionUID = 1L;

	
}
