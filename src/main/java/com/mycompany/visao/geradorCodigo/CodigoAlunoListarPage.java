package com.mycompany.visao.geradorCodigo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
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
	
	static CodigoAluno codigoAluno = new CodigoAluno();
	
	public CodigoAlunoListarPage(){
		super(codigoAluno);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = codigoAlunoServico;
	}
	
	
	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(200);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
	}
	
	private void addFiltros(){
	}
	


	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		// TODO Auto-generated method stub
		
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		
		return null;
	}
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
	}

	
}
