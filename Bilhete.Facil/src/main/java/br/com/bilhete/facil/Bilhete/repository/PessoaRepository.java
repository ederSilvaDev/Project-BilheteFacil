package br.com.bilhete.facil.Bilhete.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bilhete.facil.Bilhete.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
	
	@Query("select p from Pessoa p where p.nome like %?1% order by id")
	List<Pessoa> findPessoaByName(String nome);
	
	
	@Query("select p from Pessoa p where p.sexo = ?1")
	List<Pessoa> findPessoaBySexo(String sexo);
	 
	
	@Query("select p from Pessoa p where p.nome like %?1% and p.sexo = ?2")
	List<Pessoa> findPessoaByNameSexo(String nome, String sexo);
	
}
