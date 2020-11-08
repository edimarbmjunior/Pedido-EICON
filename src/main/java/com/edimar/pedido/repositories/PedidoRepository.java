package com.edimar.pedido.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edimar.pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Query("select max(p) from Pedido p order by 1 desc")
	public Pedido ultimoNumPedido();
	
	@Query("select p.id from Pedido p where numPedido = ?1 order by 1 desc")
	public Integer recuperaPeloNumPedido(Long numPedidoLong);
	
	@Query("select p from Pedido p where dataCadastro = :dataEnviada order by dataCadastro desc")
	public List<Pedido> porDataCadastro(@Param("dataEnviada") Date dataEnviada);
}
