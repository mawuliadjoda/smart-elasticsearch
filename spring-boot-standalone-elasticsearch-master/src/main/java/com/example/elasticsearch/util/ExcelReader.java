package com.example.elasticsearch.util;

import static com.example.elasticsearch.util.Config.CATEG;
import static com.example.elasticsearch.util.Config.CIS;
import static com.example.elasticsearch.util.Config.CLASSE_THERAPEUTIQUE;
import static com.example.elasticsearch.util.Config.DCI;
import static com.example.elasticsearch.util.Config.FORME_DOSAGE;
import static com.example.elasticsearch.util.Config.INAM_PBR;
import static com.example.elasticsearch.util.Config.NOM;
import static com.example.elasticsearch.util.Config.OBS;
import static com.example.elasticsearch.util.Config.PBR;
import static com.example.elasticsearch.util.Config.PPC;
import static com.example.elasticsearch.util.Config.PRESENTATION;
import static com.example.elasticsearch.util.Config.TYPE_MED;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.elasticsearch.model.RefMedicamentTG;



public class ExcelReader {

//    private static final String FILE_NAME = "C:\\Users\\koffi\\Documents\\dev\\REF_MEDICAMENT\\ref-des-medicaments-cnops-2014.xlsx";

//    private static final String FILE_NAME_ANSM= "C:\\Users\\koffi\\Documents\\dev\\REF_MEDICAMENT\\ANSM\\ref_ansm_test.xlsx";
    
    private static final int EXPECTED_CIS_LENGTH = 8;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);
    
    public static void main(String[] args) {

    	ExcelReader excelReader = new ExcelReader();
//    	List<RefMedicament> refMedicaments = excelReader.readMedicamentsFromFile(new File(FILE_NAME));
//    	System.out.println(refMedicaments);
//    	
//    	List<RefMedicamentANSM> refMedicamentANSMs = excelReader.readMedicamentsANSMFromFile(new File(FILE_NAME_ANSM));
//    	System.out.println(refMedicamentANSMs);
    	
    	// List<RefMedicamentTG> list = excelReader.readRefMedicamentTG(REF_MEDIC_TOGO);
    	// System.out.println(list);
 
    }

    
   
    
    // Revoir le traitement des doublons
    public Collection<RefMedicamentTG> readRefMedicamentTG(String fileName){
    	Resource resource = new ClassPathResource(fileName);
      	 //List<RefMedicamentTG> refMedicamentTGs = new ArrayList<RefMedicamentTG>();
      	 Map<String, RefMedicamentTG> map = new HashMap<String, RefMedicamentTG>();
      	 List<String> doublons = new ArrayList<String>();
      	 try {

               FileInputStream excelFile = new FileInputStream(resource.getFile());
               Workbook workbook = new XSSFWorkbook(excelFile);
               Sheet datatypeSheet = workbook.getSheetAt(0);

               for(int i=1;i<datatypeSheet.getPhysicalNumberOfRows() ;i++) {
                   // Test tempStudent = new Test();

                   Row row = datatypeSheet.getRow(i);
                   RefMedicamentTG refMedicamentTG = new RefMedicamentTG();
                   refMedicamentTG.setCis(buildCis(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(CIS))));
                   refMedicamentTG.setCateg(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(CATEG)));
                   refMedicamentTG.setNom(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(NOM)));
                   refMedicamentTG.setDci(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(DCI)));
                   refMedicamentTG.setClasseTherapeutique(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(CLASSE_THERAPEUTIQUE)));
                   refMedicamentTG.setFormeDosage(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(FORME_DOSAGE)));
                   refMedicamentTG.setPresentation(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(PRESENTATION)));
                   refMedicamentTG.setPpc(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(PPC)));
                   refMedicamentTG.setPbr(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(PBR)));
                   refMedicamentTG.setInamPBR(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(INAM_PBR)));
                   refMedicamentTG.setTypeMED(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(TYPE_MED)));
                   refMedicamentTG.setObs(getValue(row, Config.MAP_REF_MEDIC_TG_POSITION.get(OBS)));
                   
                   // Revoir le traitement des doublons
                   if(!map.containsKey(refMedicamentTG.getCis())) {
                	   map.put(refMedicamentTG.getCis(), refMedicamentTG);
                   }else {
                	   refMedicamentTG.setCis(refMedicamentTG.getCis() + "D");
                	   map.put(refMedicamentTG.getCis(), refMedicamentTG);
                	   doublons.add(refMedicamentTG.getCis());
                   }
                   
                   
                   // refMedicamentTGs.add(refMedicamentTG);
               }
              
       
           } catch (FileNotFoundException e) {
        	   LOGGER.error("", e);
           } catch (IOException e) {
        	   LOGGER.error("", e);
           }
      	 System.out.println(doublons);
      	 return map.values();

      }
   
    private String buildCis(String cis) {
    	return (cis.length() < EXPECTED_CIS_LENGTH) ? getSuffix(cis.length()) + cis : cis;
    }
    
    private String getSuffix(int cisLenght) {
    	if(EXPECTED_CIS_LENGTH > cisLenght) {
    		StringBuilder builder = new StringBuilder();
    		for(int i=0; i < (EXPECTED_CIS_LENGTH-cisLenght); i++) {
        		builder.append("0");
        	}
    		return builder.toString();
    	}
    	return "";
    }
    
    // always get string value == > setCellType
    String getValue(Row row, int position) {
    	Cell cell = row.getCell(position);
    	if(cell != null) {
    		cell.setCellType(Cell.CELL_TYPE_STRING);
        	return getCellValue(row.getCell(position)).trim();
    	}
    	System.out.println("----------position vide:" + position);
    	return "";
    }
    private  String getCellValue(Cell cell){
    	
        switch (cell.getCellTypeEnum()) {
            case STRING:
            	return cell.getStringCellValue().trim();
            	
            case NUMERIC: 
            	return String.valueOf(cell.getNumericCellValue());
            	
            case BOOLEAN: 
            	return String.valueOf(cell.getBooleanCellValue());            
            
        }
        return null;
    }
}
