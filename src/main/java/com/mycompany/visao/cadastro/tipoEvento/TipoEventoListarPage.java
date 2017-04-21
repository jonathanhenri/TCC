package com.mycompany.visao.cadastro.tipoEvento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Curso;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.TipoEvento;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class TipoEventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="tipoEventoServico")
	static ITipoEventoServico tipoEventoServico;
	
	static TipoEvento tipoEvento = new TipoEvento();
	
	public TipoEventoListarPage(){
		super(tipoEvento,10,tipoEventoServico);
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
