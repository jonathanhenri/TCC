package com.mycompany.services.servico;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Arquivo;
import com.mycompany.domain.CodigoAluno;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.IAlunoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.IAlunoServico;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.util.Util;

public class AlunoServico implements IAlunoServico {
	private IAlunoDAO alunoDAO;
	private ICodigoAlunoServico codigoAlunoServico;
	
	public AlunoServico() {
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Aluno aluno) {
		Retorno retorno = validaRegrasAntesIncluir(aluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(alunoDAO.persist(aluno)){
				mensagem  = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Arquivo arquivo) {
		Retorno retorno = new Retorno(); 
		alunoDAO.persist(arquivo);
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Arquivo arquivo) {
		Retorno retorno = new Retorno(); 
		alunoDAO.save(arquivo);
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
		if(abstractBean!=null && abstractBean.getId()!=null){
			Search search = new Search(Aluno.class);
			search.addFilterEqual("id", abstractBean.getId());
			
			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
				search.addFetch(fetch);
			}
			
			return  (AbstractBean<?>) searchUnique(search);
		}
		return null;
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Arquivo arquivo) {
		Retorno retorno = new Retorno(); 
		alunoDAO.remove(arquivo);
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	private void desativarCodigoAluno(Aluno aluno){
		Search search = new Search(CodigoAluno.class);
		search.addFilterEqual("administracao.aluno.id", aluno.getId());
		
		CodigoAluno codigoAluno = codigoAlunoServico.searchUnique(search);
		
		if(codigoAluno!=null){
			codigoAluno.setAtivo(false);
			codigoAlunoServico.save(codigoAluno);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Aluno aluno) {
		Retorno retorno = validaRegrasAntesAlterar(aluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			desativarCodigoAluno(aluno);
			if(alunoDAO.save(aluno)){
				mensagem = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno remove(Aluno aluno) {
		Retorno retorno = validaRegrasAntesRemover(aluno);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			
			Search search = new Search(CodigoAluno.class);
			search.addFilterEqual("administracao.aluno.id", aluno.getId());
			
			CodigoAluno codigoAluno = codigoAlunoServico.searchUnique(search);
			
			if(codigoAluno!=null){
				codigoAlunoServico.remove(codigoAluno);
			}
			
			if(alunoDAO.remove(aluno)){
				mensagem = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
			}else{
				mensagem = new Mensagem(aluno.getClass().getSimpleName(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public int count(Search search) {
		return alunoDAO.count(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Aluno> search(Search search) {
		return alunoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Aluno searchUnique(Search search) {
		return alunoDAO.searchUnique(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Aluno aluno) {
		
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(aluno);
		
		aluno.setLogin(aluno.getLogin().trim());
		
		Search searchLoginRepetido = new Search(Aluno.class);
		searchLoginRepetido.addFilterEqual("login", aluno.getLogin());
		Filter filterOr = Filter.or(Filter.equal("administracao.curso.id", aluno.getAdministracao().getCurso().getId()),Filter.equal("administracao.administradorCampus", true));
		searchLoginRepetido.addFilter(filterOr);
		
		int i = count(searchLoginRepetido);
		
		if(i>0){
			retorno.addMensagem(new Mensagem("login", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
			retorno.setSucesso(false);
		}
		
		if(retorno.getSucesso()){
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Aluno aluno) {
		Retorno retorno = Util.verificarIdNulo(aluno);
		if(retorno.getSucesso()){
			retorno = Reflexao.validarTodosCamposObrigatorios(aluno);
			if(retorno.getSucesso()){
				aluno.setLogin(aluno.getLogin().trim());
				
				Search searchLoginRepetido = new Search(Aluno.class);
				searchLoginRepetido.addFilterEqual("login", aluno.getLogin());
				Filter filterOr = Filter.or(Filter.equal("administracao.curso.id", aluno.getAdministracao().getCurso().getId()),Filter.equal("administracao.administradorCampus", true));
				searchLoginRepetido.addFilter(filterOr);
				searchLoginRepetido.addFilterNotEqual("id", aluno.getId());
				
				int i = count(searchLoginRepetido);
				
				if(i>0){
					retorno.addMensagem(new Mensagem("login", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
					retorno.setSucesso(false);
				}
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Aluno aluno) {
		Retorno retorno = Util.verificarIdNulo(aluno);
		
		if(retorno.getSucesso()){
			// Se precisar de regras especificas;
		}
		
		
		return retorno;
	}
	

	public void setCodigoAlunoServico(ICodigoAlunoServico codigoAlunoServico) {
		this.codigoAlunoServico = codigoAlunoServico;
	}
	
	public void setalunoDAO(IAlunoDAO alunoDAO) {
		this.alunoDAO = alunoDAO;
	}
}
