package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.OrigemEvento;
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
				mensagem  = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return origemEventoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(OrigemEvento origemEvento) {
		Retorno retorno = validaRegrasAntesAlterar(origemEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(origemEventoDAO.save(origemEvento)){
				mensagem = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(OrigemEvento origemEvento) {
		Retorno retorno = validaRegrasAntesRemover(origemEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(origemEventoDAO.remove(origemEvento)){
				mensagem = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(origemEvento.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<OrigemEvento> search(Search search) {
		return origemEventoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public OrigemEvento searchUnique(Search search) {
		return origemEventoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(OrigemEvento origemEvento) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(origemEvento);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(OrigemEvento origemEvento) {
		Retorno retorno = Util.verificarIdNulo(origemEvento);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(origemEvento);
			if(retorno.getSucesso()){
				// Se precisar de regras especificas;
				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(OrigemEvento origemEvento) {
		Retorno retorno = Util.verificarIdNulo(origemEvento);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setOrigemEventoDAO(IOrigemEventoDAO origemEventoDAO) {
		this.origemEventoDAO = origemEventoDAO;
	}


	@Override
	public List<OrigemEvento> getOrigemEventos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return origemEventoDAO.getOrigemEventos();
		}
		
		return null;
	}

}
