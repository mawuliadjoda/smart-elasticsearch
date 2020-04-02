package com.example.elasticsearch.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * https://ansm.sante.fr/searchengine/general_search?SearchText=NS&rubrique=Listes+et+r%C3%A9pertoires++-+M%C3%A9dicaments
 * @author koffi
 *
 */

@JsonPropertyOrder("_source")
public class RefMedicamentANSM {

	private String cis;
	private String nom;
	// ATC: classification Anatomique, Thérapeutique et Chimique
	private String libelleAtc;
	private String codeAtc;
	private String titulaire;
	
	
	public RefMedicamentANSM() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RefMedicamentANSM(Map<String, Object> source) {
		this.cis = source.get("cis").toString();
		this.nom = source.get("nom").toString();
		this.libelleAtc = source.get("libelleAtc").toString();
		this.codeAtc = source.get("codeAtc").toString();
		this.titulaire = source.get("titulaire").toString();
	}
	
	public String getCis() {
		return cis;
	}
	public void setCis(String cis) {
		this.cis = cis;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getLibelleAtc() {
		return libelleAtc;
	}
	public void setLibelleAtc(String libelleAtc) {
		this.libelleAtc = libelleAtc;
	}
	public String getCodeAtc() {
		return codeAtc;
	}
	public void setCodeAtc(String codeAtc) {
		this.codeAtc = codeAtc;
	}
	public String getTitulaire() {
		return titulaire;
	}
	public void setTitulaire(String titulaire) {
		this.titulaire = titulaire;
	}
	
	
}
