package com.mycompany.visao.configuracao;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.googlecode.genericdao.search.Search;
import com.mycompany.domain.Configuracao;
import com.mycompany.services.interfaces.IConfiguracaoServico;
import com.mycompany.visao.comum.Menu;

public class ConfiguracaoPage extends Menu {
	private static final long serialVersionUID = 1L;
	
	@SpringBean(name="configuracaoServico")
	private IConfiguracaoServico configuracaoServico;
	
	private Configuracao configuracao;
	
	public ConfiguracaoPage() {
		configuracao = configuracaoServico.searchUnique(new Search());
		if(configuracao == null){
			configuracao = new Configuracao();
		}
		configuracao.setCompartilharEvento(false);
		adicionarCampos();
	}
	
	private void adicionarCampos(){
		Form<Configuracao> form = new Form<Configuracao>("form", new CompoundPropertyModel<Configuracao>(configuracao));
		form.setOutputMarkupId(true);
		form.add(criarCampoCompartilharEvento());
		add(form);
	}
	
	private RadioGroup<Boolean> criarCampoCompartilharEvento() {
		RadioGroup<Boolean> radioGroupAtivo = new RadioGroup<Boolean>("compartilharEvento");
		radioGroupAtivo.add(new Radio<Boolean>("compartilharEventoSim", new Model<Boolean>(true)).add(new AttributeModifier("id", "compartilharEventoSim")));
		radioGroupAtivo.add(new Radio<Boolean>("compartilharEventoNao", new Model<Boolean>(false)).add(new AttributeModifier("id", "compartilharEventoNao")));
		radioGroupAtivo.setOutputMarkupId(true);
		return radioGroupAtivo;
	}

}
