package com.edimar.pedido.model.dto;

import java.io.Serializable;

import com.edimar.pedido.util.RecursosUtil;

public class ProdutoBO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String descricao;
	private String categoria;
	private Double preco;
	
	public ProdutoBO() {
		// TODO Auto-generated constructor stub
	}

	public ProdutoBO(Integer id, String descricao, String categoria, Double preco) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.categoria = categoria;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Double getPreco() {
		return RecursosUtil.casasDecimais(preco);
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
