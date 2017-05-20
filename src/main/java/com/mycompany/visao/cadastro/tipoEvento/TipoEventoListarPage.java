package com.mycompany.visao.cadastro.tipoEvento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class TipoEventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="tipoEventoServico")
	static ITipoEventoServico tipoEventoServico;
	
	static TipoEvento tipoEvento = new TipoEvento();
	
	public TipoEventoListarPage(){
		super(tipoEvento);
		addFiltros();
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = tipoEventoServico;
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
