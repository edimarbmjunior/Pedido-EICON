package com.edimar.pedido.model.dto;

import java.io.Serializable;
import java.util.Set;

import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.util.RecursosUtil;

public class PedidoBO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String dataCadastro;
	private Long numPedido;
	private Long codCliente;
	private Double valorTotalProdutos;
	private Set<ItemPedidoBO> itemPedidos;
	
	public PedidoBO() {
		// TODO Auto-generated constructor stub
	}

	public PedidoBO(Integer id, String dataCadastro, Long numPedido, Double valorTotalProdutos, Double valorFrete,
			Set<ItemPedidoBO> itemPedidos, Long codCliente) {
		super();
		this.id = id;
		this.dataCadastro = dataCadastro;
		this.numPedido = numPedido;
		this.valorTotalProdutos = valorTotalProdutos;
		this.itemPedidos = itemPedidos;
		this.codCliente = codCliente;
	}
	
	public PedidoBO(Pedido pedido) {
		super();
		this.id = pedido.getId();
		this.dataCadastro = RecursosUtil.converterDateToString(pedido.getDataCadastro());
		this.numPedido = pedido.getNumPedido();
		this.codCliente = pedido.getCodCliente();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(String dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(Long numPedido) {
		this.numPedido = numPedido;
	}

	public Double getValorTotalProdutos() {
		this.valorTotalProdutos = null;
		Integer qtde = 0;
		if(itemPedidos!=null && (!itemPedidos.isEmpty() && itemPedidos.size() > 0)) {
			this.valorTotalProdutos = 0d;
			itemPedidos.stream().forEach((itemPedido) -> {
				if(itemPedido.getProdutoBO().getPreco()!=null) {
					this.valorTotalProdutos = this.valorTotalProdutos + (itemPedido.getProdutoBO().getPreco() * itemPedido.getQtde());
				}
			});
			for(ItemPedidoBO item : itemPedidos) {
				qtde = qtde + item.getQtde();
			}
			if(qtde>5) {
				this.valorTotalProdutos=this.valorTotalProdutos*0.95;
			}
			if(itemPedidos.size()>9) {
				this.valorTotalProdutos=this.valorTotalProdutos*0.90;
			}
			return RecursosUtil.casasDecimais(valorTotalProdutos);
		}
		return this.valorTotalProdutos;
	}

	public void setValorTotalProdutos(Double valorTotalPedido) {
		this.valorTotalProdutos = getValorTotalProdutos();
	}

	public Set<ItemPedidoBO> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(Set<ItemPedidoBO> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	@Override
	public String toString() {
		return "PedidoBO [id=" + id + ", dataCadastro=" + dataCadastro + ", numPedido=" + numPedido + ", codCliente="
				+ codCliente + ", valorTotalProdutos=" + valorTotalProdutos + ", itemPedidos=" + itemPedidos + "]";
	}
}
