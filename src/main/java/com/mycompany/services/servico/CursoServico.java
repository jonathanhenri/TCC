package com.mycompany.services.servico;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Aluno;
import com.mycompany.domain.Curso;
import com.mycompany.domain.Materia;
import com.mycompany.domain.PermissaoAcesso;
import com.mycompany.feedback.Mensagem;
import com.mycompany.feedback.Retorno;
import com.mycompany.persistence.interfaces.ICursoDAO;
import com.mycompany.reflexao.Reflexao;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.Util;

public class CursoServico implements ICursoServico {
	@SpringBean(name="cursoDAO")
	private ICursoDAO cursoDAO;

	public CursoServico() {
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno persist(Curso curso) {
		Retorno retorno = validaRegrasAntesIncluir(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			curso.setResalvarAdministracao(true);
			if(cursoDAO.persist(curso)){
				mensagem  = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_CADASTRADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO);
			}
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_CADASTRO_ERRO, Mensagem.ERRO));
		}
		
		return retorno;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public AbstractBean<?> searchFechId(AbstractBean<?> abstractBean) {
//		if(abstractBean!=null && abstractBean.getId()!=null){
//			Search search = new Search(Curso.class);
//			search.addFilterEqual("id", abstractBean.getId());
//			
//			for(String fetch: Reflexao.getListaAtributosEstrangeiros(abstractBean)){
//				search.addFetch(fetch);
//			}
//			
//			return  (AbstractBean<?>) searchUnique(search);
//		}
		return cursoDAO.consultarPorIdFetch(abstractBean.getId());
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno save(Curso curso) {
		Retorno retorno = validaRegrasAntesAlterar(curso);
		
		if(retorno.getSucesso()){
			Mensagem mensagem = new Mensagem();
			if(cursoDAO.save(curso)){
				mensagem = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_ALTERADO, Mensagem.SUCESSO);
			}else{
				mensagem  = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO);
			}
			
			retorno.addMensagem(mensagem);
		}else{
			retorno.addMensagem(new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_ALTERADO_ERRO, Mensagem.ERRO));
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
					mensagem = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO, Mensagem.SUCESSO);
				}else{
					mensagem = new Mensagem(curso.getNomeClass(), Mensagem.MOTIVO_EXCLUIDO_ERRO, Mensagem.ERRO);
				}
				
				retorno.addMensagem(mensagem);
			}
		}catch(Exception e){
			retorno.setSucesso(false);
			if(e instanceof ConstraintViolationException || e instanceof DataIntegrityViolationException || (e.getCause()!=null && e.getCause() instanceof ConstraintViolationException)){
				retorno.addMensagem(new Mensagem("Curso sendo utilizado", Mensagem.ERRO));
			}
			
			e.printStackTrace();
			
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public List<Curso> search(Search search) {
		searchComum(search);
		return cursoDAO.search(search);
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Curso searchUnique(Search search) {
		searchComum(search);
		return cursoDAO.searchUnique(search);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasComuns(Curso curso){
		Retorno retorno = new Retorno();
		retorno.setSucesso(true);

		Search search = new Search(Curso.class);
		search.addFilterEqual("nome", curso.getNome());
		
		if(curso.getId()!=null){
			search.addFilterNotEqual("id",curso.getId());
		}
		
		int i = count(search);
		
		if(i>0){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem("Curso","nome", Mensagem.MOTIVO_REPETIDO, Mensagem.ERRO));
		}
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesIncluir(Curso curso) {
		Retorno retorno = Reflexao.validarTodosCamposObrigatorios(curso);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),curso, PermissaoAcesso.OPERACAO_INCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_INCLUIR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			retorno.addRetorno(validaRegrasComuns(curso));
			return retorno;
		}else{
			return retorno;
		}
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesAlterar(Curso curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
		
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),curso, PermissaoAcesso.OPERACAO_EXCLUIR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_ALTERAR,Mensagem.ERRO));
		}
		
		if(retorno.getSucesso()){
			
			retorno = Reflexao.validarTodosCamposObrigatorios(curso);
			
			if(retorno.getSucesso()){
				retorno.addRetorno(validaRegrasComuns(curso));
			}
		}
		
		
		return retorno;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = java.lang.Exception.class, timeout = DEFAUL_TIMEOUT)
	public Retorno validaRegrasAntesRemover(Curso curso) {
		Retorno retorno = Util.verificarIdNulo(curso);
			
		if(!Util.possuiPermissao(searchFetchAlunoLogado(Util.getAlunoLogado()),curso, PermissaoAcesso.OPERACAO_ALTERAR)){
			retorno.setSucesso(false);
			retorno.addMensagem(new Mensagem(Mensagem.MOTIVO_SEM_PERMISSAO_EXCLUIR,Mensagem.ERRO));
		}
		
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
		searchComum(search);
		return cursoDAO.count(search);
	}
	
	@Override
	public Aluno searchFetchAlunoLogado(Aluno alunoLogado) {
		return cursoDAO.searchFetchAlunoLogado(alunoLogado);
	}

	@Override
	public List<Curso> getcursos() {
		if(Util.getAlunoLogado()!=null && Util.getAlunoLogado().getId()!=null){
			return cursoDAO.getcursos();
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
			if(Util.getAlunoLogado().getAdministracao().getCurso()!=null){
				filterOr.add(Filter.equal("administracao.curso.id", Util.getAlunoLogado().getAdministracao().getCurso().getId()));
			}
					
			search.addFilter(filterOr);
		}
	}
}
