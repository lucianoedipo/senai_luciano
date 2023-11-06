package org.springframework.samples.senai.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.senai.model.Person;
import org.springframework.samples.senai.pedido.Pedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;

/**
 * Simple JavaBean domain object representing an cliente.
 *
 * @author Luciano Édipo
 */
@Entity
@Table(name = "clientes")
public class Cliente extends Person {

	@Column(name = "Endereço")
	@NotBlank
	private String address;

	@Column(name = "Cidade")
	@NotBlank
	private String city;

	@Column(name = "Celular")
	@NotBlank
	@Digits(fraction = 0, integer = 10)
	private String telephone;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id")
	@OrderBy("date")
	private List<Pedido> pedidos = new ArrayList<>();

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void addPedido(Pedido pedido) {
		if (pedido.isNew()) {
			getPedidos().add(pedido);
		}
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.toString();
	}

}
