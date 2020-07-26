package com.example.elasticsearch.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class RefMedicamentTG implements Serializable {

	@NotNull
	// Code Identifiant de Spécialité
	private String cis;
	// nom international
	@NotNull
	private String dci;
	
	@NotNull
	private String nom;
	private String classeTherapeutique;
	private String formeDosage;
	private String inamPBR;
	private String presentation;
	private String ppc;
	private String pbr;
	private String obs;
	
	private String typeMED;	
	private String categ;
	private String type;
	
	public String getCis() {
		return cis;
	}
	public void setCis(String cis) {
		this.cis = cis;
	}
	public String getCateg() {
		return categ;
	}
	public void setCateg(String categ) {
		this.categ = categ;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDci() {
		return dci;
	}
	public void setDci(String dci) {
		this.dci = dci;
	}
	public String getClasseTherapeutique() {
		return classeTherapeutique;
	}
	public void setClasseTherapeutique(String classeTherapeutique) {
		this.classeTherapeutique = classeTherapeutique;
	}
	public String getFormeDosage() {
		return formeDosage;
	}
	public void setFormeDosage(String formeDosage) {
		this.formeDosage = formeDosage;
	}
	public String getPresentation() {
		return presentation;
	}
	public void setPresentation(String presentation) {
		this.presentation = presentation;
	}
	
	public String getPbr() {
		return pbr;
	}
	public void setPbr(String pbr) {
		this.pbr = pbr;
	}
	public String getInamPBR() {
		return inamPBR;
	}
	public void setInamPBR(String inamPBR) {
		this.inamPBR = inamPBR;
	}
	public String getTypeMED() {
		return typeMED;
	}
	public void setTypeMED(String typeMED) {
		this.typeMED = typeMED;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getPpc() {
		return ppc;
	}
	public void setPpc(String ppc) {
		this.ppc = ppc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
