package com.edimar.pedido.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.exceptions.ObjectNotFoundException;
import com.edimar.pedido.model.ItemPedido;
import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.convert.PedidoConvert;
import com.edimar.pedido.model.convert.ProdutoConvert;
import com.edimar.pedido.model.dto.ItemPedidoBO;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.repositories.PedidoRepository;
import com.edimar.pedido.services.validator.ValidacaoPedido;
import com.edimar.pedido.util.RecursosUtil;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;
	
	public PedidoBO buscarPedidoPorId(Integer id) {
		ValidacaoPedido.validacaoConsultar(id);
		Pedido p = pedidoRepository
				.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado! ID: " + id +"."));
		PedidoBO retorno = PedidoConvert.converterToPedidoBoFromPedido(p);
		return retorno;
	}
	
	public PedidoBO buscarPedidoPorNumPedido(Long numPedido) {
		ValidacaoPedido.validacaoConsultarNumPedido(numPedido);
		Integer identificador = pedidoRepository
				.recuperaPeloNumPedido(numPedido);
		PedidoBO retorno = buscarPedidoPorId(identificador);
		return retorno;
	}
	
	public List<PedidoBO> buscarPedidoPorDataCadastro(PedidoBO pedido) {
		ValidacaoPedido.validacaoConsultarDataCadastro(pedido.getDataCadastro());
		List<Pedido> listaData = pedidoRepository
				.porDataCadastro(RecursosUtil.converterStringToDate(pedido.getDataCadastro()));
		List<PedidoBO> listaRetorno = new ArrayList<>();
		for(Pedido p : listaData) {
			listaRetorno.add(buscarPedidoPorId(p.getId()));
		}
		return listaRetorno;
	}
	
	public List<PedidoBO> litarPedidos(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoBO> retorno = pedidos.stream().map(pedido -> PedidoConvert.converterToPedidoBoFromPedido(pedido)).collect(Collectors.toList());
		return retorno.isEmpty() ? Arrays.asList() : retorno;
	}
	
	public void salvarPedidos(List<Pedido> pedidos) {
		pedidoRepository.saveAll(pedidos);
	}
	
	private Pedido salvarPedido(Pedido pedido) {
		pedido.setId(null);
		return pedidoRepository.save(pedido);
	}
	
	public Integer incluirPedidoComProduto(PedidoBO pedidoBO) {
		ValidacaoPedido.validacaoIncluirComProduto(pedidoBO);
		long ultimNumPedido = pedidoRepository.ultimoNumPedido().getNumPedido() + 1l;
		Pedido pedido = new Pedido();
		pedidoBO.setNumPedido(ultimNumPedido);
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> new ItemPedidoBO(produtoService.buscarProdutoPorId(itemPedido.getProdutoBO().getId()), itemPedido.getQtde()))
				.collect(Collectors.toSet()));
		pedido = PedidoConvert.converterToPedidoFromPedidoBO(pedidoBO);
		final Pedido pedidoSalvo = salvarPedido(pedido);
		pedidoBO
			.getItemPedidos()
			.stream()
			.forEach((itemPedido) -> {
				ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
				if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
					itemPedidoService.salvarItemPedido(itemPedidoSalvar);
				}
			});
		return pedidoSalvo.getId();
	}
	
	public PedidoBO atualizarPedido(PedidoBO pedidoBO) {
		ValidacaoPedido.validacaoAtualziar(pedidoBO);
		buscarPedidoPorId(pedidoBO.getId());
		pedidoBO.setItemPedidos(pedidoBO
				.getItemPedidos()
				.stream()
				.map(itemPedido -> new ItemPedidoBO(produtoService.buscarProdutoPorId(itemPedido.getProdutoBO().getId()), itemPedido.getQtde()))
				.collect(Collectors.toSet()));
		Pedido pedido = PedidoConvert.converterToPedidoFromPedidoBOAtualizar(pedidoBO);
		itemPedidoService.deletarItemPedidoPorNumPedido(pedido.getId());
		final Pedido pedidoSalvo = pedidoRepository.save(pedido);
		pedidoBO
			.getItemPedidos()
			.stream()
			.forEach((itemPedido) -> {
				ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
				if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
					itemPedidoService.salvarItemPedido(itemPedidoSalvar);
				}
			});
		return PedidoConvert.converterToPedidoBoFromPedido(pedidoSalvo);
	}
	
	public void deletarPedidoPorId(Integer id) {
		ValidacaoPedido.validacaoConsultar(id);
		final PedidoBO pedidoBO = buscarPedidoPorId(id);
		final Pedido pedidoSalvo = PedidoConvert.converterToPedidoFromPedidoBO(pedidoBO);
		try {
			pedidoBO
				.getItemPedidos()
				.stream()
				.forEach((itemPedido) -> {
					ItemPedido itemPedidoSalvar = new ItemPedido(pedidoSalvo, ProdutoConvert.converterToProdutoFromProdutoBO(itemPedido.getProdutoBO()), itemPedido.getQtde());
					if(itemPedidoSalvar != null && (itemPedidoSalvar.getPedido() != null & itemPedidoSalvar.getPedido() !=null)) {
						itemPedidoService.deletarPedido(itemPedidoSalvar);
					}
				});
			pedidoRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new com.edimar.pedido.exceptions.DataIntegrityViolationException("Não é possíve excluir pedidos que possuem itens.");
		} catch (Exception e) {
			throw new GenericExcpetion("Erro ao ao tentar excluir -> " + id + " - Motivo: " + e.getMessage());
		}
	}
}
