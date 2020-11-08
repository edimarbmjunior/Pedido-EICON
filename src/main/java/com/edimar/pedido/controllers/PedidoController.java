package com.edimar.pedido.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.services.PedidoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(value="/pedido")
@Api(tags = "Pedido", description = "API de pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca pedido pelo codigo")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Busca realizado com sucesso"),
			@ApiResponse(code = 404, message = "Não foi possível localizar"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<?> buscarPedidoPorId(@PathVariable Integer id) {
		PedidoBO p = pedidoService.buscarPedidoPorId(id);
		System.out.println(p);
		return ResponseEntity.ok().body(p);
	}
	
	@RequestMapping(value="/numpedido/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Busca pedido pelo numPedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Busca realizado com sucesso"),
			@ApiResponse(code = 404, message = "Não foi possível localizar"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<?> buscarPedidoPorNumPedido(@PathVariable Long id) {
		PedidoBO p = pedidoService.buscarPedidoPorNumPedido(id);
		return ResponseEntity.ok().body(p);
	}
	
	@RequestMapping(value="/dataCadastro", method = RequestMethod.GET)
	@ApiOperation(value = "Busca pedido pelo numPedido")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Busca realizado com sucesso"),
			@ApiResponse(code = 404, message = "Não foi possível localizar"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<?> buscarPedidoPorDataCadastro(@RequestBody PedidoBO pedido) {
		List<PedidoBO> listaPedido = pedidoService.buscarPedidoPorDataCadastro(pedido);
		return ResponseEntity.ok().body(listaPedido);
	}
	
	@RequestMapping(value="/todos", method = RequestMethod.GET)
	@ApiOperation(value = "Busca pedidos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Busca realizado com sucesso"),
			@ApiResponse(code = 404, message = "Não foi possível localizar"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<List<PedidoBO>> buscarProdutos() {
		List<PedidoBO> pedidos = pedidoService.litarPedidos();
		return ResponseEntity.ok().body(pedidos);
	}
	
	@RequestMapping(value= "salvarPedido", method = RequestMethod.POST)
	@ApiOperation(value = "Salva um pedido com item de pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Inclusão realizado com sucesso"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<Void> salvarPedidoComProduto(@RequestBody PedidoBO pedido){
		Integer identificadorPedido = pedidoService.incluirPedidoComProduto(pedido);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(identificadorPedido)
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza um pedido")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Atualização realizado com sucesso"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<Void> atualizarPedido(@RequestBody PedidoBO pedido){
		pedido = pedidoService.atualizarPedido(pedido);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deleta um pedido pelo identificador")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Deleção realizado com sucesso"),
			@ApiResponse(code = 500, message = "Houve algum erro no processamento da informação passada")
	})
	public ResponseEntity<Void> deletarPedidoPorId(@PathVariable Integer id) {
		pedidoService.deletarPedidoPorId(id);
		return ResponseEntity.noContent().build();
	}
}
