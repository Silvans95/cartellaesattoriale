package it.prova.cartellaesattoriale;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.model.Stato;
import it.prova.cartellaesattoriale.service.CartellaEsattorialeService;
import it.prova.cartellaesattoriale.service.ContribuenteService;

@SpringBootApplication
public class CartellaesattorialeApplication implements CommandLineRunner {

	@Autowired
	private ContribuenteService contribuenteService;
	@Autowired
	private CartellaEsattorialeService cartellaService;

	public static void main(String[] args) {
		SpringApplication.run(CartellaesattorialeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String paolino = "Paolino";
		String paperino = "Paperino";
		Contribuente contribuentePaperino = contribuenteService.findByNomeAndCognome(paolino, paperino);

		if (contribuentePaperino == null) {
			contribuentePaperino = new Contribuente("Paolino", "Paperino",
					new SimpleDateFormat("dd/MM/yyyy").parse("18/12/1946"), "pappaol", "paperinopoli");
			contribuenteService.inserisciNuovo(contribuentePaperino);
		}

		CartellaEsattoriale papero = new CartellaEsattoriale("sfigato", 10000, Stato.IN_CONTENZIOSO,
				contribuentePaperino);
		if (cartellaService.findByDescrizioneAndImporto("sfigato", 10000) == null)
			cartellaService.inserisciNuovo(papero);

		// ##################################################################

		String mickey = "Mickey";
		String topolino = "Topolino";
		Contribuente contribuenteTopolino = contribuenteService.findByNomeAndCognome(mickey, topolino);

		if (contribuenteTopolino == null) {
			contribuenteTopolino = new Contribuente("Mickey", "Topolino",
					new SimpleDateFormat("dd/MM/yyyy").parse("18/12/1946"), "micktop", "topolinia");
			contribuenteService.inserisciNuovo(contribuenteTopolino);
		}

		CartellaEsattoriale topo = new CartellaEsattoriale("fortunato", 20, Stato.CONCLUSA, contribuenteTopolino);
		if (cartellaService.findByDescrizioneAndImporto("fortunato", 20) == null)
			cartellaService.inserisciNuovo(topo);

	}

}
