package com.edimar.pedido.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edimar.pedido.model.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer>{

	@Query("select i from ItemPedido i where PEDIDO_ID = ?1 and PRODUTO_ID = ?2")
	public ItemPedido itemPedidoIdentificador(Integer idPedido, Integer idProduto);
}
