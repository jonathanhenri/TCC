package com.mycompany.visao.cadastro;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.mycompany.domain.Aluno;
import com.mycompany.persistence.interfaces.IAlunoDAO;

public class EditarUsuarioModal extends WebPage {
	private static final long serialVersionUID = 1L;
	
	Aluno user = new Aluno();
	Form<Aluno> form;
	@SpringBean(name="usuarioDAO")
	IAlunoDAO alunoDAO;
	
	public EditarUsuarioModal(final Aluno user,final ModalWindow modalEditar){
		
		this.user = user;
		
		form = new Form<Aluno>("formModalEditar", new CompoundPropertyModel<Aluno>(user));
		TextField<String> id = new TextField<String>("id");
		TextField<String> login = new TextField<String>("login");
		TextField<String> senha = new TextField<String> ("senha");
		TextField<String> nome = new TextField<String>("nome");
		
		form.add(new AjaxButton("submit") {
			private static final long serialVersionUID = 1L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				alunoDAO.persist(user);
				modalEditar.close(target);
			}
		});
		
		form.add(new AjaxLink<String>("cancelar") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				modalEditar.close(target);
			}
		});
		form.add(nome);
		form.add(login);
		form.add(senha);
		form.add(id);
		add(form);
	}
}
