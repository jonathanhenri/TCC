package com.mycompany.visao.cadastro.materia;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.util.WicketUtil;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class MateriaListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="materiaServico")
	static IMateriaServico materiaServico;
	
	static Materia materia = new Materia();
	
	public MateriaListarPage(){
		super(materia);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = materiaServico;
	}
	
	private void addFiltros(){
		form.add(criarCampoPeriodo());
		form.add(criarCampoNome());
	}
	

	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(350);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	
	private NumberTextField<Integer> criarCampoPeriodo(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("periodo");
		duracao.setOutputMarkupId(true);
		return duracao;
	}
	
	private TextField<String> criarCampoNome(){
		final TextField<String> nome = new TextField<String>("nome");
		nome.setOutputMarkupId(true);
		return nome;
	}
	
	
	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new Materia()));
		getModalIncluirEditar().show(target);
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		MateriaPanel materiaPanel = new MateriaPanel(getModalIncluirEditar().getContentId());
		materiaPanel.setOutputMarkupId(true);
		getForm().add(materiaPanel);
		
		MateriaEditForm editForm = new MateriaEditForm((Materia)abstractBean, materiaPanel, getFeedbackPanel(), getAtualizarListarPage(), getModalIncluirEditar());
		editForm.setOutputMarkupId(true);
		materiaPanel.add(editForm);
		return materiaPanel;
	}
	
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel(abstractBean));
		getModalIncluirEditar().show(target);
	}

	
	
}
