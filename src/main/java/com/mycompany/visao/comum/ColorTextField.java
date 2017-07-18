package com.mycompany.visao.comum;

import org.apache.wicket.markup.html.form.TextField;

public class ColorTextField extends TextField<String> {
	private static final long serialVersionUID = 1L;

	public ColorTextField(String id) {
		super(id);
	}

	@Override
	protected String getInputType() {
		return "color";
	}

}
