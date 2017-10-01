package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.ContadorAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IContadorAcessoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IContadorAcessoServico;
import com.mycompany.util.Util;

public class ContadorAcessoServico implements IContadorAcessoServico {
	private IContadorAcessoDAO contadorAcessoDAO;
	
	public ContadorAcessoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(ContadorAcesso contadorAcesso) {
		Retorno retorno = validaRegrasAntesIncluir(contadorAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(contadorAcessoDAO.persist(contadorAcesso)){
				mensagem  = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}
	
	@Override
	public Retorno validaRegrasComuns(ContadorAcesso contadorAcesso) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(ContadorAcesso.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return contadorAcessoDAO.consultarPorIdFetch(abstractBean.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(ContadorAcesso contadorAcesso) {
		Retorno retorno = validaRegrasAntesAlterar(contadorAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(contadorAcessoDAO.save(contadorAcesso)){
				mensagem = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		searchComum(search);
		return contadorAcessoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(ContadorAcesso contadorAcesso) {
		Retorno retorno = validaRegrasAntesRemover(contadorAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(contadorAcessoDAO.remove(contadorAcesso)){
				mensagem = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(contadorAcesso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<ContadorAcesso> search(Search search) {
		searchComum(search);
		return contadorAcessoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public ContadorAcesso searchUnique(Search search) {
		searchComum(search);
		return contadorAcessoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(ContadorAcesso contadorAcesso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(contadorAcesso);
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(contadorAcesso));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(ContadorAcesso contadorAcesso) {
		Retorno retorno = Util.verificarIdNulo(contadorAcesso);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(contadorAcesso);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(contadorAcesso));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(ContadorAcesso contadorAcesso) {
		Retorno retorno = Util.verificarIdNulo(contadorAcesso);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setContadorAcessoDAO(IContadorAcessoDAO contadorAcessoDAO) {
		this.contadorAcessoDAO = contadorAcessoDAO;
	}

	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return contadorAcessoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	@Override
	public List<ContadorAcesso> getAcessos(Aluno aluno) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return contadorAcessoDAO.getAcessos(aluno);
		}
		
		return null;
	}
	
	@Override
	public void searchComum(Search search){
		Filter filterOr = Filter.or();
		if(Util.getAlunoLogado().getAdministracao().getAdministradorCampus()!=null && !Util.getAlunoLogado().getAdministracao().getAdministradorCampus()){
			
			if(Util.getAlunoLogado().getAdministracao().getAluno()!=null){
				filterOr.add(Filter.equal("administracao.aluno.id", Util.getAlunoLogado().getAdministracao().getAluno().getId()));
			}
					
			search.addFilter(filterOr);
		}
	}
}
