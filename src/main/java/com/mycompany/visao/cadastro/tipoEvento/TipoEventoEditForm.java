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
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Administracao;
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
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoCurso());
	}
	
	private static final long serialVersionUID = 1L;

	
}
