package it.prova.cartellaesattoriale.service;

import java.util.List;
import java.util.Optional;

import it.prova.cartellaesattoriale.model.Contribuente;

public interface ContribuenteService {

	List<Contribuente> listAllElements();

	List<Contribuente> listAllElementsEager();

	Contribuente caricaSingoloElemento(Long id);

	Contribuente caricaSingoloElementoConCartelle(Long id);

	Contribuente aggiorna(Contribuente contribuenteInstance);

	Contribuente inserisciNuovo(Contribuente contribuenteInstance);

	void rimuovi(Contribuente contribuenteInstance);

	List<Contribuente> findByExample(Contribuente example);

	List<Contribuente> cercaByCognomeENomeILike(String term);

	Contribuente findByNomeAndCognome(String nome, String cognome);

	Optional<Contribuente> findById(Long id);

}
