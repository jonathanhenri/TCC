package com.mycompany.visao.cadastro;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.mycompany.domain.AbstractBean;

public class PainelAcao extends Panel {
	private static final long serialVersionUID = 1L;
	
	public PainelAcao(String id,AjaxLink<AbstractBean<?>> linkEditar,AjaxLink<AbstractBean<?>> linkExcluir) {
		super(id);
		add(linkEditar);
		add(linkExcluir);
	}
}
