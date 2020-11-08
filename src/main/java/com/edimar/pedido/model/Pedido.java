package com.edimar.pedido.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.edimar.pedido.model.convert.ItemPedidoConvert;
import com.edimar.pedido.model.dto.PedidoBO;
import com.edimar.pedido.util.RecursosUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/YYY")
	private Date dataCadastro;
	
	private Long numPedido;
	
	@Column(nullable = false)
	private Long codCliente;
	
	//select * from information_schema.columns where table_name = 'PEDIDO'
	
	@OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
	private Set<ItemPedido> itensPedidos = new HashSet<>();
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public Pedido(Integer id, Date dataCadastro, Long numPedido, Long codCliente) {
		super();
		this.id = id;
		this.dataCadastro = dataCadastro;
		this.numPedido = numPedido;
		this.codCliente = codCliente;
	}
	
	public Pedido(PedidoBO pedidoBO) {
		super();
		this.id = pedidoBO.getId();
		this.dataCadastro = RecursosUtil.converterStringToDate(pedidoBO.getDataCadastro());
		this.numPedido = pedidoBO.getNumPedido();
		if(pedidoBO.getItemPedidos() != null && (!pedidoBO.getItemPedidos().isEmpty() && pedidoBO.getItemPedidos().size() > 0)) {
			this.itensPedidos = pedidoBO.getItemPedidos().stream().map(itemPedido -> ItemPedidoConvert.converterToItemPedidoFromItemPedidoBOFixoPedido(itemPedido, pedidoBO)).collect(Collectors.toSet());
		}
	}

	@JsonIgnore
	public List<Produto> getProduto(){
		List<Produto> listProdutos = new ArrayList<>();
		for(ItemPedido item : itensPedidos) {
			listProdutos.add(item.getProduto());
		}
		return listProdutos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Long getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(Long numPedido) {
		this.numPedido = numPedido;
	}
	
	public Long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(Long codCliente) {
		this.codCliente = codCliente;
	}

	public Set<ItemPedido> getItensPedidos() {
		return itensPedidos;
	}

	public void setItensPedidos(Set<ItemPedido> itensPedidos) {
		this.itensPedidos = itensPedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", dataCadastro=" + dataCadastro + ", numPedido=" + numPedido + ", itensPedidos=" + itensPedidos + "]";
	}
}
