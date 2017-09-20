package com.mycompany.visao.comum;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

import wicket.contrib.input.events.EventType;
import wicket.contrib.input.events.InputBehavior;
import wicket.contrib.input.events.key.KeyType;

import com.mycompany.domain.AbstractBean;
import com.mycompany.domain.FiltroDinamicoAtributo;
import com.mycompany.util.Util;

public abstract class ListagemFiltrosDinamicosPanel extends Panel {
	private static final long serialVersionUID = 1L;
    private ListView<FiltroDinamicoAtributo> dataViewFiltroDinamicoAtributo;
    private List<FiltroDinamicoAtributo> listaFiltrosDinamicosSelecionados;
    private ModalWindow modalFiltro;
    private ModalWindow modalEdit;
    private WebMarkupContainer divListViewFiltrosDinamicos;
    private AbstractBean<?> abstractBean;
    private List<FiltroDinamicoAtributo> listaFiltrosDisponiveisEntity;
    @SuppressWarnings("all")
    private boolean focusGained;
    
    public ListagemFiltrosDinamicosPanel(String id, ModalWindow modalEdit,AbstractBean<?> abstractBean) {
        super(id);
        this.modalEdit = modalEdit;
        this.abstractBean = abstractBean;
        listaFiltrosDinamicosSelecionados = new ArrayList<FiltroDinamicoAtributo>();
        listaFiltrosDisponiveisEntity = Util.getNameFieldEntity(abstractBean,true);
        focusGained = true;
        adicionarCampos();
    }
    void adicionarCampos(){
        add(criarModalFiltro());
        add(criarNewHeader());
        Form<FiltroDinamicoAtributo> form = new Form<FiltroDinamicoAtributo>("formFiltrosDinamicos");
        divListViewFiltrosDinamicos = new WebMarkupContainer("divListViewFiltrosDinamicos");
        divListViewFiltrosDinamicos.setOutputMarkupId(true);
        form.add(divListViewFiltrosDinamicos);
        divListViewFiltrosDinamicos.add(criarDataViewFiltroDinamico());
        form.add(criarBotaoPesquisar());
        form.add(criarBotaoVoltar());
        form.setOutputMarkupId(true);
        form.add(criarBotaoIncluirNovoFiltro());
        add(form);
    }
    private WebMarkupContainer criarDataViewFiltroDinamico() {
        LoadableDetachableModel<List<FiltroDinamicoAtributo>> listaModelAtributos = new LoadableDetachableModel<List<FiltroDinamicoAtributo>>() {
            private static final long serialVersionUID = 1L;
            @Override
            protected List<FiltroDinamicoAtributo> load() {
                List<FiltroDinamicoAtributo> list = new ArrayList<FiltroDinamicoAtributo>();
                if(listaFiltrosDinamicosSelecionados!=null && listaFiltrosDinamicosSelecionados.size()>0){
                    list.addAll(listaFiltrosDinamicosSelecionados);
                }
                return list;
            }
        };
        dataViewFiltroDinamicoAtributo = new ListView<FiltroDinamicoAtributo>("listFiltrosDinamicos",listaModelAtributos) {
            private static final long serialVersionUID = 1L;
            @Override
            protected void populateItem(ListItem<FiltroDinamicoAtributo> item) {
                FiltroDinamicoAtributo atributo = item.getModelObject();
                Label nomeCampo = null;
                if(atributo.getNomeCampoPersonalidado()!=null){
                    nomeCampo = new Label("nomeCampo", atributo.getNomeCampoPersonalidado());
                }else if(atributo.getNomeCampo()!=null){
                    nomeCampo = new Label("nomeCampo", atributo.getNomeCampo());
                }
                Label valorCampo = new Label("valorCampo", Util.getStringValueField(atributo));
                Label operador = new Label("operador",atributo.getOperadorNome());
                item.add(operador);
                item.add(nomeCampo);
                item.add(valorCampo);
//                item.add(criarLinkEditarFiltroDinamicoPage(atributo));
                item.add(criarLinkFiltroDinamicoPage(atributo));
            }
        };
        dataViewFiltroDinamicoAtributo.setOutputMarkupId(true);
        return dataViewFiltroDinamicoAtributo;
    }
    
