package com.edimar.pedido;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edimar.pedido.model.ItemPedido;
import com.edimar.pedido.model.Pedido;
import com.edimar.pedido.model.Produto;
import com.edimar.pedido.services.ItemPedidoService;
import com.edimar.pedido.services.PedidoService;
import com.edimar.pedido.services.ProdutoService;

@SpringBootApplication
public class PedidoApplication implements CommandLineRunner{
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;

	public static void main(String[] args) {
		SpringApplication.run(PedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inicio das inclusões!");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("05/10/2020 13:58"), 1l, 1L);
		Pedido ped2 = new Pedido(null, sdf.parse("05/10/2020 15:09"), 2l, 2L);
		Pedido ped3 = new Pedido(null, sdf.parse("05/10/2020 15:09"), 3l, 3L);
		Pedido ped4 = new Pedido(null, sdf.parse("15/10/2020 15:09"), 4l, 4L);
		Pedido ped5 = new Pedido(null, sdf.parse("15/10/2020 15:09"), 5l, 4L);

		pedidoService.salvarPedidos(Arrays.asList(ped1, ped2, ped3, ped4, ped5));
		
		Produto prd1 = new Produto(null, "Arroz", "Alimento", 29.50);
		Produto prd2 = new Produto(null, "Feijao", "Alimento", 10.25);
		Produto prd3 = new Produto(null, "Macarrao", "Alimento", 4.48);
		Produto prd4 = new Produto(null, "Oleo", "Alimento", 7.71);
		Produto prd5 = new Produto(null, "Smartphone", "Informatica", 951.50);
		Produto prd6 = new Produto(null, "Notebook", "Informatica", 3450.0);
		Produto prd7 = new Produto(null, "Carregador Notebbok", "Informatica", 48.00);
		Produto prd8 = new Produto(null, "Carregador Smartphone", "Informatica", 22.50);
		Produto prd9 = new Produto(null, "Processador", "Informatica", 770.0);
		Produto prd10 = new Produto(null, "Placa Mãe", "Informatica", 462.22);
		Produto prd11 = new Produto(null, "Placa de video", "Informatica", 462.22);

		produtoService.salvarProdutos(Arrays.asList(prd1, prd2, prd3, prd4, prd5, prd6, prd7, prd8, prd9, prd10, prd11));
		
		ItemPedido item1 = new ItemPedido(ped1, prd1, 1);
		ItemPedido item2 = new ItemPedido(ped2, prd2, 3);
		ItemPedido item3 = new ItemPedido(ped2, prd4, 2);
		ItemPedido item4 = new ItemPedido(ped3, prd3, 2);
		ItemPedido item5 = new ItemPedido(ped3, prd4, 5);

		ItemPedido item6 = new ItemPedido(ped4, prd5, 2);
		ItemPedido item7 = new ItemPedido(ped4, prd6, 1);
		ItemPedido item8 = new ItemPedido(ped4, prd7, 1);
		ItemPedido item9 = new ItemPedido(ped4, prd8, 2);
		ItemPedido item10 = new ItemPedido(ped4, prd9, 2);
		
		ItemPedido item11 = new ItemPedido(ped5, prd1, 2);
		ItemPedido item12 = new ItemPedido(ped5, prd2, 2);
		ItemPedido item13 = new ItemPedido(ped5, prd3, 2);
		ItemPedido item14 = new ItemPedido(ped5, prd4, 2);
		ItemPedido item15 = new ItemPedido(ped5, prd5, 2);
		ItemPedido item16 = new ItemPedido(ped5, prd6, 2);
		ItemPedido item17 = new ItemPedido(ped5, prd7, 2);
		ItemPedido item18 = new ItemPedido(ped5, prd8, 2);
		ItemPedido item19 = new ItemPedido(ped5, prd9, 2);
		ItemPedido item20 = new ItemPedido(ped5, prd10, 2);

		itemPedidoService.salvarItensPedidos(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13, item14, item15, item16, item17, item18, item19, item20));
		
		System.out.println("Fim das inclusões!");
	}

}
