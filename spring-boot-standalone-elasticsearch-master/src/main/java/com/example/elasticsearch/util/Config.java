package com.example.elasticsearch.util;

import java.util.HashMap;
import java.util.Map;

public class Config {
	
	public static final String OBS = "obs";
	public static final String TYPE_MED = "typeMED";
	public static final String INAM_PBR = "inamPBR";
	public static final String PBR = "pbr";
	public static final String PPC = "ppc";
	public static final String PRESENTATION = "presentation";
	public static final String FORME_DOSAGE = "formeDosage";
	public static final String CLASSE_THERAPEUTIQUE = "classeTherapeutique";
	public static final String DCI = "dci";
	public static final String NOM = "nom";
	public static final String CATEG = "categ";
	public static final String CIS = "cis";
	
	public static final String REF_MEDIC_TOGO = "ref_medicament_togo/ref_medic_togo-converted.xlsx";
	public static final String REF_MEDIC_2_TOGO = "ref_medicament_togo/ref_medic_togo_2-converted.xlsx";
	
	public static final Map<String, Integer> MAP_REF_MEDIC_TG_POSITION = new HashMap<String, Integer>();
	
	static {
		MAP_REF_MEDIC_TG_POSITION.put(CIS, 0);
		MAP_REF_MEDIC_TG_POSITION.put(DCI, 1);
		MAP_REF_MEDIC_TG_POSITION.put(NOM, 2);
		MAP_REF_MEDIC_TG_POSITION.put(CLASSE_THERAPEUTIQUE, 3);
		MAP_REF_MEDIC_TG_POSITION.put(FORME_DOSAGE, 4);
		MAP_REF_MEDIC_TG_POSITION.put(INAM_PBR, 5);
		MAP_REF_MEDIC_TG_POSITION.put(PRESENTATION, 6);
		MAP_REF_MEDIC_TG_POSITION.put(PPC, 7);
		MAP_REF_MEDIC_TG_POSITION.put(PBR, 8);
		MAP_REF_MEDIC_TG_POSITION.put(OBS, 9);
		
		MAP_REF_MEDIC_TG_POSITION.put(TYPE_MED, 10);
		MAP_REF_MEDIC_TG_POSITION.put(CATEG, 111);
		
		
		
	}
}
