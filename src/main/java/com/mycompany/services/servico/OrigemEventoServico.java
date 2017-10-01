package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.OrigemEvento;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IOrigemEventoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IOrigemEventoServico;
import com.mycompany.util.Util;

public class OrigemEventoServico implements IOrigemEventoServico {
	private IOrigemEventoDAO origemEventoDAO;
	
	public OrigemEventoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(OrigemEvento origemEvento) {
		Retorno retorno = validaRegrasAntesIncluir(origemEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(origemEventoDAO.persist(origemEvento)){
				mensagem  = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(OrigemEvento.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return origemEventoDAO.consultarPorIdFetch(abstractBean.getId());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(OrigemEvento origemEvento){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		Search searchLoginRepetido = new Search(OrigemEvento.class);
		searchLoginRepetido.addFilterEqual("nome", origemEvento.getNome());
		Filter filterOr = Filter.or(Filter.equal("administracao.curso.id", origemEvento.getAdministracao().getCurso().getId()),Filter.equal("administracao.administradorCampus", true));
		searchLoginRepetido.addFilter(filterOr);
		
		if(origemEvento.getId()!=null){
			searchLoginRepetido.addFilterNotEqual("id", origemEvento.getId());
		}
		
		int i = count(searchLoginRepetido);
		
		if(i>0){
			retorno.addMensagem(new Mensagem("Origem de evento","nome", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
			retorno.setSucesso(false);
		}
		
		return retorno;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return origemEventoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(OrigemEvento origemEvento) {
		Retorno retorno = validaRegrasAntesAlterar(origemEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(origemEventoDAO.save(origemEvento)){
				mensagem = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(OrigemEvento origemEvento) {
		Retorno retorno = validaRegrasAntesRemover(origemEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(origemEventoDAO.remove(origemEvento)){
				mensagem = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(origemEvento.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<OrigemEvento> search(Search search) {
		searchComum(search);
		return origemEventoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public OrigemEvento searchUnique(Search search) {
		searchComum(search);
		return origemEventoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(OrigemEvento origemEvento) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(origemEvento);
		

		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),origemEvento, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(origemEvento));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(OrigemEvento origemEvento) {
		Retorno retorno = Util.verificarIdNulo(origemEvento);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),origemEvento, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(origemEvento);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(origemEvento));
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(OrigemEvento origemEvento) {
		Retorno retorno = Util.verificarIdNulo(origemEvento);
			
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),origemEvento, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setOrigemEventoDAO(IOrigemEventoDAO origemEventoDAO) {
		this.origemEventoDAO = origemEventoDAO;
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return origemEventoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
			Aluno aluno = searchFetchAlunoLogado(Util.getAlunoLogado());
			
			if(aluno.getConfiguracao()!=null && aluno.getConfiguracao().getSincronizarOrigemEvento()){
				Filter filterCompartilhar = Filter.or(Filter.equal("administracao.aluno.configuracao.compartilharOrigemEvento", true));
				filterOr.add(filterCompartilhar);
			}
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
					
			search.addFilter(filterOr);
		}
	}
	
	@Override
	public List<OrigemEvento> getOrigemEventos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return origemEventoDAO.getOrigemEventos();
		}
		
		return null;
	}

}
