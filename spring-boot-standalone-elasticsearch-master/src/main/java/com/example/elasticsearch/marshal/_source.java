package com.example.elasticsearch.marshal;

public class _source
{
    private String cis;

    private String nom;

    private String codeAtc;

    private String libelleAtc;

    private String titulaire;

    public void setCis(String cis){
        this.cis = cis;
    }
    public String getCis(){
        return this.cis;
    }
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return this.nom;
    }
    public void setCodeAtc(String codeAtc){
        this.codeAtc = codeAtc;
    }
    public String getCodeAtc(){
        return this.codeAtc;
    }
    public void setLibelleAtc(String libelleAtc){
        this.libelleAtc = libelleAtc;
    }
    public String getLibelleAtc(){
        return this.libelleAtc;
    }
    public void setTitulaire(String titulaire){
        this.titulaire = titulaire;
    }
    public String getTitulaire(){
        return this.titulaire;
    }
}