    private AjaxLink<String> criarLinkFiltroDinamicoPage(final FiltroDinamicoAtributo filtroDinamicoAtributo) {
        AjaxLink<String> excluirTransacao = new AjaxLink<String>("linkExcluirFiltroDinamicoPage") {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(AjaxRequestTarget target) {
                listaFiltrosDinamicosSelecionados.remove(filtroDinamicoAtributo);
                target.add(divListViewFiltrosDinamicos);
            }
            @Override
            protected void updateAjaxAttributes(AjaxRequestAttributes attributes ) {
                super.updateAjaxAttributes( attributes );
                AjaxCallListener ajaxCallListener = new AjaxCallListener();
                ajaxCallListener.onPrecondition( "return confirm('" + getLocalizer().getString("confirmacao", ListagemFiltrosDinamicosPanel.this) + "');" );
                attributes.getAjaxCallListeners().add( ajaxCallListener );
            }
        };
        return excluirTransacao;
    }
    
    private AjaxLink<String> criarLinkEditarFiltroDinamicoPage(final FiltroDinamicoAtributo filtroDinamicoAtributo){
        AjaxLink<String> linkAlterar = new AjaxLink<String>("linkEditarFiltroDinamico") {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(AjaxRequestTarget target) {
                IncluirFiltroDinamicoAtributoPanel incluirFiltrosDinamicosPage = new IncluirFiltroDinamicoAtributoPanel(modalFiltro.getContentId(),modalEdit,abstractBean,listaFiltrosDisponiveisEntity,filtroDinamicoAtributo.clonar(true)) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void executarAoSalvar(AjaxRequestTarget target,FiltroDinamicoAtributo filtroDinamicoAtributoAux) {
                        listaFiltrosDinamicosSelecionados.remove(filtroDinamicoAtributo);
                        listaFiltrosDinamicosSelecionados.add(filtroDinamicoAtributo.clonar(true));
                        target.add(divListViewFiltrosDinamicos);
                        focusGained = true;
                        modalFiltro.close(target);
                    }
                };
                focusGained = false;
                modalFiltro.setContent(incluirFiltrosDinamicosPage);
                modalFiltro.show(target);
            }
        };
        linkAlterar.setOutputMarkupId(true);
        return linkAlterar;
    }
    private AjaxButton criarBotaoIncluirNovoFiltro(){
        AjaxButton ajaxButton = new AjaxButton("botaoNovoFiltro") {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                IncluirFiltroDinamicoAtributoPanel incluirFiltrosDinamicosPage = new IncluirFiltroDinamicoAtributoPanel(modalFiltro.getContentId(),modalFiltro,abstractBean,listaFiltrosDisponiveisEntity,new FiltroDinamicoAtributo()) {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public void executarAoSalvar(AjaxRequestTarget target,FiltroDinamicoAtributo filtroDinamicoAtributo) {
                        listaFiltrosDinamicosSelecionados.add(filtroDinamicoAtributo.clonar(false));
                        target.add(divListViewFiltrosDinamicos);
                        focusGained = true;
                        modalFiltro.close(target);
                    }
                };
                focusGained = false;
                modalFiltro.setContent(incluirFiltrosDinamicosPage);
                modalFiltro.show(target);
            }
        };
        return ajaxButton;
    }
    private Label criarCampoNomeClasseFiltro(){
        Label label = new Label("nomeClasse", abstractBean.getClass().getSimpleName());
        label.setOutputMarkupId(true);
        return label;
    }
    private WebMarkupContainer criarNewHeader(){
        WebMarkupContainer newHeader = new WebMarkupContainer("newHeader") {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onBeforeRender() {
                super.onBeforeRender();
            }
        };
        newHeader.add(criarCampoNomeClasseFiltro());
        newHeader.setOutputMarkupId(true);
        return newHeader;
    }
    private ModalWindow criarModalFiltro() {
        modalFiltro = new ModalWindow("modalFiltro");
        modalFiltro.setInitialWidth(700);
        modalFiltro.setInitialHeight(400);
        modalFiltro.setOutputMarkupId(true);
        return modalFiltro;
    }
    private AjaxButton criarBotaoPesquisar(){
        AjaxButton botaoVoltar= new AjaxButton("pesquisar") {
            private static final long serialVersionUID = 1L;
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                getListaFiltrosDinamicosSelecionados(target, listaFiltrosDinamicosSelecionados);
                modalEdit.close(target);
            }
        };
        botaoVoltar.setOutputMarkupId(true);
        return botaoVoltar;
    }
    private AjaxLink<String> criarBotaoVoltar(){
        AjaxLink<String> botaoVoltar= new AjaxLink<String>("voltar") {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick(AjaxRequestTarget target) {
                if(focusGained)
                    modalEdit.close(target);
            }
        };
        botaoVoltar.add(new InputBehavior(new KeyType[]{KeyType.Escape}, EventType.click));
        botaoVoltar.setOutputMarkupId(true);
        return botaoVoltar;
    }
    public abstract void getListaFiltrosDinamicosSelecionados(AjaxRequestTarget target, List<FiltroDinamicoAtributo> listaFiltroDinamico);
	
}
