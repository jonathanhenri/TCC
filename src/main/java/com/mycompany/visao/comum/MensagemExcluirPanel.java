package com.mycompany.visao.comum;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import wicket.contrib.input.events.EventType;
import wicket.contrib.input.events.InputBehavior;
import wicket.contrib.input.events.key.KeyType;

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
		AjaxLink<String> linkNao = new AjaxLink<String>("linkNao") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				executarAoClicarNao(target);
			}
		};
		
		linkNao.add(new InputBehavior(new KeyType[] { KeyType.Escape }, EventType.click));
		linkNao.setOutputMarkupId(true);
		return linkNao;
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
