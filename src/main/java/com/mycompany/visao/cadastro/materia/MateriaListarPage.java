package com.mycompany.visao.cadastro.materia;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Materia;
import com.mycompany.services.interfaces.IMateriaServico;
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
	}

	@Override
	protected ModalWindow criarModalIncluirEditar() {
		modalIncluirEditar = new ModalWindow("modalIncluirEditar");
		modalIncluirEditar.setOutputMarkupId(true);
		modalIncluirEditar.setInitialHeight(200);
		modalIncluirEditar.setInitialWidth(600);
		return modalIncluirEditar;
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
		getModalIncluirEditar().show(target);
	}

	
	
}
