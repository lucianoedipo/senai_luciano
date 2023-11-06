package org.springframework.samples.senai.produto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface ProdutoRepository extends Repository<Produto, Integer> {

	// Métodos de consulta personalizados

	@Transactional(readOnly = true)
	Optional<Produto> findById(Integer id);

	@Transactional(readOnly = true)
	List<Produto> findAll();

	@Transactional
	<S extends Produto> S save(S produto);

	@Transactional
	void deleteById(Integer id);

	@Transactional(readOnly = true)
	boolean existsById(Integer id);

}
