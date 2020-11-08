package com.edimar.pedido.model.dto;

import java.io.Serializable;

import com.edimar.pedido.util.RecursosUtil;

public class ItemPedidoBO implements Serializable{

	private static final long serialVersionUID = 1L;
	private ProdutoBO produto;
	private Integer qtde;
	private Double valorTotalItensPedido;
	
	public ItemPedidoBO() {
		// TODO Auto-generated constructor stub
	}

	public ItemPedidoBO(ProdutoBO produto, Integer qtde) {
		super();
		this.produto = produto;
		this.qtde = qtde;
	}

	public ProdutoBO getProdutoBO() {
		return produto;
	}

	public void setProdutoBO(ProdutoBO produto) {
		this.produto = produto;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}

	public Double getValorTotalItensPedido() {
		if(produto.getPreco()!=null) {
			this.valorTotalItensPedido = produto.getPreco() * qtde;
		}
		return RecursosUtil.casasDecimais(this.valorTotalItensPedido);
	}

	public void setValorTotalItensPedido(Double valorTotalItensPedido) {
		this.valorTotalItensPedido = getValorTotalItensPedido();
	}
}
