package com.mycompany.visao.configuracao;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Configuracao;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.services.interfaces.IConfiguracaoServico;
import com.mycompany.util.Util;
import com.mycompany.visao.comum.Menu;

public class ConfiguracaoPage extends Menu {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="configuracaoServico")
	private IConfiguracaoServico configuracaoServico;
	
	private Configuracao configuracao;
	private WebMarkupContainer divCompartilhar;
	private WebMarkupContainer divSincronizar;
	
	public ConfiguracaoPage() {
		inicializarConfiguracao();
		
		criarDivEsconderCompartilhar();
		
		criarDivEsconderSincronizar();
		
		adicionarCampos();
	}

	private void criarDivEsconderSincronizar() {
		WebMarkupContainer esconderDivSincronizar = new WebMarkupContainer("esconderSincronizar"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(Util.getAlunoLogado(), configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR);
			}
		};
		esconderDivSincronizar.setOutputMarkupId(true);
		add(esconderDivSincronizar);
	}

	private void criarDivEsconderCompartilhar() {
		WebMarkupContainer esconderDivCompartilhar = new WebMarkupContainer("esconderCompartilhar"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(Util.getAlunoLogado(), configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR);
			}
		};
		esconderDivCompartilhar.setOutputMarkupId(true);
		add(esconderDivCompartilhar);
	}
	
	private void inicializarConfiguracao(){
		Search search = new Search(Configuracao.class);
		search.addFilterEqual("administracao.aluno.id", Util.getAlunoLogado().getId());
		configuracao = configuracaoServico.searchUnique(search);
		if(configuracao == null){
			configuracao = new Configuracao();
			configuracao.setCompartilharEvento(false);
			configuracao.setCompartilharTipoEvento(false);
			configuracao.setCompartilharOrigemEvento(false);
			configuracao.setCompartilharMateria(false);
			configuracao.setCompartilharAgenda(false);
			configuracao.setCompartilharPerfilAcesso(false);
			
			configuracao.setSincronizarEvento(true);
			configuracao.setSincronizarTipoEvento(true);
			configuracao.setSincronizarOrigemEvento(true);
			configuracao.setSincronizarMateria(true);
			configuracao.setSincronizarAgenda(true);
			configuracao.setSincronizarPerfilAcesso(true);
		}
	}
	private WebMarkupContainer criarDivSincronizar(){
		divSincronizar = new WebMarkupContainer("divSincronizar"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(Util.getAlunoLogado(), configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR);
			}
		};
		
		divSincronizar.add(criarCampoSincronizarEvento());
		divSincronizar.add(criarCampoSincronizarTipoEvento());
		divSincronizar.add(criarCampoSincronizarOrigemEvento());
		divSincronizar.add(criarCampoSincronizarMateria());
		divSincronizar.add(criarCampoSincronizarAgenda());
		divSincronizar.add(criarCampoSincronizarPerfilAcesso());
		
		return divSincronizar;
	}
	private WebMarkupContainer criarDivCompartilhar(){
		divCompartilhar = new WebMarkupContainer("divCompartilhar"){
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
				return Util.possuiPermissao(Util.getAlunoLogado(), configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR);
			}
		};
		
		divCompartilhar.add(criarCampoCompartilharEvento());
		divCompartilhar.add(criarCampoCompartilharTipoEvento());
		divCompartilhar.add(criarCampoCompartilharOrigemEvento());
		divCompartilhar.add(criarCampoCompartilharMateria());
		divCompartilhar.add(criarCampoCompartilharAgenda());
		divCompartilhar.add(criarCampoCompartilharPerfilAcesso());
		
		return divCompartilhar;
	}
	
	private void adicionarCampos(){
		Form<Configuracao> form = new Form<Configuracao>("form", new CompoundPropertyModel<Configuracao>(configuracao));
		form.setOutputMarkupId(true);
		
		form.add(criarDivCompartilhar());
		form.add(criarDivSincronizar());
		form.add(criarBotaoSalvar(form));
		add(form);
	}
	
	private RadioGroup<Boolean> criarCampoCompartilharPerfilAcesso() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharPerfilAcesso");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharPerfilAcessoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharPerfilAcessoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharPerfilAcessoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharPerfilAcessoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	private RadioGroup<Boolean> criarCampoCompartilharAgenda() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharAgenda");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharAgendaSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharAgendaSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharAgendaNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharAgendaNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	private RadioGroup<Boolean> criarCampoCompartilharMateria() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharMateria");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharMateriaSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharMateriaSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharMateriaNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharMateriaNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoCompartilharOrigemEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharOrigemEvento");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharOrigemEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharOrigemEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharOrigemEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharOrigemEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoCompartilharTipoEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharTipoEvento");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharTipoEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharTipoEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharTipoEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharTipoEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoCompartilharEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharEvento");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	//Sincronizar
	private RadioGroup<Boolean> criarCampoSincronizarPerfilAcesso() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarPerfilAcesso");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarPerfilAcessoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarPerfilAcessoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarPerfilAcessoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarPerfilAcessoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	private RadioGroup<Boolean> criarCampoSincronizarAgenda() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarAgenda");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarAgendaSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarAgendaSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarAgendaNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarAgendaNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	private RadioGroup<Boolean> criarCampoSincronizarMateria() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarMateria");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarMateriaSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarMateriaSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarMateriaNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarMateriaNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoSincronizarOrigemEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarOrigemEvento");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarOrigemEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarOrigemEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarOrigemEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarOrigemEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoSincronizarTipoEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarTipoEvento");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarTipoEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarTipoEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarTipoEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarTipoEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	private RadioGroup<Boolean> criarCampoSincronizarEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("sincronizarEvento");
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "sincronizarEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("sincronizarEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "sincronizarEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}
	
	
	
	
	
	private AjaxSubmitLink criarBotaoSalvar(Form<Configuracao> form){
		AjaxSubmitLink salvar = new AjaxSubmitLink("salvar",form){
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> formAux) {
				Retorno retorno = new Retorno(); 
				retorno.setSucesso(true);
				
				if(configuracao!=null && configuracao.getId() == null){
					retorno = configuracaoServico.persist(configuracao);
				}else{
					retorno = configuracaoServico.save(configuracao);
				}
				
				for(Mensagem mensagem:retorno.getListaMensagem()){
					 Util.notify(target, mensagem.toString(), mensagem.getTipo());
		        }
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				Util.notify(target, "Dados incorretos", Mensagem.ALERTA);
				super.onError(target, form);
			}
		};
		
		salvar.setOutputMarkupId(true);
		return salvar;
	}

}
