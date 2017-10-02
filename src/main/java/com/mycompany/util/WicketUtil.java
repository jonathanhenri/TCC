package com.mycompany.util;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import com.mycompany.domain.FiltroDinamicoAtributo;

public class WicketUtil {

	public static ChoiceRenderer<Integer> getOperadoresFiltroDinamico(final Component component){
		ChoiceRenderer<Integer> render = new ChoiceRenderer<Integer>() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getIdValue(Integer object, int index) {
				return object.toString();
			}

			@Override
			public Object getDisplayValue(Integer object) {
				if (object.equals(FiltroDinamicoAtributo.LIKE)) {
					return component.getString("filtroDinamicoAtributo.like");
				} else if (object.equals(FiltroDinamicoAtributo.EQUALS)) {
					return component.getString("filtroDinamicoAtributo.equals");
				}else if (object.equals(FiltroDinamicoAtributo.MAIOR_IQUAL)) {
					return component.getString("filtroDinamicoAtributo.maiorIqual");
				}else if (object.equals(FiltroDinamicoAtributo.MENOR_IQUAL)) {
					return component.getString("filtroDinamicoAtributo.menorIqual");
				}
				return null;
			}
		};
		return render;
	}
}
