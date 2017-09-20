package com.mycompany.visao.comum;

import java.util.Date;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

public class DataTextField extends DateTextField {
	private static final long serialVersionUID = 1L;
	
	public DataTextField(String id, DateConverter converter) {
		super(id, converter);
	}
	
	public DataTextField(String id, IModel<Date> model, DateConverter converter){
		super(id, model, converter);
	}

	@Override
	protected String getInputType() {
		return "date";
	}

}
