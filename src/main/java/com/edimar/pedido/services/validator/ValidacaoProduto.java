package com.edimar.pedido.services.validator;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.model.dto.ProdutoBO;

public class ValidacaoProduto {

	public static void validacaoConsultar(Integer id) {
		isIdentificadorValido(id);
	}
	
	public static void validacaoIncluirProduto(ProdutoBO produtoBO) {
		campoObrigatorio(isNULL(produtoBO.getCategoria()), "categoria");
		campoObrigatorio(isNULL(produtoBO.getDescricao()), "descrição");
		validacaoDescricao(produtoBO.getDescricao());
		campoObrigatorio(isNULL(produtoBO.getPreco()), "preço");
		validaPreco(produtoBO.getPreco());
	}
	
	public static void validacaoAtualziarProduto(ProdutoBO produtoBO) {
		isIdentificadorValido(produtoBO.getId());
		campoObrigatorio(isNULL(produtoBO.getCategoria()), "categoria");
		campoObrigatorio(isNULL(produtoBO.getDescricao()), "descrição");
		validacaoDescricao(produtoBO.getDescricao());
		campoObrigatorio(isNULL(produtoBO.getPreco()), "preço");
		validaPreco(produtoBO.getPreco());
	}

	private static void isIdentificadorValido(Integer id) {
		if(null==id
				|| id <= 0) {
			throw new GenericExcpetion("Identificador enviado é inválido!");
		}
	}
	
	private static void validacaoDescricao(String descricao) {
		if(descricao != null && descricao.length() < 5) {
			throw new GenericExcpetion("Descrição é inválido. Quantidade mínima de carateres é 5!");
		}
	}
	
	private static void validaPreco(Double preco) {
		if(preco <= Double.valueOf("0")) {
			throw new GenericExcpetion("Preço é inválido. Valor deve ser maior que zero!");
		}
	}

	private static Boolean isNULL(Object obj) {
		if(obj==null) {
			return true;
		}
		return false;
	}
	
	private static void campoObrigatorio(Boolean fazCritica, String nomeCampo) {
		if(fazCritica) throw new GenericExcpetion("Campo " + nomeCampo + " é obrigatório");
	}
}
