package com.mycompany.visao.comum;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;

import com.mycompany.domain.Curso;
import com.mycompany.domain.TipoEvento;
import com.mycompany.util.Util;
import com.mycompany.visao.cadastro.Cadastro;
import com.mycompany.visao.cadastro.aluno.AlunoListarPage;
import com.mycompany.visao.cadastro.curso.CursoListarPage;
import com.mycompany.visao.cadastro.evento.EventoListarPage;
import com.mycompany.visao.cadastro.materia.MateriaListarPage;
import com.mycompany.visao.cadastro.origemEvento.OrigemEventoListarPage;
import com.mycompany.visao.cadastro.tipoEvento.TipoEventoListarPage;
import com.mycompany.visao.login.Login;

public class Menu extends WebPage {
	private static final long serialVersionUID = 1L;

	public Menu(){

		if(Util.getAlunoLogado() == null || Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId() == null){
			setResponsePage(Login.class);
		}
		
		
		add(new AjaxLink<String>("link_cadastro_curso") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CursoListarPage.class);
			}
		});
//		
//		
		add(new AjaxLink<String>("link_cadastro_aluno") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AlunoListarPage.class);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_origem_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(OrigemEventoListarPage.class);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_materia") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(MateriaListarPage.class);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_tipo_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TipoEventoListarPage.class);
			}
		});
		
		
		add(new AjaxLink<String>("link_cadastro_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(EventoListarPage.class);
			}
		});
		
//		add(new AjaxLink<String>("sair") {
//			private static final long serialVersionUID = 1L;
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				((BasicAuthenticationSession)Session.get()).clear();
//				((BasicAuthenticationSession)Session.get()).invalidate();
//				setResponsePage(Login.class);				
//			}
//		});
		

		
	}
}
