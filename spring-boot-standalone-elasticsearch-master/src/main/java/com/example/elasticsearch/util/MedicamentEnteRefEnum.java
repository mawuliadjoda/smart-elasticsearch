package com.example.elasticsearch.util;

public enum MedicamentEnteRefEnum {
	CIS("CIS", "cis", 0, "String"), 
	NOM("NOM", "nom", 1, "String"), 
	FORME_PHARMACEUTIQUE("FORME_PHARMACEUTIQUE","formePharmaceutique", 2, "String"),
	
	VOIES_ADMINISTRATION("VOIES_ADMINISTRATION", "voiesAdmnistration", 3, "String"), 
	STATUS_AUTORISATION("STATUS_AUTORISATION", "statusAutorisation", 4, "String"), 
	TYPE_AUTORISATION("TYPE_AUTORISATION","typeAutorisation", 5, "String"),
	
	ETAT_COMMERCIALISATION("ETAT_COMMERCIALISATION", "etatCommercialisation", 6, "String"), 
	DATE_MISE_SUR_MARCHE("DATE_MISE_SUR_MARCHE", "dateMiseSurLeMarche", 7, "String"), 
	STATUT_BDM("STATUT_BDM","StatutBdm", 8, "String"),
	
	NUMERO_AUTORISATION_EUROPEEN("NUMERO_AUTORISATION_EUROPEEN", "numeroAutorisationEuropeen", 9, "String"), 
	TITULAIRE("TITULAIRE", "titulaire", 10, "array"), 
	SURVEILLANCE_RENFORCE("SURVEILLANCE_RENFORCE","surveillanceRenforcee", 11, "String") ;
	
	private String code;
	private String libelle;
	private int position;
	private String type;
	

	private MedicamentEnteRefEnum(String code, String libelle, int position, String type) {
		this.code = code;
		this.libelle = libelle;
		this.position = position;
		this.type = type;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
