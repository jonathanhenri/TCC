package com.mycompany.visao.comum;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class MensagemExcluirPanel extends Panel {
	private static final long serialVersionUID = 1L;

	public MensagemExcluirPanel(String id,String mensagem) {
		super(id);
		add(criarCampoMensagem(mensagem));
		add(criarLinkNao());
		add(criarLinkSim());
	}
	
	private AjaxLink<String> criarLinkSim() {
		AjaxLink<String> linkSim = new AjaxLink<String>("linkSim") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				executarAoClicarSim(target);
			}
		};
		linkSim.setOutputMarkupId(true);
		return linkSim;
	}
	
	private AjaxLink<String> criarLinkNao() {
		AjaxLink<String> linkSim = new AjaxLink<String>("linkNao") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				executarAoClicarNao(target);
			}
		};
		linkSim.setOutputMarkupId(true);
		return linkSim;
	}
	
	protected void executarAoClicarSim(AjaxRequestTarget target){
		
	}
	
	protected void executarAoClicarNao(AjaxRequestTarget target){
		
	}
	
	
	private Label criarCampoMensagem(String mensagem){
		Label labelMensagem = new Label("conteudoMensagem",mensagem);
		labelMensagem.setOutputMarkupId(true);
		return labelMensagem;
	}
}
