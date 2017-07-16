package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
				mensagem  = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(ContadorAcesso.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(ContadorAcesso contadorAcesso) {
		Retorno retorno = validaRegrasAntesAlterar(contadorAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(contadorAcessoDAO.save(contadorAcesso)){
				mensagem = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return contadorAcessoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(ContadorAcesso contadorAcesso) {
		Retorno retorno = validaRegrasAntesRemover(contadorAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(contadorAcessoDAO.remove(contadorAcesso)){
				mensagem = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(contadorAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<ContadorAcesso> search(Search search) {
		return contadorAcessoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public ContadorAcesso searchUnique(Search search) {
		return contadorAcessoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(ContadorAcesso contadorAcesso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(contadorAcesso);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
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
				// Se precisar de regras especificas;
				
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
	public List<ContadorAcesso> getAcessos(Aluno aluno) {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return contadorAcessoDAO.getAcessos(aluno);
		}
		
		return null;
	}
}
