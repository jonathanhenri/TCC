package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ICodigoAlunoDAO;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.util.Util;

public class CodigoAlunoServico implements ICodigoAlunoServico {
	private ICodigoAlunoDAO codigoAlunoDAO;

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(CodigoAluno curso) {
		Retorno retorno = validaRegrasAntesIncluir(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.persist(curso)){
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(CodigoAluno curso) {
		Retorno retorno = validaRegrasAntesAlterar(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.save(curso)){
				mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(CodigoAluno curso) {
		Retorno retorno = validaRegrasAntesRemover(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.remove(curso)){
				mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(curso.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<CodigoAluno> search(Search search) {
		return codigoAlunoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public CodigoAluno searchUnique(Search search) {
		return codigoAlunoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(CodigoAluno curso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(curso);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
			
			
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(CodigoAluno curso) {
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
	public Retorno validaRegrasAntesRemover(CodigoAluno curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
			
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		return retorno;
	}
	
	public void setCodigoAlunoDAO(ICodigoAlunoDAO codigoAlunoDAO) {
		this.codigoAlunoDAO = codigoAlunoDAO;
	}

	@Override
	public CodigoAluno verificarCodigoAtivo(String codigo) {
		return codigoAlunoDAO.verificarCodigoAtivo(codigo);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public CodigoAluno utilizarCodigoAluno(String codigo) {
		return codigoAlunoDAO.utilizarCodigoAluno(codigo);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<CodigoAluno> gerarCodigosAluno(int quantidade, Curso curso) {
		List<CodigoAluno> listaCodigoAluno = codigoAlunoDAO.gerarCodigosAluno(quantidade, curso);
		for(CodigoAluno codigoAluno:listaCodigoAluno){
			persist(codigoAluno);
		}
		return listaCodigoAluno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return codigoAlunoDAO.count(search);
	}
	
}
