package com.mycompany.visao.cadastro.curso;

import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.JGrowlFeedbackPanel;
import com.mycompany.visao.comum.EditForm;

public class CursoEditForm extends EditForm<Curso> {
	
	@SpringBean(name="cursoServico")
	private static ICursoServico cursoServico;
	
	private Curso curso;
	private FileUploadField  uploadFieldLogo;
	private String file = "";
	
	public CursoEditForm(String id, Curso curso,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super(id, curso,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.curso = curso;
	}
	
	public CursoEditForm(Curso curso,Panel editPanel,JGrowlFeedbackPanel feedbackPanel,WebMarkupContainer divAtualizar,ModalWindow modalIncluirEditar) {
		super("formCadastro", curso,editPanel,feedbackPanel,divAtualizar,modalIncluirEditar);
		this.curso = curso;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = cursoServico;
	}
	
	
	private FileUploadField criarCampoUploadLogo(){
		uploadFieldLogo = new FileUploadField("logo",new PropertyModel<List<FileUpload>>(this, "file"));
		uploadFieldLogo.setOutputMarkupId(true);
		return uploadFieldLogo;
	}
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
		textFieldNome.add(StringValidator.lengthBetween(1, 300));
		return textFieldNome;
	}
	
	
	private NumberTextField<Integer> criarCampoQuantidadePeriodo(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("quantidadePeriodo");
		duracao.setOutputMarkupId(true);
		return duracao;
	}
	
	private NumberTextField<Integer> criarCampoDuracao(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("duracao");
		duracao.setOutputMarkupId(true);
		return duracao;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoDuracao());
		add(criarCampoQuantidadePeriodo());
//		add(criarCampoUploadLogo());
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getFile() {
		return file;
	}
	
//	@Override
//	protected Boolean validarRegrasAntesSalvarEditar(AjaxRequestTarget target) {
//		if(uploadFieldLogo.getFileUpload()!=null){
//			FileUpload fileUpload = uploadFieldLogo.getFileUpload();
//			Arquivo arquivo = new Arquivo();
//			arquivo.setBytes(fileUpload.getBytes());
//			arquivo.setCurso(curso);
//			curso.setLogo(arquivo);
//			
//		}
//		return super.validarRegrasAntesSalvarEditar(target);
//	}
	
	private static final long serialVersionUID = 1L;

	
}
