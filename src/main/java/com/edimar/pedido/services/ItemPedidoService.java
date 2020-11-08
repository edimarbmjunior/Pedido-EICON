package com.edimar.pedido.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.model.ItemPedido;
import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.convert.ItemPedidoConvert;
import com.edimar.pedido.model.convert.PedidoConvert;
import com.edimar.pedido.model.convert.ProdutoConvert;
import com.edimar.pedido.model.dto.ItemPedidoBO;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.repositories.ItemPedidoRepository;
import com.edimar.pedido.services.validator.ValidacaoItemPedido;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public ItemPedidoBO buscarItemPedido(Integer idNumpedido, Integer idProduto) {
		ValidacaoItemPedido.validacaoConsultar(idNumpedido, idProduto);
		try {
			ItemPedido itemPedido = itemPedidoRepository.itemPedidoIdentificador(idNumpedido, idProduto);
			ItemPedidoBO itemPedidoBO = ItemPedidoConvert.converterToItemPedidoBoFromItemPedido(itemPedido);
			return itemPedidoBO;
		}catch (Exception e) {
			throw new GenericExcpetion("Error ao buscar daos do Itempedido ->" + idNumpedido + "/" + idProduto);
		}
	}
	
	public void salvarItensPedidos(List<ItemPedido> itensPedidos) {
		itemPedidoRepository.saveAll(itensPedidos);
	}
	
	public ItemPedido salvarItemPedido(ItemPedido itemPedido) {
		return itemPedidoRepository.save(itemPedido);
	}
	
	public Integer incluirItemPedido(PedidoBO pedidoBO) {
		ValidacaoItemPedido.validacaoAtualizar(pedidoBO);
		final PedidoBO pedidoSalvoBO = pedidoService.buscarPedidoPorId(pedidoBO.getId());
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> new ItemPedidoBO(produtoService.buscarProdutoPorId(itemPedido.getProdutoBO().getId()), itemPedido.getQtde()))
				.collect(Collectors.toSet()));
		final Pedido pedidoSalvo = PedidoConvert.converterToPedidoFromPedidoBO(pedidoSalvoBO);
		pedidoBO
			.getItemPedidos()
			.stream()
			.forEach((itemPedido) -> {
				ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
				if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
					salvarItemPedido(itemPedidoSalvar);
				}
			});
		return pedidoSalvo.getId();
	}
	
	public PedidoBO atualizarItemPedido(PedidoBO pedidoBO) {
		ValidacaoItemPedido.validacaoAtualizar(pedidoBO);
		final PedidoBO pedidoSalvoBO = pedidoService.buscarPedidoPorId(pedidoBO.getId());
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> new ItemPedidoBO(produtoService.buscarProdutoPorId(itemPedido.getProdutoBO().getId()), itemPedido.getQtde()))
				.collect(Collectors.toSet()));
		final Pedido pedidoSalvo = PedidoConvert.converterToPedidoFromPedidoBO(pedidoSalvoBO);
		pedidoBO
			.getItemPedidos()
			.stream()
			.forEach((itemPedido) -> {
				ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
				if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
					salvarItemPedido(itemPedidoSalvar);
				}
			});
		return pedidoService.buscarPedidoPorId(pedidoBO.getId());
	}
	
	public void deletarItemPedidoPorId(final Integer idNumpedido, Integer idProduto) {
		ValidacaoItemPedido.validacaoConsultar(idNumpedido, idProduto);
		final PedidoBO pedidoBO = pedidoService.buscarPedidoPorId(idNumpedido);
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> buscarItemPedido(idNumpedido, itemPedido.getProdutoBO().getId()))
				.collect(Collectors.toSet()));
		final Pedido pedidoSalvo = PedidoConvert.converterToPedidoFromPedidoBO(pedidoBO);
		try {
			pedidoBO
				.getItemPedidos()
				.stream()
				.forEach((itemPedido) -> {
					ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
					if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
						if(idNumpedido.toString().trim().equals(itemPedidoSalvar.getPedido().getId().toString().trim())
								&& idProduto.toString().trim().equals(itemPedidoSalvar.getProduto().getId().toString().trim())) {
							deletarPedido(itemPedidoSalvar);
						}
					}
				});
		} catch (DataIntegrityViolationException e) {
			throw new com.edimar.pedido.exceptions.DataIntegrityViolationException("Não foi possível excluir ItemPedido que que possuem itens.");
		} catch (Exception e) {
			throw new GenericExcpetion("Erro ao ao tentar excluir -> " + idNumpedido + "/" + idProduto + " - Motivo: " + e.getMessage());
		}
	}
	
	public void deletarItemPedidoPorNumPedido(final Integer idNumpedido) {
		ValidacaoItemPedido.validacaoConsultarNumPedido(idNumpedido);
		final PedidoBO pedidoBO = pedidoService.buscarPedidoPorId(idNumpedido);
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> buscarItemPedido(idNumpedido, itemPedido.getProdutoBO().getId()))
				.collect(Collectors.toSet()));
		final Pedido pedidoSalvo = PedidoConvert.converterToPedidoFromPedidoBO(pedidoBO);
		try {
			pedidoBO
				.getItemPedidos()
				.stream()
				.forEach((itemPedido) -> {
					ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
					if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
						if(idNumpedido.toString().trim().equals(itemPedidoSalvar.getPedido().getId().toString().trim())) {
							deletarPedido(itemPedidoSalvar);
						}
					}
				});
		} catch (DataIntegrityViolationException e) {
			throw new com.edimar.pedido.exceptions.DataIntegrityViolationException("Não foi possível excluir ItemPedido que que possuem itens.");
		} catch (Exception e) {
			throw new GenericExcpetion("Erro ao ao tentar excluir -> " + idNumpedido + " - Motivo: " + e.getMessage());
		}
	}
	
	public void deletarPedido(ItemPedido itemPedido) {
		buscarItemPedido(itemPedido.getId().getPedido().getId(), itemPedido.getId().getProduto().getId());
		try {
			itemPedidoRepository.delete(itemPedido);
		} catch (DataIntegrityViolationException e) {
			throw new com.edimar.pedido.exceptions.DataIntegrityViolationException("Não é possíve excluir os itens de pedido do identificador -> " + itemPedido.getId().getProduto().getId() + ".");
		}
	}
}
