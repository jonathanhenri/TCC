package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Materia;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IMateriaDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IMateriaServico;
import com.mycompany.util.Util;

public class MateriaServico implements IMateriaServico {
	private IMateriaDAO materiaDAO;
	
	public MateriaServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Materia materia) {
		Retorno retorno = validaRegrasAntesIncluir(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(materiaDAO.persist(materia)){
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(Materia.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return materiaDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Materia materia) {
		Retorno retorno = validaRegrasAntesAlterar(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(materiaDAO.save(materia)){
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Materia materia) {
		Retorno retorno = validaRegrasAntesRemover(materia);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(materiaDAO.remove(materia)){
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(materia.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Materia> search(Search search) {
		return materiaDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Materia searchUnique(Search search) {
		return materiaDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Materia materia) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(materia);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
			
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Materia materia) {
		Retorno retorno = Util.verificarIdNulo(materia);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(materia);
			if(retorno.getSucesso()){
				// Se precisar de regras especificas;
				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Materia materia) {
		Retorno retorno = Util.verificarIdNulo(materia);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setMateriaDAO(IMateriaDAO materiaDAO) {
		this.materiaDAO = materiaDAO;
	}


	@Override
	public List<Materia> getMaterias() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return materiaDAO.getMaterias();
		}
		
		return null;
	}
}
