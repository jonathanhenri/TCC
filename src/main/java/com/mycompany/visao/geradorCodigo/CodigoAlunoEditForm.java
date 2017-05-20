package com.mycompany.visao.geradorCodigo;

import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.CodigoAluno;
import com.mycompany.services.interfaces.ICodigoAlunoServico;
import com.mycompany.visao.comum.EditForm;

public class CodigoAlunoEditForm extends EditForm {
	
	@SpringBean(name="codigoAlunoServico")
	private static ICodigoAlunoServico codigoAlunoServico;
	
	private CodigoAluno codigoAluno;
	private Integer quantidadeCodigos = 0;
	public CodigoAlunoEditForm(String id, CodigoAluno codigoAluno,Panel editPanel) {
		super(id, codigoAluno,editPanel);
		this.codigoAluno = codigoAluno;
	}
	
	public CodigoAlunoEditForm(CodigoAluno codigoAluno,Panel editPanel) {
		super("formCadastro", codigoAluno,editPanel);
		this.codigoAluno = codigoAluno;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = codigoAlunoServico;
	}

	
	private NumberTextField<Integer> criarCampoQuantidade(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("quantidade");
		duracao.setOutputMarkupId(true);
		duracao.setRequired(true);
		return duracao;
	}
	
	@Override
	protected void adicionarCampos() {
		criarCampoQuantidade();
	}
	
	
	
	private static final long serialVersionUID = 1L;

	
	
	public void setQuantidadeCodigos(Integer quantidadeCodigos) {
		this.quantidadeCodigos = quantidadeCodigos;
	}
	
	public Integer getQuantidadeCodigos() {
		return quantidadeCodigos;
	}
}
