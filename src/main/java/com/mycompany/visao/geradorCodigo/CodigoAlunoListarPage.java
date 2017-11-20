package com.mycompany.visao.geradorCodigo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class CodigoAlunoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="codigoAlunoServico")
	static ICodigoAlunoServico codigoAlunoServico;
	
	
	public CodigoAlunoListarPage(){
		super(new CodigoAluno(),60);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = codigoAlunoServico;
	}
	
	@Override
	protected String getNomeTituloListarPage() {
		return "Listagem de acessos provisórios";
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
	
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(350);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	
	private void addFiltros(){
		criarCampoCodigo();
	}
	

	@Override
	protected Boolean isVisibleBotaoEditar() {
		return false;
	}
	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		getModalIncluirEditar().setContent(criarPanel(new CodigoAluno()));
		getModalIncluirEditar().show(target);

	}

	private void criarCampoCodigo(){
		final TextField<String> codigo = new TextField<String>("codigo");
		codigo.setOutputMarkupId(true);
		form.add(codigo);
	}
	
	private Panel criarPanel(AbstractBean<?> abstractBean){
		CodigoAlunoPanel codigoAlunoPanel = new CodigoAlunoPanel(getModalIncluirEditar().getContentId());
		codigoAlunoPanel.setOutputMarkupId(true);
		getForm().add(codigoAlunoPanel);
		
		CodigoAlunoEditForm editForm = new CodigoAlunoEditForm((CodigoAluno)abstractBean, codigoAlunoPanel, getFeedbackPanel(), getAtualizarListarPage(), getModalIncluirEditar());
		editForm.setOutputMarkupId(true);
		codigoAlunoPanel.add(editForm);
		return codigoAlunoPanel;
	}
	
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
	}

	
}
