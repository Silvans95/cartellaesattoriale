package it.prova.cartellaesattoriale.repository.cartellaesattoriale;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;


public interface CartellaEsattorialeRepository extends CrudRepository<CartellaEsattoriale, Long>, CustomCartellaEsattorialeRepository {
	@Query("from CartellaEsattoriale f join fetch f.contribuente where f.id = ?1")
	CartellaEsattoriale findSingleCartellaEsattorialeEager(Long id);
	
	@Query("select f from CartellaEsattoriale f join fetch f.contribuente")
	List<CartellaEsattoriale> findAllCartellaEsattorialeEager();

}
