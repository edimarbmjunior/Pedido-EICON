package com.edimar.pedido.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.edimar.pedido.builder.PedidoBuilder;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.repositories.PedidoRepository;
import com.edimar.pedido.services.PedidoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PedidoControllerTest {
	
private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	private static String BASE_URL = "/pedido";
	
	@InjectMocks @Spy
	private PedidoService pedidoService;
	
	@Mock
	private PedidoRepository produtoRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void teste01_deveRetornarSucesso_BuscaPedido() throws Exception {
		this.mockMvc.perform(get(BASE_URL+"/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", equalTo(1)));
	}
	
	@Test
	public void teste02_deveRetornarSucesso_BuscaPedido() throws Exception {
		this.mockMvc.perform(get(BASE_URL+"/todos")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void teste03_deveRetornarSucesso_SalvarPedido() throws Exception {
		this.mockMvc.perform(post(BASE_URL+"/salvarPedido")
				.content("{ \"dataCadastro\": \"25/05/2020\",\"codCliente\": 1,\"itemPedidos\": [{\"produtoBO\": {\"id\": 1},\"qtde\": 2},{\"produtoBO\": {\"id\": 3},\"qtde\": 7}]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", is("http://localhost/pedido/salvarPedido/6")));
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste04_deveRetornarError_SalvarPedido() throws Exception {
		this.mockMvc.perform(post(BASE_URL+"/salvarPedido")
				.content("{ \"dataCadastro\": \"25/05/2020\",\"codCliente\": 0,\"itemPedidos\": [{\"produtoBO\": {\"id\": 1},\"qtde\": 2},{\"produtoBO\": {\"id\": 3},\"qtde\": 7}]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste05_deveRetornarSucesso_AtualizarPedido() throws Exception {
		this.mockMvc.perform(put(BASE_URL)
				.content("{ \"id\": 6,\"dataCadastro\": \"25/05/2020\",\"codCliente\": 3,\"itemPedidos\": [{\"produtoBO\": {\"id\": 5},\"qtde\": 3}]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste06_deveRetornarError_AtualizarPedido() throws Exception {
		this.mockMvc.perform(put(BASE_URL)
				.content("{ \"id\": 9,\"dataCadastro\": \"25/05/2020\",\"codCliente\": 3,\"itemPedidos\": [{\"produtoBO\": {\"id\": 5},\"qtde\": 3}]}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound());
			//.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void teste07_deveRetornarSucesso_DeletarProdutoPorId() throws Exception {
		this.mockMvc.perform(delete(BASE_URL+"/3"))
			.andExpect(status().isNoContent());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste08_XML_deveRetornarSucesso_BuscaProduto() throws Exception {
		PedidoBO pedido = PedidoBuilder.montaEntityPedidoBOTipo1().retornoEntityPedidoBO();
		Mockito.doReturn(pedido).when(pedidoService).buscarPedidoPorId(Mockito.anyInt());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/1").accept(MediaType.APPLICATION_XML);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "<PedidoBO><id>1</id><dataCadastro>05/10/2020</dataCadastro><numPedido>1</numPedido><codCliente>1</codCliente><valorTotalProdutos>29.5</valorTotalProdutos><itemPedidos><itemPedidos><qtde>1</qtde><valorTotalItensPedido>29.5</valorTotalItensPedido><produtoBO><id>1</id><descricao>Arroz</descricao><categoria>Alimento</categoria><preco>29.5</preco></produtoBO></itemPedidos></itemPedidos></PedidoBO>";
		Assert.assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void teste09_XML_deveRetornarSucesso_BuscaProduto() throws Exception {
		PedidoBO pedido = PedidoBuilder.montaEntityPedidoBOTipo1().retornoEntityPedidoBO();
		Mockito.doReturn(Arrays.asList(pedido)).when(pedidoService).litarPedidos();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/todos").accept(MediaType.APPLICATION_XML);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		Assert.assertTrue(result.getResponse().getContentAsString().contains("<List><item><id>1</id>"));
	}
}
