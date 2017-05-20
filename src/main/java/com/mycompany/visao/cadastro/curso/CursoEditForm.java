package com.mycompany.visao.cadastro.curso;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.file.File;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.Arquivo;
import com.mycompany.domain.Curso;
import com.mycompany.services.interfaces.ICursoServico;
import com.mycompany.util.WicketUtil;
import com.mycompany.visao.comum.EditForm;

public class CursoEditForm extends EditForm {
	
	@SpringBean(name="cursoServico")
	private static ICursoServico cursoServico;
	
	private Curso curso;
	private FileUploadField  uploadFieldLogo;
	private String file = "";
	
	public CursoEditForm(String id, Curso curso,Panel editPanel) {
		super(id, curso,editPanel);
		this.curso = curso;
	}
	
	public CursoEditForm(Curso curso,Panel editPanel) {
		super("formCadastro", curso,editPanel);
		this.curso = curso;
	}
	
	@Override
	protected void setServicoComum() {
		serviceComum = cursoServico;
	}
	
	private DropDownChoice<Integer> criarCampoSituacao(){
		ListModel<Integer> tipos = new ListModel<Integer>(Curso.getListaModalidades());
		
		final DropDownChoice<Integer> tipoRadioChoice = new DropDownChoice<Integer>("modalidade", tipos, WicketUtil.getModalidadesCurso(this));
		tipoRadioChoice.setNullValid(true);
		tipoRadioChoice.setOutputMarkupId(true);
		
		return tipoRadioChoice;
	}
	
	private FileUploadField criarCampoUploadLogo(){
		uploadFieldLogo = new FileUploadField("logo",new PropertyModel<List<FileUpload>>(this, "file"));
		uploadFieldLogo.setOutputMarkupId(true);
		return uploadFieldLogo;
	}
	private TextField<String> criarCampoNome(){
		TextField<String> textFieldNome = new TextField<String>("nome");
		textFieldNome.setOutputMarkupId(true);
//		textFieldNome.setRequired(true);
		return textFieldNome;
	}
	
	
	private NumberTextField<Integer> criarCampoDuracao(){
		NumberTextField<Integer> duracao = new NumberTextField<Integer>("duracao");
		duracao.setOutputMarkupId(true);
//		duracao.setRequired(true);
		return duracao;
	}
	
	@Override
	protected void adicionarCampos() {
		add(criarCampoNome());
		add(criarCampoDuracao());
//		add(criarCampoUploadLogo());
		add(criarCampoSituacao());
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
