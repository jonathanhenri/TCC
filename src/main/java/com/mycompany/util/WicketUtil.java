package com.mycompany.util;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mycompany.domain.Curso;

public class WicketUtil {

	public static ChoiceRenderer<Integer> getModalidadesCurso(final Component component){
		ChoiceRenderer<Integer> render = new ChoiceRenderer<Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdValue(Integer object, int index) {
				return object.toString();
			}

			@Override
			public Object getDisplayValue(Integer object) {
				if (object.equals(Curso.MODALIDADE_ANUAL)) {
					return component.getString("curso.modalidade.anual");
				} else if (object.equals(Curso.MODALIDADE_SEMESTRAL)) {
					return component.getString("curso.modalidade.semestral");
				}
				return null;
			}
		};
		return render;
	}
}
