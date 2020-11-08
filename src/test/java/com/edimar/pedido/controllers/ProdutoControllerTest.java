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

import com.edimar.pedido.builder.ProdutoBuilder;
import com.edimar.pedido.model.dto.ProdutoBO;
import com.edimar.pedido.repositories.ProdutoRepository;
import com.edimar.pedido.services.ProdutoService;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProdutoControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;

	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	private static String BASE_URL = "/produto/";
	
	@InjectMocks @Spy
	private ProdutoService produtoService;
	
	@Mock
	private ProdutoRepository produtoRepository;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@Test
	public void teste01_deveRetornarSucesso_BuscaProduto() throws Exception {
		this.mockMvc.perform(get(BASE_URL+"/1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("descricao", equalTo("Arroz")));
	}
	
	@Test
	public void teste02_deveRetornarSucesso_BuscaProduto() throws Exception {
		this.mockMvc.perform(get(BASE_URL+"/todos")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void teste03_deveRetornarSucesso_SalvarProduto() throws Exception {
		this.mockMvc.perform(post(BASE_URL)
				.content("{\"descricao\": \"Biscoito\",\"categoria\": \"Alimento\",\"preco\": 2.73}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", is("http://localhost/produto/12")));
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste04_deveRetornarError_SalvarProduto() throws Exception {
		this.mockMvc.perform(post(BASE_URL)
				.content("{\"descricao\": \"Biscoito\",\"categoria\": \"Alimento\",\"preco\": 0.0}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste05_deveRetornarSucesso_AtualizarProduto() throws Exception {
		this.mockMvc.perform(put(BASE_URL)
				.content("{\"id\": 3, \"descricao\": \"Leite\", \"categoria\": \"Alimento\", \"preco\": 4.52}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste06_deveRetornarError_AtualizarProduto() throws Exception {
		this.mockMvc.perform(put(BASE_URL)
				.content("{\"id\": 3, \"descricao\": \"Leite\", \"categoria\": \"Alimento\", \"preco\": 0.0}")
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isInternalServerError());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste07_deveRetornarSucesso_DeletarProdutoPorId() throws Exception {
		this.mockMvc.perform(delete(BASE_URL+"/11"))
			.andExpect(status().isNoContent());
			//.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void teste08_deveRetornarError_DeletarProdutoPorId() throws Exception {
		this.mockMvc.perform(delete(BASE_URL+"/13"))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void teste09_XML_deveRetornarSucesso_BuscaProduto() throws Exception {
		ProdutoBO produto = ProdutoBuilder.montaEntityProdutoBOTipo1().retornoEntityProdutoBO();
		Mockito.doReturn(produto).when(produtoService).buscarProdutoPorId(Mockito.anyInt());
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/1").accept(MediaType.APPLICATION_XML);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "<ProdutoBO><id>1</id><descricao>Arroz</descricao><categoria>Alimento</categoria><preco>29.5</preco></ProdutoBO>";
		Assert.assertEquals(expected, result.getResponse().getContentAsString());
	}
	
	@Test
	public void teste10_XML_deveRetornarSucesso_BuscaProduto() throws Exception {
		ProdutoBO produto = ProdutoBuilder.montaEntityProdutoBOTipo1().retornoEntityProdutoBO();
		Mockito.doReturn(Arrays.asList(produto)).when(produtoService).litarProdutos();
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL+"/todos").accept(MediaType.APPLICATION_XML);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		System.out.println(result.getResponse());
		String expected = "<List><item><id>1</id><descricao>Arroz</descricao><categoria>Alimento</categoria><preco>29.5</preco></item><item><id>2</id><descricao>Feijao</descricao><categoria>Alimento</categoria><preco>10.25</preco></item><item><id>3</id><descricao>Leite</descricao><categoria>Alimento</categoria><preco>4.52</preco></item><item><id>4</id><descricao>Oleo</descricao><categoria>Alimento</categoria><preco>7.71</preco></item><item><id>5</id><descricao>Smartphone</descricao><categoria>Informatica</categoria><preco>951.5</preco></item><item><id>6</id><descricao>Notebook</descricao><categoria>Informatica</categoria><preco>3450.0</preco></item><item><id>7</id><descricao>Carregador Notebbok</descricao><categoria>Informatica</categoria><preco>48.0</preco></item><item><id>8</id><descricao>Carregador Smartphone</descricao><categoria>Informatica</categoria><preco>22.5</preco></item><item><id>9</id><descricao>Processador</descricao><categoria>Informatica</categoria><preco>770.0</preco></item><item><id>10</id><descricao>Placa Mãe</descricao><categoria>Informatica</categoria><preco>462.22</preco></item><item><id>12</id><descricao>Biscoito</descricao><categoria>Alimento</categoria><preco>2.73</preco></item></List>";
		Assert.assertEquals(expected, result.getResponse().getContentAsString());
	}
}
