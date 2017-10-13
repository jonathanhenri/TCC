package com.mycompany.visao.relatorio;

public class RelatorioJasper {
//	public JasperPrint processarOrcamento(Evento evento, IEventoServico eventoServico) throws Exception{
//		//
//		
//		//Jasper parameters
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("empresa", nomeEmpresa);
//		params.put("endereco", endereco);
//		params.put("cidade_cep_telefone", cidadeCepTelefone);
//		params.put("cnpj", cnpj);
//		params.put("ie", ieEmpresa);
//		params.put("cliente", cliente);
//		params.put("vendedor", vendedor);
//		params.put("vendedorPai", vendedorPai);
//		params.put("tabela_de_preco", tabelaDePreco);
//		params.put("valorFrete", valorFrete);
//		
//		params.put("data", data);
//		params.put("data_prevista", dataPrevista);
//		params.put("pedido", pedido);
//		params.put("situacao", situacao);
//		params.put("observacao", orcamento.getObservacaoGeral());
//		
//		params.put("numero_orcamento", String.valueOf(orcamento.getId()));
//		
//		List<Map<String, ?>> produtos = new ArrayList<Map<String, ?>>();
//		
//		List<OrcamentoProduto> orcamentoProdutos = Util.toList(orcamento.getOrcamentoProdutos());
//		
//		Collections.sort(orcamentoProdutos, new Comparator<OrcamentoProduto>() {
//			Collator collator = Collator.getInstance(Locale.US);
//			
//			public int compare(OrcamentoProduto o1, OrcamentoProduto o2) {
//				int c;
//			    c = collator.compare(o1.getProduto().getFornecedor().getRazaoSocial(), o2.getProduto().getFornecedor().getRazaoSocial());
//			    if (c == 0)
//			       c = collator.compare(o1.getProduto().getNome(), o2.getProduto().getNome());
//			    return c;
//			}
//		});
//		
//		params.put("total_produtos", Util.formataMoedaSemLocale(orcamento.getValorTotal()));
//		params.put("total_pedido", Util.formataMoedaSemLocale(orcamento.getValorTotalFinal()));
//		params.put("usuario", Util.getUsuarioLogado().getNome());
//		
//		params.put("logo_path", getLogo());
//		
//		Locale locale = new Locale("pt", "BR");
//		params.put("data_impressao", Util.formataDataComHora(new Date(), locale));
//		
//		if(orcamentoProdutos != null && !orcamentoProdutos.isEmpty()){
//			for (OrcamentoProduto orcamentoProduto : orcamentoProdutos) {
//				if(orcamentoProduto.getCancelado() != null && orcamentoProduto.getCancelado()){
//					continue;
//				}
//				HashMap<String, String> mapItens = new HashMap<>();
//				mapItens.put("codigo", orcamentoProduto.getProduto().getCodigoPrincipal() != null ? orcamentoProduto.getProduto().getCodigoPrincipal() : "");
//				mapItens.put("produto", orcamentoProduto.getProduto().getNome() != null ? orcamentoProduto.getProduto().getNome() : "");
//				mapItens.put("observacao", orcamentoProduto.getObservacao() != null ? orcamentoProduto.getObservacao() : "");
//				mapItens.put("qtde", orcamentoProduto.getQuantidade() != null ? Util.formataMoedaSemLocale(orcamentoProduto.getQuantidade()) : "0,00");
//				mapItens.put("unidade_compra", orcamentoProduto.getProduto().getUnidade().getAbreviacao() != null ? orcamentoProduto.getProduto().getUnidade().getAbreviacao() : "");
//				mapItens.put("valor_unitario", orcamentoProduto.getValorUnitario() != null ? Util.formataMoedaSemLocale(orcamentoProduto.getValorUnitario()) : "0,00");
//				mapItens.put("valor_total", orcamentoProduto.getValorTotal() != null ? Util.formataMoedaSemLocale(orcamentoProduto.getValorTotal()) : "0,00");
//				produtos.add(mapItens);
//			}
//		}
//		
//		//filds do report pai, vazios nesse caso.
//		List<Map<String, ?>> relatorioPaiFields = new ArrayList<Map<String, ?>>();
//		relatorioPaiFields.add(new HashMap<String, Object>());
//		JRDataSource jrDataPai = new JRBeanCollectionDataSource(relatorioPaiFields);
//		
//		JRDataSource jrData = new JRBeanCollectionDataSource(produtos);
//		
//		//SubReportParameters
//		params.put("itens_pedido_path", Util.fileSeparator("orcamento/itens_orcamento.jasper"));
//		params.put("itens_pedido_dados",jrData);
//		
//		
//		String baseReportPath = "";
//		InputStream is = null;
//		
//		JasperPrint jasperPrint = null;
//		
//		baseReportPath = Util.fileSeparator("orcamento/orcamento.jasper");
//		is = BasePage.class.getClassLoader().getResource(baseReportPath).openStream();
//		
//		try {
//			jasperPrint = JasperFillManager.fillReport(is, params, jrDataPai);
//		} catch (JRException e) {
//			e.printStackTrace();
//		} catch (ClassCastException x) {
//			x.printStackTrace();
//		}
//		return jasperPrint;
//	}
//	
}