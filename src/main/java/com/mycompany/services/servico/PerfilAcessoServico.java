package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.PerfilAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IPerfilAcessoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IPerfilAcessoServico;
import com.mycompany.util.Util;

public class PerfilAcessoServico implements IPerfilAcessoServico {
	private IPerfilAcessoDAO perfilAcessoDAO;
	
	public PerfilAcessoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(PerfilAcesso perfilAcesso) {
		Retorno retorno = validaRegrasAntesIncluir(perfilAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(perfilAcessoDAO.persist(perfilAcesso)){
				mensagem  = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Override
	public Retorno validaRegrasComuns(PerfilAcesso perfilAcesso) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return perfilAcessoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(PerfilAcesso.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(PerfilAcesso perfilAcesso) {
		Retorno retorno = validaRegrasAntesAlterar(perfilAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(perfilAcessoDAO.save(perfilAcesso)){
				mensagem = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(PerfilAcesso perfilAcesso) {
		Retorno retorno = validaRegrasAntesRemover(perfilAcesso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(perfilAcessoDAO.remove(perfilAcesso)){
				mensagem = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(perfilAcesso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<PerfilAcesso> search(Search search) {
		return perfilAcessoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public PerfilAcesso searchUnique(Search search) {
		return perfilAcessoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(PerfilAcesso perfilAcesso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(perfilAcesso);
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(perfilAcesso));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(PerfilAcesso perfilAcesso) {
		Retorno retorno = Util.verificarIdNulo(perfilAcesso);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(perfilAcesso);
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(perfilAcesso));				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(PerfilAcesso perfilAcesso) {
		Retorno retorno = Util.verificarIdNulo(perfilAcesso);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setPerfilAcessoDAO(IPerfilAcessoDAO perfilAcessoDAO) {
		this.perfilAcessoDAO = perfilAcessoDAO;
	}

}
