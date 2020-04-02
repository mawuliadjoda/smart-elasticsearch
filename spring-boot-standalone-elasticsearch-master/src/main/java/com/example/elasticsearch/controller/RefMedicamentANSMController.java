package com.example.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.lucene.search.FuzzyQuery;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.elasticsearch.model.RefMedicamentANSM;
import com.example.elasticsearch.util.ElascticSearchUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping("/rest")
public class RefMedicamentANSMController {

	private String index = "refMedicamentANSMs".toLowerCase();
	private String type = "refMedicamentANSM".toLowerCase();
	
	public static final String META_NAME_FIELD = "nom";
	private static String URL = "http://localhost:9200/refmedicamentansms/refmedicamentansm/_search";
	
	
    @Autowired
    Client client;
    
    
    @PostMapping("/refMedicamentANSMs")
//    public String create(@RequestBody RefMedicamentANSM refMedicamentANSM) throws IOException {
    public String create() throws IOException {
    	RefMedicamentANSM refMedicamentANSM = new RefMedicamentANSM();
    	refMedicamentANSM.setCis("60002283");
    	refMedicamentANSM.setCodeAtc("L02BG03");
    	refMedicamentANSM.setLibelleAtc("Anastrozole");
    	refMedicamentANSM.setNom("ANASTROZOLE ACCORD 1 mg, comprimé pelliculé");
    	refMedicamentANSM.setTitulaire("ACCORD HEALTHCARE FRANCE");
    	
    
        
        IndexResponse response = client.prepareIndex(index, type, refMedicamentANSM.getCis())
                .setSource(jsonBuilder()
                        .startObject()                       
                        .field("cis", refMedicamentANSM.getCis())
                        .field("nom", refMedicamentANSM.getNom())
                        .field("codeAtc", refMedicamentANSM.getCodeAtc())
                        .field("libelleAtc", refMedicamentANSM.getLibelleAtc())
                        .field("titulaire", refMedicamentANSM.getTitulaire())
                        
                        .endObject()
                )
                .get();
               System.out.println("response id:"+response.getId());
        return response.getResult().toString();
    }
    
    /**
     * http://localhost:8102/rest/refMedicamentANSMs/_search?fieldName=nom&fieldValue=ANASTROZOLE
     * @param fieldName
     * @param fieldValue
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    
    @GetMapping("/refMedicamentANSMs/_search")
    public List<RefMedicamentANSM> searchByName(@RequestParam(defaultValue = "nom") String fieldName, @RequestParam() String fieldValue ) throws JsonParseException, JsonMappingException, IOException {
    	List<RefMedicamentANSM> results = simpleSearchByName(fieldName, fieldValue);
    	if(results !=null && !results.isEmpty()) {
    		return results;
    	}
		return ElascticSearchUtil.getRefMedicamentANSMs(URL, fieldName, fieldValue);
    	
        
    }
    
    
    public List<RefMedicamentANSM> simpleSearchByName(final String fieldName, final String fieldValue) {
    	List<RefMedicamentANSM> results = new ArrayList<>();
    	
        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.QUERY_AND_FETCH)
                .setQuery(QueryBuilders.matchQuery(fieldName, fieldValue).minimumShouldMatch("75%"))
                .get()
                ;
                
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        
        searchHits.forEach(
        		  hit -> results.add(JSON.parseObject(hit.getSourceAsString(), RefMedicamentANSM.class)));

        return results;
    }
    
    @GetMapping("/refMedicamentANSMs")
    public List<RefMedicamentANSM> searchByName() {
    	List<RefMedicamentANSM> results = new ArrayList<>();
        SearchResponse response = client.prepareSearch(index)
                                .setTypes(type)
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchAllQuery())
                                
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        
        searchHits.forEach(
        		  hit -> results.add(JSON.parseObject(hit.getSourceAsString(), RefMedicamentANSM.class)));

        return results;

    }
    
    public SearchResponse findAllTeamsByNameAndFuzziness(String name) {
    	  QueryBuilder queryBuilder = QueryBuilders.fuzzyQuery("nom", name)
    	    .fuzziness(Fuzziness.ONE)
    	    .queryName("fuzzyTeamName");
    	 
    	  SearchResponse response = client.prepareSearch(index)
    	    .setQuery(queryBuilder)
    	    .setFrom(0)
    	    .setSize(200)
    	    .addSort("nom", SortOrder.ASC)
    	    .get();
    	 
    	  return response;
    	}
    
    private static QueryBuilder getTextFilter(String key, Object value, String predicate) {
    	  switch (predicate) {
    	    case "LIKE":
    	      return QueryBuilders.wildcardQuery(key, value.toString());
    	    case "UNLIKE":
    	      return QueryBuilders.boolQuery().mustNot(QueryBuilders.wildcardQuery(key, value.toString()));
    	    case "PREFIX":
    	      return QueryBuilders.prefixQuery(key, value.toString());
    	    case "UNPREFIX":
    	      return QueryBuilders.boolQuery().mustNot(QueryBuilders.prefixQuery(key, value.toString()));
    	    case "REGEXP":
    	      return QueryBuilders.regexpQuery(key, value.toString());
    	    case "UNREGEXP":
    	      return QueryBuilders.boolQuery().mustNot(QueryBuilders.regexpQuery(key, value.toString()));
    	    case "FUZZY":
    	      return QueryBuilders.fuzzyQuery(key, value.toString());
    	    case "UNFUZZY":
    	      return QueryBuilders.boolQuery().mustNot(QueryBuilders.fuzzyQuery(key, value.toString()));
    	    default:
    	      throw new IllegalArgumentException("predicate not supported in has step: " + predicate);
    	  }
    	}
    
//    private QueryBuilder getNameQuery(String searchTerm) {
//    	 //prefix name match
//    	 QueryBuilder namePrefixMatch = prefixQuery(META_NAME_FIELD,
//    	   searchTerm);
//    	 QueryBuilder namePhraseMatch = matchPhraseQuery(META_NAME_FIELD,
//    	   searchTerm);
//    	 QueryBuilder nameFuzzyQuery = fuzzyQuery(
//    	   META_NAME_FIELD, searchTerm);
//    	 QueryBuilder wildCardQuery = wildcardQuery(META_NAME_FIELD,
//    	   String.format("*%s*", searchTerm));
//    	 QueryBuilder nameQuery = boolQuery()
//    	   .should(namePrefixMatch)
//    	   .should(namePhraseMatch)
//    	   .should(nameFuzzyQuery)
//    	   .should(wildCardQuery);
//    	 return nameQuery;
//    	}
}
