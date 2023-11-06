package org.springframework.samples.senai.produto;

import org.springframework.samples.senai.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Representa um produto com um nome e um preço.
 */
@Entity
@Table(name = "produtos")
public class Produto extends BaseEntity {

	@Column(name = "nome")
	private String nome;

	@Column(name = "preco")
	private double preco;

	// Construtores, getters e setters

	public Produto() {
		// Construtor padrão
	}

	public Produto(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}
