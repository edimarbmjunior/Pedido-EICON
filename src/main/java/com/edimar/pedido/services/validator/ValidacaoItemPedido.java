package com.edimar.pedido.services.validator;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.model.dto.PedidoBO;

public class ValidacaoItemPedido {
	
	public static void validacaoConsultar(Integer idNumpedido, Integer idProduto) {
		isIdentificadorValido(idNumpedido, idProduto);
	}
	
	public static void validacaoConsultarNumPedido(Integer idNumpedido) {
		isIdentificadorValido(idNumpedido);
	}
	
	public static void validacaoAtualizar(PedidoBO pedidoBO) {
		validaPossuiItem(pedidoBO);
		validaPossuiProduto(pedidoBO);
	}
	
	private static void validaPossuiItem(PedidoBO pedidoBO) {
		if(pedidoBO.getItemPedidos() == null || (pedidoBO.getItemPedidos().isEmpty() || pedidoBO.getItemPedidos().size() == 0)) {
			throw new GenericExcpetion("Não foram enviado dados de itens de pedidos");
		}
	}
	
	private static void validaPossuiProduto(PedidoBO pedidoBO) {
		pedidoBO
			.getItemPedidos()
			.stream()
			.forEach((itemPedido) -> {
				isIdentificadorValido(pedidoBO.getId(), itemPedido.getProdutoBO().getId());
			});
	}
	
	private static void isIdentificadorValido(Integer idNumpedido, Integer idProduto) {
		if(null==idNumpedido
				|| idNumpedido <= 0) {
			throw new GenericExcpetion("Identificador do pedido é inválido!");
		}
		if(null==idProduto
				|| idProduto <= 0) {
			throw new GenericExcpetion("Identificador do produto é inválido!");
		}
	}
	
	private static void isIdentificadorValido(Integer idNumpedido) {
		if(null==idNumpedido
				|| idNumpedido <= 0) {
			throw new GenericExcpetion("Identificador do pedido é inválido!");
		}
	}
}
