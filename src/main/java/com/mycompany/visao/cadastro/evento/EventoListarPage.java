package com.mycompany.visao.cadastro.evento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Evento;
import com.mycompany.services.interfaces.IEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class EventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="eventoServico")
	static IEventoServico eventoServico;
	
	static Evento evento = new Evento();
	
	public EventoListarPage(){
		setServiceComum(eventoServico);
		setAbstractBean(evento);
		adicionaCampos();
		addFiltros();
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
