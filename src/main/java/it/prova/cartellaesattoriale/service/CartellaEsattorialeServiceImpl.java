package it.prova.cartellaesattoriale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.repository.cartellaesattoriale.CartellaEsattorialeRepository;

@Service
public class CartellaEsattorialeServiceImpl implements CartellaEsattorialeService {

	@Autowired
	private CartellaEsattorialeRepository repository;

	public List<CartellaEsattoriale> listAllElements(boolean eager) {
		if (eager)
			return (List<CartellaEsattoriale>) repository.findAllCartellaEsattorialeEager();

		return (List<CartellaEsattoriale>) repository.findAll();
	}

	public CartellaEsattoriale caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public CartellaEsattoriale caricaSingoloElementoEager(Long id) {
		return repository.findSingleCartellaEsattorialeEager(id);
	}

	@Transactional
	public CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaInstance) {
		return repository.save(cartellaInstance);
	}

	@Transactional
	public CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaInstance) {
		return repository.save(cartellaInstance);
	}

	@Transactional
	public void rimuovi(CartellaEsattoriale cartellaInstance) {
		repository.delete(cartellaInstance);
	}

	public List<CartellaEsattoriale> findByExample(CartellaEsattoriale example) {
		return repository.findByExample(example);
	}
}
