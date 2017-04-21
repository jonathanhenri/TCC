package com.mycompany.visao.cadastro.origemEvento;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Curso;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.visao.cadastro.ListarPageGenerico;


public class OrigemEventoListarPage extends ListarPageGenerico {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="origemEventoServico")
	static IOrigemEventoServico origemEventoServico;
	
	static OrigemEvento origemEvento = new OrigemEvento();
	
	public OrigemEventoListarPage(){
		super(origemEvento,10,origemEventoServico);
		addFiltros();
	}
	
	private void addFiltros(){
	}

	@Override
	protected void getEditFormIncluir(AjaxRequestTarget target) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getNomeTituloListarPage() {
		return "Listagem de Origem do evento";
	}

	private Panel criarPanel(AbstractBean<?> abstractBean){
		
		return null;
	}
	@Override
	protected void getEditFormEditar(AjaxRequestTarget target,AbstractBean<?> abstractBean) {
	}

		
	
}
