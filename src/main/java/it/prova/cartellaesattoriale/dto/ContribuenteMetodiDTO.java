package it.prova.cartellaesattoriale.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.cartellaesattoriale.model.Contribuente;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContribuenteMetodiDTO {

	private Long id;

	@NotBlank(message = "{nome.notblank}")
	private String nome;

	@NotBlank(message = "{cognome.notblank}")
	private String cognome;

	@NotNull(message = "{dataDiNascita.notnull}")
	private Date dataDiNascita;

	@NotBlank(message = "{codiceFiscale.notblank}")
	private String codiceFiscale;

	@NotBlank(message = "{indirizzo.notblank}")
	private String indirizzo;
	
	private Integer totale;
	private Integer conclusoPagato;
	private Integer inContenzioso;

	@JsonIgnoreProperties(value = { "contribuente" })
	private Set<CartellaEsattorialeDTO> cartelle = new HashSet<CartellaEsattorialeDTO>(0);

	
	public ContribuenteMetodiDTO() {
		// TODO Auto-generated constructor stub
	}

	public ContribuenteMetodiDTO(Long id, String nome, String cognome, Date dataDiNascita, String codiceFiscale,
			String indirizzo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
	}

	public ContribuenteMetodiDTO(String nome, String cognome, Date dataDiNascita, String codiceFiscale,
			String indirizzo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
	}

	public ContribuenteMetodiDTO(Long id, String nome, String cognome, Date dataDiNascita, String codiceFiscale,
			String indirizzo, Set<CartellaEsattorialeDTO> cartelle) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.codiceFiscale = codiceFiscale;
		this.indirizzo = indirizzo;
		this.cartelle = cartelle;
	}

	public Integer getTotale() {
		return totale;
	}

	public void setTotale(Integer totale) {
		this.totale = totale;
	}

	public Integer getConclusoPagato() {
		return conclusoPagato;
	}

	public void setConclusoPagato(Integer conclusoPagato) {
		this.conclusoPagato = conclusoPagato;
	}

	public Integer getInContenzioso() {
		return inContenzioso;
	}

	public void setInContenzioso(Integer inContenzioso) {
		this.inContenzioso = inContenzioso;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Set<CartellaEsattorialeDTO> getCartelle() {
		return cartelle;
	}

	public void setCartelle(Set<CartellaEsattorialeDTO> cartelle) {
		this.cartelle = cartelle;
	}

	// ##################################################
	public Contribuente buildContribuenteModel() {
		return new Contribuente(this.id, this.nome, this.cognome, this.dataDiNascita, this.codiceFiscale,
				this.indirizzo);
	}

	public static ContribuenteMetodiDTO buildContribuenteDTOFromModel(Contribuente contribuenteModel,
			boolean includeCartelle) {
		ContribuenteMetodiDTO result = new ContribuenteMetodiDTO(contribuenteModel.getId(), contribuenteModel.getNome(),
				contribuenteModel.getCognome(), contribuenteModel.getDataDiNascita(),
				contribuenteModel.getCodiceFiscale(), contribuenteModel.getIndirizzo());
		if (includeCartelle)
			result.setCartelle(CartellaEsattorialeDTO
					.createCartellaEsattorialeDTOSetFromModelSet(contribuenteModel.getCartelle(), false));
		return result;
	}

	public static List<ContribuenteMetodiDTO> createContribuenteDTOListFromModelList(List<Contribuente> modelListInput,
			boolean includeCartelle) {
		return modelListInput.stream().map(contribuenteEntity -> {
			ContribuenteMetodiDTO result = ContribuenteMetodiDTO.buildContribuenteDTOFromModel(contribuenteEntity,
					includeCartelle);
			if (includeCartelle)
				result.setCartelle(CartellaEsattorialeDTO
						.createCartellaEsattorialeDTOSetFromModelSet(contribuenteEntity.getCartelle(), false));
			return result;
		}).collect(Collectors.toList());
	}

}
