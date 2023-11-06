package org.springframework.samples.senai.pedido;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.senai.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author Luciano Édipo
 */
@Entity
@Table(name = "pedidos")
public class Pedido extends BaseEntity {

	@Column(name = "data_do_pedido")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private LocalDate date;

	/**
	 * Creates a new instance of Visit for the current date
	 */
	public Pedido() {
		this.date = LocalDate.now();
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

}
