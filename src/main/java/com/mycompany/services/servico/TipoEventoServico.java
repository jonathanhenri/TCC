package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.TipoEvento;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ITipoEventoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.ITipoEventoServico;
import com.mycompany.util.Util;

public class TipoEventoServico implements ITipoEventoServico {
	private ITipoEventoDAO tipoEventoDAO;
	
	public TipoEventoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(TipoEvento tipoEvento) {
		Retorno retorno = validaRegrasAntesIncluir(tipoEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(tipoEventoDAO.persist(tipoEvento)){
				mensagem  = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return tipoEventoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(TipoEvento tipoEvento) {
		Retorno retorno = validaRegrasAntesAlterar(tipoEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(tipoEventoDAO.save(tipoEvento)){
				mensagem = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(TipoEvento tipoEvento) {
		Retorno retorno = validaRegrasAntesRemover(tipoEvento);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(tipoEventoDAO.remove(tipoEvento)){
				mensagem = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(tipoEvento.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<TipoEvento> search(Search search) {
		return tipoEventoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public TipoEvento searchUnique(Search search) {
		return tipoEventoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(TipoEvento tipoEvento) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(tipoEvento);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(TipoEvento tipoEvento) {
		Retorno retorno = Util.verificarIdNulo(tipoEvento);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(tipoEvento);
			if(retorno.getSucesso()){
				// Se precisar de regras especificas;
				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(TipoEvento tipoEvento) {
		Retorno retorno = Util.verificarIdNulo(tipoEvento);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setTipoEventoDAO(ITipoEventoDAO tipoEventoDAO) {
		this.tipoEventoDAO = tipoEventoDAO;
	}


	@Override
	public List<TipoEvento> getTipoEventos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return tipoEventoDAO.getTipoEventos();
		}
		
		return null;
	}
}
