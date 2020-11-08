package com.edimar.pedido.model.convert;

import java.util.stream.Collectors;

import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.util.RecursosUtil;

public class PedidoConvert {

	public static Pedido converterToPedidoFromPedidoBO(PedidoBO pedidoBO) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoBO.getId() == null ? null : pedidoBO.getId());
		pedido.setDataCadastro(pedidoBO.getDataCadastro() == null ? null : RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro()));
		pedido.setNumPedido(pedidoBO.getNumPedido() ==null ? null : pedidoBO.getNumPedido());
		pedido.setCodCliente(pedidoBO.getCodCliente());
		pedido.setItensPedidos(pedidoBO.getItemPedidos() == null ? null : pedidoBO.getItemPedidos().stream().map(itemPedido -> ItemPedidoConvert.converterToItemPedidoFromItemPedidoBOFixoPedido(itemPedido, pedidoBO)).collect(Collectors.toSet()));
		return pedido;
	}
	
	public static Pedido converterToPedidoFromPedidoBOAtualizar(PedidoBO pedidoBO) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoBO.getId() == null ? null : pedidoBO.getId());
		pedido.setDataCadastro(pedidoBO.getDataCadastro() == null ? null : RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro()));
		pedido.setNumPedido(pedidoBO.getNumPedido() ==null ? null : pedidoBO.getNumPedido());
		pedido.setCodCliente(pedidoBO.getCodCliente());
		return pedido;
	}
	
	public static Pedido converterToPedidoFromPedidoBOTipo2(PedidoBO pedidoBO) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoBO.getId());
		pedido.setDataCadastro(RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro()));
		pedido.setNumPedido(pedidoBO.getNumPedido());
		pedido.setCodCliente(pedidoBO.getCodCliente());
		pedido.setItensPedidos(pedidoBO.getItemPedidos().stream().map(itemPedido -> ItemPedidoConvert.converterToItemPedidoFromItemPedidoBO(itemPedido, pedidoBO)).collect(Collectors.toSet()));
		return pedido;
	}
	
	public static Pedido converterToPedidoFromPedidoBOTipo3(PedidoBO pedidoBO) {
		Pedido pedido = new Pedido();
		pedido.setId(pedidoBO.getId());
		pedido.setDataCadastro(RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro()));
		pedido.setNumPedido(pedidoBO.getNumPedido());
		pedido.setCodCliente(pedidoBO.getCodCliente());
		pedido.setItensPedidos(pedidoBO.getItemPedidos().stream().map(itemPedido -> ItemPedidoConvert.converterToItemPedidoFromItemPedidoBOFixoPedido(itemPedido, pedidoBO)).collect(Collectors.toSet()));
		return pedido;
	}
	
	public static PedidoBO converterToPedidoBoFromPedido(Pedido pedido) {
		PedidoBO pedidoBO = new PedidoBO();
		pedidoBO.setId(pedido.getId());
		pedidoBO.setDataCadastro(RecursosUtil.converterDateToString(pedido.getDataCadastro()));
		pedidoBO.setNumPedido(pedido.getNumPedido());
		pedidoBO.setCodCliente(pedido.getCodCliente());
		pedidoBO.setItemPedidos(pedido.getItensPedidos() == null ? null : pedido.getItensPedidos().stream().map(itemPedido -> ItemPedidoConvert.converterToItemPedidoBoFromItemPedido(itemPedido)).collect(Collectors.toSet()));
		pedidoBO.setValorTotalProdutos(pedidoBO.getValorTotalProdutos());
		
		return pedidoBO;
	}
}
