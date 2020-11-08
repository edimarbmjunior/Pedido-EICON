package com.edimar.pedido.model.convert;

import com.edimar.pedido.model.ItemPedido;
import com.edimar.pedido.model.ItemPedidoPK;
import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.dto.ItemPedidoBO;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.util.RecursosUtil;

public class ItemPedidoConvert {

	public static ItemPedido converterToItemPedidoFromItemPedidoBOFixoPedido(ItemPedidoBO itemPedidoDTO, PedidoBO pedidoBO) {
		ItemPedido itemPedido = new ItemPedido();
		ItemPedidoPK itemPedidoPK = new ItemPedidoPK();
		Pedido pedido = new Pedido();
		pedido.setId(pedidoBO.getId());
		pedido.setCodCliente(pedidoBO.getCodCliente());
		pedido.setDataCadastro(pedidoBO.getDataCadastro() == null ? null : RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro()));
		pedido.setNumPedido(pedidoBO.getNumPedido() ==null ? null : pedidoBO.getNumPedido());
		itemPedidoPK.setPedido(pedido);
		itemPedidoPK.setProduto(ProdutoConvert.converterToProdutoFromProdutoBO(itemPedidoDTO.getProdutoBO()));
		itemPedido.setId(itemPedidoPK);
		itemPedido.setQtde(itemPedidoDTO.getQtde());
		return itemPedido;
	}
	
	public static ItemPedido converterToItemPedidoFromItemPedidoBOTipo3(ItemPedidoBO itemPedidoDTO, Integer identificadorPedido) {
		ItemPedido itemPedido = new ItemPedido();
		ItemPedidoPK itemPedidoPK = new ItemPedidoPK();
		Pedido pedido = new Pedido();
		pedido.setId(identificadorPedido);
		itemPedidoPK.setPedido(pedido);
		itemPedidoPK.setProduto(ProdutoConvert.converterToProdutoFromProdutoBO(itemPedidoDTO.getProdutoBO()));
		itemPedido.setId(itemPedidoPK);
		itemPedido.setQtde(itemPedidoDTO.getQtde());
		return itemPedido;
	}
	
	public static ItemPedidoBO converterToItemPedidoBoFromItemPedido(ItemPedido itemPedido) {
		ItemPedidoBO itemPedidoDTO = new ItemPedidoBO();
		itemPedidoDTO.setProdutoBO(ProdutoConvert.converterToProdutoBOFromProduto(itemPedido.getProduto()));
		itemPedidoDTO.setQtde(itemPedido.getQtde());
		itemPedidoDTO.setValorTotalItensPedido(itemPedido.getProduto().getPreco() == null ? 0d : itemPedidoDTO.getValorTotalItensPedido());
		return itemPedidoDTO;
	}
	
	public static ItemPedido converterToItemPedidoFromItemPedidoBO(ItemPedidoBO itemPedidoDTO, PedidoBO pedidoBO) {
		ItemPedido itemPedido = new ItemPedido();
		ItemPedidoPK itemPedidoPK = new ItemPedidoPK();
		itemPedidoPK.setPedido(PedidoConvert.converterToPedidoFromPedidoBO(pedidoBO));
		itemPedidoPK.setProduto(ProdutoConvert.converterToProdutoFromProdutoBO(itemPedidoDTO.getProdutoBO()));
		itemPedido.setId(itemPedidoPK);
		itemPedido.setQtde(itemPedidoDTO.getQtde());
		return itemPedido;
	}
}
