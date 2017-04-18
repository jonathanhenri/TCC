package com.mycompany.visao.comum;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;

import com.mycompany.domain.Curso;
import com.mycompany.util.Util;
import com.mycompany.visao.cadastro.Cadastro;
import com.mycompany.visao.cadastro.aluno.AlunoListarPage;
import com.mycompany.visao.cadastro.curso.CursoListarPage;
import com.mycompany.visao.login.Login;

public class Menu extends WebPage {
	private static final long serialVersionUID = 1L;

	public Menu(){

		if(Util.getAlunoLogado() == null || Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId() == null){
			setResponsePage(Login.class);
		}
		
		add(new AjaxLink<String>("link_cadastro") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Cadastro.class);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_curso") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CursoListarPage.class);
			}
		});
//		
//		
		add(new AjaxLink<String>("link_listagem") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AlunoListarPage.class);
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
