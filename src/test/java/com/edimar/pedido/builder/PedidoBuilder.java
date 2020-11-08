package com.edimar.pedido.builder;

import java.util.Date;

import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.util.RecursosUtil;

public class PedidoBuilder {
	
	private PedidoBO pedidoBO;
	private Pedido pedido;
	
	private PedidoBuilder() {}
	
	public static PedidoBuilder montaEntityPedidoBOTipo1() {
		PedidoBuilder dto = new PedidoBuilder();
		dto.pedidoBO = new PedidoBO();
		dto.pedidoBO.setId(1);
		dto.pedidoBO.setDataCadastro(RecursosUtil.converterDateToString(new Date()));
		dto.pedidoBO.setNumPedido(1l);
		dto.pedidoBO.setValorTotalProdutos(50.36);
		return dto;
	}
	
	public static PedidoBuilder montaEntityPedidoTipo1() {
		PedidoBuilder dto = new PedidoBuilder();
		dto.pedido = new Pedido();
		dto.pedidoBO.setId(1);
		dto.pedidoBO.setDataCadastro(RecursosUtil.converterDateToString(new Date()));
		dto.pedidoBO.setNumPedido(1l);
		dto.pedidoBO.setValorTotalProdutos(50.36);
		return dto;
	}
	
	public PedidoBO retornoEntityPedidoBO() {
		return pedidoBO;
	}
	
	public Pedido retornoEntityPedido() {
		return pedido;
	}
}
