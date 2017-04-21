package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.Util;

public class CursoServico implements ICursoServico {
	private ICursoDAO cursoDAO;

	public CursoServico() {
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Curso curso) {
		Retorno retorno = validaRegrasAntesIncluir(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(cursoDAO.persist(curso)){
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Curso curso) {
		Retorno retorno = validaRegrasAntesAlterar(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(cursoDAO.save(curso)){
				mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Curso curso) {
		Retorno retorno = new Retorno();
		try{
			 retorno = validaRegrasAntesRemover(curso);
			
			if(retorno.getSucesso()){
				Mensagem mensagem = new Mensagem();
				if(cursoDAO.remove(curso)){
					mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
				}else{
					mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
				}
				
				retorno.addMensagem(mensagem);
			}
		}catch(Exception e){
			e.printStackTrace();
			retorno.setSucesso(false);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Curso> search(Search search) {
		return cursoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Curso searchUnique(Search search) {
		return cursoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Curso curso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(curso);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
			
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Curso curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
		
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(curso);
			if(retorno.getSucesso()){
				// Se precisar de regras especificas;
				
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Curso curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	

	public void setCursoDAO(ICursoDAO cursoDAO) {
		this.cursoDAO = cursoDAO;
	}

	@Override
	public List<Materia> materiasPorCurso(Curso curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
		
		if(retorno.getSucesso()){
			return cursoDAO.materiasPorCurso(curso);
		}

		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return cursoDAO.count(search);
	}
	
	@Override
	public List<Curso> getcursos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return cursoDAO.getcursos();
		}
		
		return null;
	}
}
