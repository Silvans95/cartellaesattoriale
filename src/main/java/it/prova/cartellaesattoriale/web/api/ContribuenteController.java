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

import it.prova.cartellaesattoriale.dto.ContribuenteDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteMetodiDTO;
import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.model.Stato;
import it.prova.cartellaesattoriale.service.ContribuenteService;
import it.prova.cartellaesattoriale.web.api.exception.CartelleAssociateException;
import it.prova.cartellaesattoriale.web.api.exception.ContribuenteNotFoundException;
import it.prova.cartellaesattoriale.web.api.exception.IdNotNullForInsertException;

@RestController
@RequestMapping("api/contribuente")
public class ContribuenteController {

	@Autowired
	private ContribuenteService contribuenteService;

	@GetMapping
	public List<ContribuenteDTO> getAll() {
		return ContribuenteDTO.createContribuenteDTOListFromModelList(contribuenteService.listAllElementsEager(), true);
	}

	@GetMapping("/{id}")
	public ContribuenteDTO findById(@PathVariable(value = "id", required = true) long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElementoConCartelle(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuente, true);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContribuenteDTO createNew(@Valid @RequestBody ContribuenteDTO contribuenteInput) {

		if (contribuenteInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Contribuente contribuenteInserito = contribuenteService
				.inserisciNuovo(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteInserito, false);
	}

	@PutMapping("/{id}")
	public ContribuenteDTO update(@Valid @RequestBody ContribuenteDTO contribuenteInput,
			@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		contribuenteInput.setId(id);
		Contribuente contribuenteAggiornato = contribuenteService.aggiorna(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		if (contribuente.getCartelle() != null)
			throw new CartelleAssociateException("Ci sono ancora delle cartelleEsattoriali associati al contribuente");

		contribuenteService.rimuovi(contribuente);
	}

	@PostMapping("/search")
	public List<ContribuenteDTO> search(@RequestBody ContribuenteDTO example) {
		return ContribuenteDTO.createContribuenteDTOListFromModelList(
				contribuenteService.findByExample(example.buildContribuenteModel()), false);
	}

//	####################################################
	@GetMapping("/reportContribuenti")
	public List<ContribuenteMetodiDTO> reportContribuenti() {
		List<ContribuenteMetodiDTO> contribuenti = ContribuenteMetodiDTO
				.createContribuenteDTOListFromModelList(contribuenteService.listAllElements(), true);

		contribuenti.stream().forEach(con -> {
			con.setTotale(0);
			con.setConclusoPagato(0);
			con.setInContenzioso(0);
			con.getCartelle().stream().forEach(car -> {
				// conto del totale
				con.setTotale(con.getTotale() + car.getImporto());
				// conto delle cartelle pagate
				if (car.getStato() == Stato.CONCLUSA) {
					con.setConclusoPagato(con.getConclusoPagato() + car.getImporto());
				}
				// conto in contenzioso
				if (car.getStato() == Stato.IN_CONTENZIOSO) {
					con.setInContenzioso(con.getInContenzioso() + car.getImporto());
				}
			});
			con.setCartelle(null);
		});

//		for (ContribuenteMetodiDTO contribuentiItem : contribuenti) {
//			Integer importoTotale = 0;
//			for (CartellaEsattorialeDTO cartelleItem : contribuentiItem.getCartelle()) {
//				importoTotale = importoTotale + cartelleItem.getImporto();
//				contribuentiItem.setTotale(importoTotale);
//			}
//		}
//		for (ContribuenteMetodiDTO contribuentiItem : contribuenti) {
//			Integer conclusoPagato = 0;
//			for (CartellaEsattorialeDTO cartelleItem : contribuentiItem.getCartelle()) {
//				if (cartelleItem.getStato() == Stato.CONCLUSA) {
//					conclusoPagato = conclusoPagato + cartelleItem.getImporto();
//					contribuentiItem.setConclusoPagato(conclusoPagato);
//				}
//			}
//		}
//		for (ContribuenteMetodiDTO contribuentiItem : contribuenti) {
//			Integer inContenzioso = 0;
//			for (CartellaEsattorialeDTO cartelleItem : contribuentiItem.getCartelle()) {
//				if (cartelleItem.getStato() == Stato.IN_CONTENZIOSO) {
//					inContenzioso = inContenzioso + cartelleItem.getImporto();
//					contribuentiItem.setInContenzioso(inContenzioso);
//				}
//			}
//			contribuentiItem.setCartelle(null);
//		}

		return contribuenti;
	}

}
