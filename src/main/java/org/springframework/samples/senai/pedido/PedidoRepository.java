package org.springframework.samples.senai.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoRepository extends Repository<Pedido, Integer> {

	@Transactional(readOnly = true)
	Optional<Pedido> findById(Integer id);

	@Transactional(readOnly = true)
	List<Pedido> findAll();

	@Transactional
	Pedido save(Pedido pedido);

	@Transactional
	void deleteById(Integer id);

	@Transactional(readOnly = true)
	List<Pedido> findByDate(LocalDate date);

	@Transactional(readOnly = true)
	List<Pedido> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
