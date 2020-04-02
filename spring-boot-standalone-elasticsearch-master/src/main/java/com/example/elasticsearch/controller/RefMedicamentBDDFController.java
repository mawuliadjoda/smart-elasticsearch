package com.example.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.elasticsearch.model.RefMedicamentBDDF;
import com.example.elasticsearch.model.User;
import com.example.elasticsearch.util.ElascticSearchUtil;
import com.example.elasticsearch.util.TxtFileReader;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/rest")
public class RefMedicamentBDDFController {
	private static String index = "RefMedicamentBDDFs".toLowerCase();
	private static String type = "RefMedicamentBDDF".toLowerCase();

	public static final String META_NAME_FIELD = "nom";
	private static String URL = "http://localhost:9200/" + index + "/" + type + "/_search";

//	private static String FILE_NAME = "C:\\Users\\koffi\\Documents\\dev\\REF_MEDICAMENT\\TXT_FILE\\ref_medic.txt";
	private static String FILE_NAME = "C:\\Users\\koffi\\Documents\\dev\\REF_MEDICAMENT\\TXT_FILE\\CIS_bdpm.txt";
	@Autowired
	Client client;

	@PostMapping("/refMedicamentBDDFs")
	public void saveAll() throws IOException {
		TxtFileReader fileReader = new TxtFileReader();
		List<RefMedicamentBDDF> refMedicamentBDDFs = fileReader.readFile(FILE_NAME);

		System.out.println("start ...");
		for (RefMedicamentBDDF refMedicamentBDDF : refMedicamentBDDFs) {
			refMedicamentBDDF.setType("BDDF");
			client.prepareIndex(index, type, refMedicamentBDDF.getCis())
					.setSource(jsonBuilder().startObject().field("cis", refMedicamentBDDF.getCis())
							.field("nom", refMedicamentBDDF.getNom())
							.field("titulaire", refMedicamentBDDF.getTitulaire())
							.field("typeM", refMedicamentBDDF.getType()).endObject())
					.get();
		}
		System.out.println("end ...");

	}

    @GetMapping("/refMedicamentBDDFs")
    public int searchByName() {

    	List<User> results = new ArrayList<>();
        SearchResponse response = client.prepareSearch(index)
                                .setTypes(type)
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchAllQuery())
                                
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        
        System.out.println("---------searchHits.size()-------"+ searchHits.size());
        return searchHits.size();

    }
    
	 /**
     * http://localhost:8102/rest/refMedicamentBDDFs/_search?fieldName=nom&fieldValue=ANASTROZOLE RATIOPHARM
     * curl ne marche pas avec /_search?fieldName=nom&fieldValue=ANASTROZOLE RATIOPHARM
     * 
     * elastic url: 
     * http://localhost:9200/refmedicamentbddfs/refmedicamentbddf/_search
     * {
		    "query": {
		        "match_phrase_prefix" : {
		          "nom" : "ANASTROZOLE RATIOPHARM"
		        }
		    }
		}
     * @param fieldName
     * @param fieldValue
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    @GetMapping("/refMedicamentBDDFs/_search")
    public List<Object> searchByName(@RequestParam(name = "fieldName", required = true) String fieldName, 
    								 @RequestParam(name = "fieldValue", required = true) String fieldValue ) throws JsonParseException, JsonMappingException, IOException {
    
    	System.out.println(URL);
		return ElascticSearchUtil.getSearchHit(URL, fieldName, fieldValue);
    	
        
    }
}
