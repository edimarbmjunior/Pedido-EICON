package com.edimar.pedido.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.exceptions.ObjectNotFoundException;
import com.edimar.pedido.model.Produto;
import com.edimar.pedido.model.convert.ProdutoConvert;
import com.edimar.pedido.model.dto.ProdutoBO;
import com.edimar.pedido.repositories.ProdutoRepository;
import com.edimar.pedido.services.validator.ValidacaoProduto;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<ProdutoBO> litarProdutos(){
		List<Produto> produtos = produtoRepository.findAll();
		List<ProdutoBO> produtosBO = produtos.stream().map(produto -> ProdutoConvert.converterToProdutoBOFromProduto(produto)).collect(Collectors.toList());
		return produtosBO.isEmpty() ? Arrays.asList() : produtosBO;
	}
	
	public ProdutoBO buscarProdutoPorId(Integer id) {
		ValidacaoProduto.validacaoConsultar(id);
		Produto produto = produtoRepository
				.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado! ID: " + id +"."));
		ProdutoBO produtoBO = ProdutoConvert.converterToProdutoBOFromProduto(produto);
		return produtoBO;
	}
	
	public void salvarProdutos(List<Produto> produtos) {
		produtoRepository.saveAll(produtos);
	}
	
	public ProdutoBO salvarProduto(ProdutoBO produtoBO) {
		ValidacaoProduto.validacaoIncluirProduto(produtoBO);
		Produto produto = ProdutoConvert.converterToProdutoFromProdutoBO(produtoBO);
		produto.setId(null);
		return ProdutoConvert.converterToProdutoBOFromProduto(produtoRepository.save(produto));
	}
	
	public ProdutoBO atualizarProduto(ProdutoBO produtoBO) {
		ValidacaoProduto.validacaoAtualziarProduto(produtoBO);
		buscarProdutoPorId(produtoBO.getId());
		Produto produto = ProdutoConvert.converterToProdutoFromProdutoBO(produtoBO);
		return ProdutoConvert.converterToProdutoBOFromProduto(produtoRepository.save(produto));
	}
	
	public void deletarProdutoPorId(Integer id) {
		ValidacaoProduto.validacaoConsultar(id);
		buscarProdutoPorId(id);
		try {
			produtoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.edimar.pedido.exceptions.DataIntegrityViolationException("Não é possíve excluir produtos que que possuem pedidos");
		} catch (Exception e) {
			throw new GenericExcpetion("Erro ao ao tentar excluir -> " + id + " - Motivo: " + e.getMessage());
		}
	}
}
