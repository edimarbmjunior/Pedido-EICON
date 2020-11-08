package com.edimar.pedido.services.validator;

import java.util.Date;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.util.RecursosUtil;

public class ValidacaoPedido {
	
	public static void validacaoConsultar(Integer id) {
		isIdentificadorValido(id);
	}
	
	public static void validacaoConsultarNumPedido(Long id) {
		isNumPedidoValido(id);
	}
	
	public static void validacaoConsultarDataCadastro(String Data) {
		campoObrigatorio(isNULL(Data), "Data Cadastro");
	}
	
	public static void validacaoIncluirComProduto(PedidoBO pedidoBO) {
		campoOpcional(isNULL(pedidoBO.getDataCadastro()), pedidoBO);
		validacaoDataPedido(pedidoBO.getDataCadastro());
		campoNaoPodeAlterar(!isNULL(pedidoBO.getNumPedido()), "número pedido");
		validaItemPedidoObrigatorio(pedidoBO);
		validaCodCliente(pedidoBO);
		validaQtdeItens(pedidoBO);
	}
	
	public static void validacaoAtualziar(PedidoBO pedidoBO) {
		isIdentificadorValido(pedidoBO.getId());
		campoOpcional(isNULL(pedidoBO.getDataCadastro()), pedidoBO);
		validacaoDataPedido(pedidoBO.getDataCadastro());
		campoNaoPodeAlterar(!isNULL(pedidoBO.getNumPedido()), "número pedido");
		validaItemPedidoObrigatorio(pedidoBO);
		validaCodCliente(pedidoBO);
		validaQtdeItens(pedidoBO);
	}
	
	private static void isIdentificadorValido(Integer id) {
		if(null==id
				|| id <= 0) {
			throw new GenericExcpetion("Identificador enviado é inválido!");
		}
	}
	
	private static void isNumPedidoValido(Long numPedido) {
		if(null==numPedido
				|| numPedido <= 0) {
			throw new GenericExcpetion("Número pedido enviado é inválido!");
		}
	}
	
	private static void validacaoDataPedido(String dataPedido) {
		if(dataPedido != null && dataPedido.length() != 10) {
			throw new GenericExcpetion("Data do pedido é inválido.");
		}
		if(!isInteiro(dataPedido.substring(0, 1))
				|| !isInteiro(dataPedido.substring(6, 10))
				|| !isInteiro(dataPedido.substring(3, 4))
				|| "/".equals(dataPedido.substring(2, 2))
				|| "/".equals(dataPedido.substring(5, 5))) {
			throw new GenericExcpetion("Data do pedido é inválido.");
		}
	}

	private static Boolean isNULL(Object obj) {
		if(obj==null) {
			return true;
		}
		return false;
	}
	
	private static void campoObrigatorio(Boolean fazCritica, String campo) {
		if(fazCritica) {
			throw new GenericExcpetion("O campo " + campo + "deve está preenchido corretamente!");
		}
	}

	private static void campoOpcional(Boolean estaPreenchido, PedidoBO pedidoBO) {
		if(estaPreenchido) {
			pedidoBO.setDataCadastro(RecursosUtil.converterDateToString(new Date()));
		}
	}
	
	private static void campoNaoPodeAlterar(Boolean fazCritica, String nomeCampo) {
		if(fazCritica) throw new GenericExcpetion("Campo " + nomeCampo + " não pode ser informado!");
	}
	
	private static void validaItemPedidoObrigatorio(PedidoBO pedidoBO) {
		if(pedidoBO.getItemPedidos() == null || (pedidoBO.getItemPedidos().isEmpty() || pedidoBO.getItemPedidos().size() == 0)) {
			throw new GenericExcpetion("Deve conter dados de pedidos");
		}
		if(pedidoBO.getItemPedidos().size() > 10) {
			throw new GenericExcpetion("Quantidade de itens inválidos");
		}
	}
	
	private static void validaCodCliente(PedidoBO pedidoBO) {
		if(pedidoBO.getCodCliente()==null) {
			throw new GenericExcpetion("Deve conter o identificador do cliente!");
		}else {
			if(pedidoBO.getCodCliente()<1) {
				throw new GenericExcpetion("Deve conter o identificador do cliente válido!");
			}
		}
	}
	
	private static void validaQtdeItens(PedidoBO pedidoBO) {
		pedidoBO.getItemPedidos().stream().forEach(item -> {
			if(item.getQtde()==null) {
				item.setQtde(1);
			}else {
				if(item.getQtde()<1) {
					item.setQtde(1);
				}
			}
		});
	}
	
	private static boolean isInteiro(String s) {
		// cria um array de char
		char[] c = s.toCharArray();
		boolean d = true;

		for ( int i = 0; i < c.length; i++ ) {
		    // verifica se o char não é um dígito
		    if ( !Character.isDigit( c[ i ] ) ) {
		        d = false;
		        break;
		    }
		}

		return d;
	}
}
