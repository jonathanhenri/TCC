package com.mycompany.visao.cadastro.aula;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aula;
import com.mycompany.services.interfaces.IAulaServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class AulaListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="aulaServico")
	static IAulaServico aulaServico;
	
	static Aula aula = new Aula();
	
	public AulaListarPage(){
		super(aula);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = aulaServico;
	}
	
	private void addFiltros(){
		form.add(criarCampoPeriodo());
		form.add(criarCampoNome());
	}
	

	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(780);
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
		getModalIncluirEditar().setContent(criarPanel(new Aula()));
		getModalIncluirEditar().show(target);
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		AulaPanel materiaPanel = new AulaPanel(getModalIncluirEditar().getContentId());
		materiaPanel.setOutputMarkupId(true);
		getForm().add(materiaPanel);
		
		AulaEditForm editForm = new AulaEditForm((Aula)abstractBean, materiaPanel, getFeedbackPanel(), getAtualizarListarPage(), getModalIncluirEditar());
		editForm.setOutputMarkupId(true);
		materiaPanel.add(editForm);
		return materiaPanel;
	}
	
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
		getModalIncluirEditar().setContent(criarPanel((aulaServico.searchFechId(abstractBean))));
		getModalIncluirEditar().show(target);
	}

	@Override
	protected ModalWindow criarModalFiltros() {
		modalFiltros = new ModalWindow("modalFiltros");
		modalFiltros.setOutputMarkupId(true);
		modalFiltros.setInitialHeight(400);
		modalFiltros.setInitialWidth(500);
		
		modalFiltros.setCloseButtonCallback(null);
		
		return modalFiltros;
	}
	
}
