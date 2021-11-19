package it.prova.cartellaesattoriale.service;

import java.util.List;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;


public interface CartellaEsattorialeService {
	List<CartellaEsattoriale> listAllElements(boolean eager);

	CartellaEsattoriale caricaSingoloElemento(Long id);

	CartellaEsattoriale caricaSingoloElementoEager(Long id);

	CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaInstance);

	CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaInstance);

	void rimuovi(CartellaEsattoriale cartellaInstance);

	List<CartellaEsattoriale> findByExample(CartellaEsattoriale example);


}
