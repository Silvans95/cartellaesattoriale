package it.prova.cartellaesattoriale.web.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.cartellaesattoriale.dto.CartellaEsattorialeDTO;
import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.service.CartellaEsattorialeService;
import it.prova.cartellaesattoriale.web.api.exception.CartellaEsattorialeNotFoundException;
import it.prova.cartellaesattoriale.web.api.exception.ContribuenteNotFoundException;
import it.prova.cartellaesattoriale.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/cartella")
public class CartellaEsattorialeController {

	@Autowired
	private CartellaEsattorialeService cartellaService;

	@GetMapping
	public List<CartellaEsattorialeDTO> getAll() {
		return CartellaEsattorialeDTO
				.createCartellaEsattorialeDTOListFromModelList(cartellaService.listAllElements(true), true);
	}

	@PostMapping
	public CartellaEsattorialeDTO createNew(@Valid @RequestBody CartellaEsattorialeDTO cartellaInput) {

		if (cartellaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		CartellaEsattoriale cartellaInserito = cartellaService
				.inserisciNuovo(cartellaInput.buildCartellaEsattorialeModel());
		return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartellaInserito, true);
	}

	@GetMapping("/{id}")
	public CartellaEsattorialeDTO findById(@PathVariable(value = "id", required = true) long id) {
		CartellaEsattoriale cartella = cartellaService.caricaSingoloElementoEager(id);

		if (cartella == null)
			throw new CartellaEsattorialeNotFoundException("CartellaEsattoriale not found con id: " + id);

		return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartella, true);
	}

	@PutMapping("/{id}")
	public CartellaEsattorialeDTO update(@Valid @RequestBody CartellaEsattorialeDTO cartellaInput,
			@PathVariable(required = true) Long id) {
		CartellaEsattoriale cartella = cartellaService.caricaSingoloElemento(id);

		if (cartella == null)
			throw new CartellaEsattorialeNotFoundException("CartellaEsattoriale not found con id: " + id);

		cartellaInput.setId(id);
		CartellaEsattoriale cartellaAggiornato = cartellaService
				.aggiorna(cartellaInput.buildCartellaEsattorialeModel());
		return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartellaAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		CartellaEsattoriale cartella = cartellaService.caricaSingoloElemento(id);

		if (cartella == null)
			throw new ContribuenteNotFoundException("CartellaEsattoriale not found con id: " + id);

		cartellaService.rimuovi(cartella);
	}

	@PostMapping("/search")
	public List<CartellaEsattorialeDTO> search(@RequestBody CartellaEsattorialeDTO example) {
		return CartellaEsattorialeDTO.createCartellaEsattorialeDTOListFromModelList(
				cartellaService.findByExample(example.buildCartellaEsattorialeModel()), false);
	}

}
