package it.prova.cartellaesattoriale.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.model.Stato;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartellaEsattorialeDTO {

	private Long id;

	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;

	@NotNull(message = "{importo.notnull}")
	@Min(1)
	private Integer importo;

	@NotNull(message = "{stato.notblank}")
	private Stato stato;

	@JsonIgnoreProperties(value = { "cartelle" })
	@NotNull(message = "{contribuente.notnull}")
	private ContribuenteDTO contribuente;

	public CartellaEsattorialeDTO() {
		// TODO Auto-generated constructor stub
	}

	public CartellaEsattorialeDTO(Long id, String descrizione, Integer importo, Stato stato) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
		this.stato = stato;
	}

	public CartellaEsattorialeDTO(Long id, String descrizione, Integer importo, Stato stato,
			ContribuenteDTO contribuente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.importo = importo;
		this.stato = stato;
		this.contribuente = contribuente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getImporto() {
		return importo;
	}

	public void setImporto(Integer importo) {
		this.importo = importo;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public ContribuenteDTO getContribuente() {
		return contribuente;
	}

	public void setContribuente(ContribuenteDTO contribuente) {
		this.contribuente = contribuente;
	}

	// ##########################################################àà
	public CartellaEsattoriale buildCartellaEsattorialeModel() {
		return new CartellaEsattoriale(this.id, this.descrizione, this.importo, this.stato,
				this.contribuente.buildContribuenteModel());
	}

	public static CartellaEsattorialeDTO buildCartellaEsattorialeDTOFromModel(CartellaEsattoriale cartellaModel,
			boolean includeRegisti) {
		CartellaEsattorialeDTO result = new CartellaEsattorialeDTO(cartellaModel.getId(),
				cartellaModel.getDescrizione(), cartellaModel.getImporto(), cartellaModel.getStato());

		if (includeRegisti)
			result.setContribuente(
					ContribuenteDTO.buildContribuenteDTOFromModel(cartellaModel.getContribuente(), false));

		return result;
	}

	public static List<CartellaEsattorialeDTO> createCartellaEsattorialeDTOListFromModelList(
			List<CartellaEsattoriale> modelListInput, boolean includeRegisti) {
		return modelListInput.stream().map(cartellaEntity -> {
			return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartellaEntity, includeRegisti);
		}).collect(Collectors.toList());
	}

	public static Set<CartellaEsattorialeDTO> createCartellaEsattorialeDTOSetFromModelSet(
			Set<CartellaEsattoriale> modelListInput, boolean includeRegisti) {
		return modelListInput.stream().map(cartellaEntity -> {
			return CartellaEsattorialeDTO.buildCartellaEsattorialeDTOFromModel(cartellaEntity, includeRegisti);
		}).collect(Collectors.toSet());
	}
}
