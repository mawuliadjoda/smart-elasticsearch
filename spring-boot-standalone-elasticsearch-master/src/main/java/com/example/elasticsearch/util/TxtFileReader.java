package com.example.elasticsearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.example.elasticsearch.model.RefMedicamentBDDF;

public class TxtFileReader {
	
	private static String FILE_NAME = "C:\\Users\\koffi\\Documents\\dev\\REF_MEDICAMENT\\TXT_FILE\\ref_medic.txt";
	public static void main(String[] args) {

      TxtFileReader fileReader = new TxtFileReader();
      List<RefMedicamentBDDF>  refMedicamentBDDFs = fileReader.readFile(FILE_NAME);

    }
	
	public List<RefMedicamentBDDF> readFile(String filePath){

		List<RefMedicamentBDDF> refMedicamentBDDFs = new ArrayList<>();
	        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.ISO_8859_1)) {

	            // read line by line
	            String line;
	            while ((line = br.readLine()) != null) {
	            	String[] tab = line.split("\t");
	            	RefMedicamentBDDF refMedicamentBDDF = new RefMedicamentBDDF();
	            	refMedicamentBDDF.setCis(tab[MedicamentEnteRefEnum.CIS.getPosition()]);
	            	refMedicamentBDDF.setNom(tab[MedicamentEnteRefEnum.NOM.getPosition()]);
	            	refMedicamentBDDF.setFormePharmaceutique(tab[MedicamentEnteRefEnum.FORME_PHARMACEUTIQUE.getPosition()]);
	                refMedicamentBDDF.setVoiesAdmnistration(tab[MedicamentEnteRefEnum.VOIES_ADMINISTRATION.getPosition()]);
	            	refMedicamentBDDF.setStatusAutorisation(tab[MedicamentEnteRefEnum.STATUS_AUTORISATION.getPosition()]);
	                refMedicamentBDDF.setTypeAutorisation(tab[MedicamentEnteRefEnum.TYPE_AUTORISATION.getPosition()]);
	                refMedicamentBDDF.setEtatCommercialisation(tab[MedicamentEnteRefEnum.ETAT_COMMERCIALISATION.getPosition()]);
	                refMedicamentBDDF.setDateMiseSurLeMarche(tab[MedicamentEnteRefEnum.DATE_MISE_SUR_MARCHE.getPosition()]);
	                refMedicamentBDDF.setStatutBdm(tab[MedicamentEnteRefEnum.STATUT_BDM.getPosition()]);
	                refMedicamentBDDF.setNumeroAutorisationEuropeen(tab[MedicamentEnteRefEnum.NUMERO_AUTORISATION_EUROPEEN.getPosition()]);
	                refMedicamentBDDF.setTitulaire(tab[MedicamentEnteRefEnum.TITULAIRE.getPosition()]);
	                refMedicamentBDDF.setSurveillanceRenforcee(tab[MedicamentEnteRefEnum.SURVEILLANCE_RENFORCE.getPosition()]);
	                
	                refMedicamentBDDFs.add(refMedicamentBDDF);
	                
	            	//System.out.println(refMedicamentBDDFs);
	            }

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
			return refMedicamentBDDFs;

	}
}
