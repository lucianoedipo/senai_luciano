package org.springframework.samples.senai.pedido;

import org.springframework.samples.senai.produto.Produto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

/**
 * Representa um item do pedido, composto por um produto e uma quantidade.
 */
@Embeddable
public class ItemPedido {

	@ManyToOne
	private Produto produto;

	private Integer quantidade;

	// Construtores, getters e setters

	public ItemPedido() {
		// Construtor padrão
	}

	public ItemPedido(Produto produto, Integer quantidade) {
		this.produto = produto;
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
