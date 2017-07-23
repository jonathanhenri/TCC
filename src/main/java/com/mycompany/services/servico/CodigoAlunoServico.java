package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ICodigoAlunoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.util.Util;

public class CodigoAlunoServico implements ICodigoAlunoServico {
	private ICodigoAlunoDAO codigoAlunoDAO;
	private IAlunoServico alunoServico;
	
	public CodigoAlunoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(List<CodigoAluno> listaCodigosAluno) {
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);
		
		for (CodigoAluno codigoAluno:listaCodigosAluno) {
			if(codigoAluno.getAdministracao().getAluno()!=null && codigoAluno.getAdministracao().getAluno().getId() ==null){
				alunoServico.persist(codigoAluno.getAdministracao().getAluno());
			}
			if(!codigoAlunoDAO.persist(codigoAluno)){
				retorno.setSucesso(false);
				break;
			}
		}
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(CodigoAluno codigoAluno) {
		Retorno retorno = new Retorno();
		
		if(codigoAluno.getCursoAux()!=null && codigoAluno.getQuantidadeAlunosAux()>0){
			List<CodigoAluno> listaNovosCodigosAluno = gerarCodigosAluno(codigoAluno);
			
			retorno = persist(listaNovosCodigosAluno);
			if(retorno.getSucesso()){
				retorno.addMensagem(new Mensagem("Alunos criados com sucesso.", Mensagem.SUCESSO));
			}else{
				retorno.addMensagem(new Mensagem("Erro ao criar alunos automaticamente.", Mensagem.ERRO));
			}
		}else{
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Quantidade de alunos e curso são obrigatórios", Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(CodigoAluno codigoAluno) {
		Retorno retorno = validaRegrasAntesAlterar(codigoAluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.save(codigoAluno)){
				mensagem = new Mensagem(codigoAluno.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(codigoAluno.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(CodigoAluno codigoAluno) {
		Retorno retorno = validaRegrasAntesRemover(codigoAluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(codigoAlunoDAO.remove(codigoAluno)){
				mensagem = new Mensagem(codigoAluno.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(codigoAluno.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(CodigoAluno.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
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
			if(curso.getAdministracao().getAluno()!=null){
				retorno.setSucesso(false);
				retorno.addMensagem(new Mensagem("Aluno vinculado a este código", Mensagem.ERRO));
			}
		}
		
		return retorno;
	}
	
	public void setCodigoAlunoDAO(ICodigoAlunoDAO codigoAlunoDAO) {
		this.codigoAlunoDAO = codigoAlunoDAO;
	}
	
	public void setAlunoServico(IAlunoServico alunoServico) {
		this.alunoServico = alunoServico;
	}

	@Override
	public CodigoAluno verificarCodigoAtivo(String codigo,CodigoAluno codigoAluno) {
		return codigoAlunoDAO.verificarCodigoAtivo(codigo,codigoAluno);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<CodigoAluno> gerarCodigosAluno(CodigoAluno codigoAluno) {
		List<CodigoAluno> listaCodigoAluno = codigoAlunoDAO.gerarCodigosAluno(codigoAluno);
		return listaCodigoAluno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return codigoAlunoDAO.count(search);
	}
	
}
