package com.edimar.pedido.builder;

import com.edimar.pedido.model.Produto;
import com.edimar.pedido.model.dto.ProdutoBO;

public class ProdutoBuilder {

	private ProdutoBO produtoBO;
	private Produto produto;
	
	private ProdutoBuilder() {}
	
	public static ProdutoBuilder montaEntityProdutoBOTipo1() {
		ProdutoBuilder dto = new ProdutoBuilder();
		dto.produtoBO = new ProdutoBO();
		dto.produtoBO.setId(1);
		dto.produtoBO.setCategoria("Alimento");;
		dto.produtoBO.setDescricao("Arroz");
		dto.produtoBO.setPreco(29.50d);
		return dto;
	}
	
	public static ProdutoBuilder montaEntityProdutoTipo1() {
		ProdutoBuilder dto = new ProdutoBuilder();
		dto.produto = new Produto();
		dto.produto.setId(1);
		dto.produto.setCategoria("Alimento");;
		dto.produto.setDescricao("Arroz");
		dto.produto.setPreco(29.50d);
		return dto;
	}
	
	public static ProdutoBuilder montaEntityProdutoBOTipo2() {
		ProdutoBuilder dto = new ProdutoBuilder();
		dto.produtoBO = new ProdutoBO();
		dto.produtoBO.setId(2);
		dto.produtoBO.setCategoria("Alimento");;
		dto.produtoBO.setDescricao("Feijao");
		dto.produtoBO.setPreco(10.25d);
		return dto;
	}
	
	public ProdutoBO retornoEntityProdutoBO() {
		return produtoBO;
	}
	
	public Produto retornoEntityProduto() {
		return produto;
	}
}
