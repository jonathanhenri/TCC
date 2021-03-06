package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Configuracao;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IConfiguracaoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.IConfiguracaoServico;
import com.mycompany.util.Util;

public class ConfiguracaoServico implements IConfiguracaoServico {
	private IConfiguracaoDAO configuracaoDAO;
	private IAlunoServico alunoServico;
	
	public ConfiguracaoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Configuracao configuracao) {
		Retorno retorno = validaRegrasAntesIncluir(configuracao);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			aluno.setConfiguracao(configuracao);
			if(configuracaoDAO.persist(configuracao) && alunoServico.save(aluno).getSucesso()){
				mensagem  = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(Configuracao.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return configuracaoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return configuracaoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Configuracao configuracao) {
		Retorno retorno = validaRegrasAntesAlterar(configuracao);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(configuracaoDAO.save(configuracao)){
				mensagem = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Configuracao configuracao) {
		Retorno retorno = validaRegrasAntesRemover(configuracao);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			aluno.setConfiguracao(null);
			if(configuracaoDAO.remove(configuracao) && alunoServico.save(aluno).getSucesso()){
				mensagem = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(configuracao.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Configuracao> search(Search search) {
		searchComum(search);
		return configuracaoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Configuracao searchUnique(Search search) {
		searchComum(search);
		return configuracaoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(Configuracao configuracao){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		return retorno;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Configuracao configuracao) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(configuracao);
		
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR) && !Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		Search search = new Search(Configuracao.class);
		search.addFilterEqual("administracao.aluno.id", Util.getAlunoLogado().getId());
		if(count(search)>0){
			retorno.addMensagem(new Mensagem("Configuração já existente",Mensagem.ALERTA));
			retorno.setSucesso(false);
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(configuracao));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Configuracao configuracao) {
		Retorno retorno = Util.verificarIdNulo(configuracao);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR) && !Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(configuracao);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(configuracao));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Configuracao configuracao) {
		Retorno retorno = Util.verificarIdNulo(configuracao);
			
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_SINCRONIZAR) && !Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),configuracao, PermissaoAcesso.OPERACAO_CONFIGURACAO_COMPARTILHAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	
	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return configuracaoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	public void setAlunoServico(IAlunoServico alunoServico) {
		this.alunoServico = alunoServico;
	}
	
	public void setConfiguracaoDAO(IConfiguracaoDAO configuracaoDAO) {
		this.configuracaoDAO = configuracaoDAO;
	}
	
	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getAdministracao()!=null &&  Util.getAlunoLogado().getAdministracao().getAluno()!=null){
			filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
		}
				
		search.addFilter(filterOr);
	}

}
