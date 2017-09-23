package com.mycompany.visao.comum;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.IModel;

public class PassowordTextFieldPersonalizado extends PasswordTextField {
	public PassowordTextFieldPersonalizado(String id,Boolean required) {
		super(id);
		setRequired(required);
	}
	
	public PassowordTextFieldPersonalizado(final String id, IModel<String> model,Boolean required){
		super(id, model);
		setRequired(required);
	}
	

	private static final long serialVersionUID = 1L;

}
