package com.mycompany.visao.comum;

import java.util.List;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.util.ListModel;

@SuppressWarnings("all")
public class IndicatorDropDownChoice<T> extends DropDownChoice<T> implements IAjaxIndicatorAware {
	private static final long serialVersionUID = 1L;

	public IndicatorDropDownChoice(String id, IModel<T> model, LoadableDetachableModel<List<T>> loadableModel) {
		super(id, model, loadableModel);
	}
	
	public IndicatorDropDownChoice(String id, IModel<T> model, ListModel<T> listModel) {
		super(id, model, listModel);
	}
	
	public IndicatorDropDownChoice(String id, List<T> list, ChoiceRenderer<T> choiceRenderer) {
		super(id, list, choiceRenderer);
	}
	
	public IndicatorDropDownChoice(String id, List<T> list) {
		super(id, list);
	}
	
	public IndicatorDropDownChoice(String id, IModel model, List<T> list, ChoiceRenderer<T> choiceRenderer) {
		super(id, model, list, choiceRenderer);
	}
	
	public IndicatorDropDownChoice(String id, IModel model, ListModel<T> list, ChoiceRenderer<T> choiceRenderer) {
		super(id, model, list, choiceRenderer);
	}
	
	public IndicatorDropDownChoice(String id, IModel model, IModel<List<T>> list, ChoiceRenderer<T> choiceRenderer) {
		super(id, model, list, choiceRenderer);
	}
	
	public IndicatorDropDownChoice(String id, IModel list, ChoiceRenderer<T> choiceRenderer) {
		super(id, list, choiceRenderer);
	}
	
	public IndicatorDropDownChoice(String id, IModel<T> model, LoadableDetachableModel<List<T>> loadableModel, ChoiceRenderer<T> choiceRenderer) {
		super(id, model, loadableModel, choiceRenderer);
	}

	public String getAjaxIndicatorMarkupId() {
		return "indicator";
	}
}