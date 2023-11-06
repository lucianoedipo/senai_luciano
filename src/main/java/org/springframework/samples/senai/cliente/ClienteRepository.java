package org.springframework.samples.senai.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends Repository<Cliente, Integer> {

	@Query("SELECT DISTINCT cliente FROM Cliente cliente LEFT JOIN FETCH cliente.pedidos WHERE cliente.lastName LIKE :lastName% ")
	@Transactional(readOnly = true)
	Page<Cliente> findByLastName(@Param("lastName") String lastName, Pageable pageable);

	@Query("SELECT cliente FROM Cliente cliente LEFT JOIN FETCH cliente.pedidos WHERE cliente.id = :id")
	@Transactional(readOnly = true)
	Cliente findById(@Param("id") Integer id);

	void save(Cliente cliente);

	@Query("SELECT cliente FROM Cliente cliente LEFT JOIN FETCH cliente.pedidos")
	@Transactional(readOnly = true)
	Page<Cliente> findAll(Pageable pageable);

}
