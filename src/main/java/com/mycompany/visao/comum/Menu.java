package com.mycompany.visao.comum;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.BasicAuthenticationSession;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.util.Util;
import com.mycompany.visao.cadastro.Index;
import com.mycompany.visao.cadastro.aluno.AlunoListarPage;
import com.mycompany.visao.cadastro.aula.AulaListarPage;
import com.mycompany.visao.cadastro.curso.CursoListarPage;
import com.mycompany.visao.cadastro.evento.EventoListarPage;
import com.mycompany.visao.cadastro.materia.MateriaListarPage;
import com.mycompany.visao.cadastro.origemEvento.OrigemEventoListarPage;
import com.mycompany.visao.cadastro.perfilAcesso.PerfilAcessoListarPage;
import com.mycompany.visao.cadastro.tipoEvento.TipoEventoListarPage;
import com.mycompany.visao.configuracao.ConfiguracaoPage;
import com.mycompany.visao.geradorCodigo.CodigoAlunoListarPage;
import com.mycompany.visao.login.Login;

public class Menu extends WebPage {
	private static final long serialVersionUID = 1L;
	@SpringBean(name="alunoServico")
	static IAlunoServico alunoServico;
	
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
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_CURSO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("link_dashBoard") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(Index.class);
			}
		});
		add(new AjaxLink<String>("link_cadastro_aluno") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AlunoListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_ALUNO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
//		
		add(new AjaxLink<String>("link_cadastro_origem_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(OrigemEventoListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_ORIGEM_EVENTO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
//		
		add(new AjaxLink<String>("link_cadastro_aula") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(AulaListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_AULA_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_materia") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(MateriaListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_MATERIA_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_tipo_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(TipoEventoListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_TIPO_EVENTO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		
		add(new AjaxLink<String>("link_cadastro_evento") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(EventoListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_EVENTO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("link_gerador_codigos") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(CodigoAlunoListarPage.class);
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_ACESSO_PROVISORIO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("link_configuracao") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(ConfiguracaoPage.class);
			}
		});
		
		add(new AjaxLink<String>("link_cadastro_perfil_acesso") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				setResponsePage(PerfilAcessoListarPage.class);			
			}
			
			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(alunoServico.searchFetchAlunoLogado(Util.getAlunoLogado()),PermissaoAcesso.PERMISSAO_PERFIL_ACESSO_PESQUISAR, PermissaoAcesso.OPERACAO_PESQUISAR);
			}
		});
		
		add(new AjaxLink<String>("sair_profile") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				((BasicAuthenticationSession)Session.get()).clear();
				((BasicAuthenticationSession)Session.get()).invalidate();
				setResponsePage(Login.class);				
			}
		});
		
		add(new AjaxLink<String>("sair_icon") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				((BasicAuthenticationSession)Session.get()).clear();
				((BasicAuthenticationSession)Session.get()).invalidate();
				setResponsePage(Login.class);				
			}
		});
		
		
		
//		add(criarFeedbackPanel());
		
	}
	
	protected JGrowlFeedbackPanel getFeedbackPanel(){
		return (JGrowlFeedbackPanel)get("jgrowlFeedback");
	}
	
	
//	private JGrowlFeedbackPanel criarFeedbackPanel() {
//		JGrowlFeedbackPanel feedback = new JGrowlFeedbackPanel("jgrowlFeedback");
//		Options errorOptions = new Options();
//		errorOptions.set("header", getString("erro"));
//		errorOptions.set("theme", "jgrowl-ERROR"); 
//		errorOptions.set("glue", "after");
////		errorOptions.set("sticky", new FunctionString("true"));
//		errorOptions.set("sticky",false);
//		feedback.setErrorMessageOptions(errorOptions);
//
//		Options infoOptions = new Options();
//		infoOptions.set("header", getString("info"));
//		infoOptions.set("theme", "jgrowl-INFO");
////		infoOptions.set("sticky", new FunctionString("true"));
//		errorOptions.set("sticky", false);
//		infoOptions.set("glue", "after");
//		feedback.setInfoMessageOptions(infoOptions);
//		
//		Options successOptions = new Options();
//		successOptions.set("header", getString("successo"));
//		successOptions.set("theme", "jgrowl-SUCCESS");
////		successOptions.set("sticky", new FunctionString("true"));
//		successOptions.set("sticky", false);
//		successOptions.set("glue", "after");
//		feedback.setSuccessMessageOptions(successOptions);
//		
//		return feedback;
//	}
}
