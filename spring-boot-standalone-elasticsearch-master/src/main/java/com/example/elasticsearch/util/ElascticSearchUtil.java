package com.example.elasticsearch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.example.elasticsearch.model.RefMedicamentANSM;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElascticSearchUtil {

	private static final String PREFIX = "PREFIX";
	private static final String FUZZY = "FUZZY";
	private static final String UTF_8 = "UTF-8";
	private static final String FAILED_HTTP_ERROR_CODE = "Failed : HTTP error code : ";
	private static final String _SOURCE = "_source";
	private static final String HITS2 = "hits";
	
	public static List<RefMedicamentANSM>  getRefMedicamentANSMs (String url, String fieldName, String fieldValue) throws JsonParseException, JsonMappingException, IOException {
		
		List<String> results = getResult(url, fieldName, fieldValue, PREFIX);

		
		List<Object> hitsMap = getHits(results);
		if(hitsMap == null || hitsMap.isEmpty()) {
			System.out.println("------------results is null-----------");
			results = getResult(url, fieldName, fieldValue, FUZZY);
			hitsMap = getHits(results);
		}
		
		List<RefMedicamentANSM> refMedicamentANSMs = getRefMedicamentANSMs(hitsMap);
	
		return refMedicamentANSMs;
	}
	
	public static List<Object>  getSearchHit (String url, String fieldName, String fieldValue) throws JsonParseException, JsonMappingException, IOException {
		
		List<String> results = getResult(url, fieldName, fieldValue, PREFIX);
		List<Object> hitsMap = getHits(results);
		if(hitsMap == null || hitsMap.isEmpty()) {
			System.out.println("------------results is null-----------");
			results = getResult(url, fieldName, fieldValue, FUZZY);
			hitsMap = getHits(results);
		}
		
		return  getHits(results);
	
	}
	
	public static List<RefMedicamentANSM> getRefMedicamentANSMs(List<Object> hitsMap){
		List<RefMedicamentANSM> refMedicamentANSMs = new ArrayList<>();
		if(hitsMap !=null && !hitsMap.isEmpty()) {
			for(Object o : hitsMap) {
				Map<String, Object> sourceMap = (Map<String, Object>) o;
				Map<String, Object> source = (Map<String, Object>) sourceMap.get(_SOURCE);
				
				RefMedicamentANSM refMedicamentANSM = new RefMedicamentANSM(source);
				refMedicamentANSMs.add(refMedicamentANSM);
			}
		}	
		return refMedicamentANSMs;
	}
	public static List<Object> getHits(List<String> results) throws JsonParseException, JsonMappingException, IOException {
		List<Object> hitsMap = null;
		if(results !=null && !results.isEmpty()) {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> map = mapper.readValue(results.get(0), Map.class);
			Object hits = map.get(HITS2);
			
			if(hits instanceof Map) {
				Map myMap = (Map<String, Object>) hits;
				hitsMap =  (List<Object>) myMap.get(HITS2);
				
			}
		}
		return hitsMap;
	}
	
	public static List<String> getResult(String uri,String fieldName, String searchField, String searchType) {
		List<String> results = new ArrayList<String>();

			try {

				URL url = new URL(uri);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				String body = null;
				 
				body = getBody(fieldName, searchField, searchType);
				System.out.println("------body---------------");
				System.out.println(body);
				System.out.println("---------end--------------");
				
				OutputStream os = conn.getOutputStream();
				os.write(body.getBytes());
				os.flush();

				HttpStatus httpStatus = HttpStatus.valueOf(conn.getResponseCode());
				if (!httpStatus.is2xxSuccessful()) {
					throw new RuntimeException(FAILED_HTTP_ERROR_CODE + conn.getResponseCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream()), UTF_8));;

				String output = null;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					results.add(output);
					
					System.out.println(output);
				}

				conn.disconnect();

			} catch (MalformedURLException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();

			}
			return results;

		
	}

	private static String getBody(String fieldName, String searchField, String searchType) {
		switch (searchType) {
		case FUZZY:
			return "{\r\n" + 
					"    \"query\": {\r\n" + 
					"        \"match\" : {\r\n" + 
					"            \""+fieldName+"\" : {\r\n" + 
					"                \"query\" : \""+ searchField   +"\",\r\n" + 
					"                \"fuzziness\": \"AUTO\"\r\n" + 
					"            }\r\n" + 
					"        }\r\n" + 
					"    }\r\n" + 
					"}";
		case PREFIX:
			return "{\r\n" + 
					"    \"query\": {\r\n" + 
					"        \"match_phrase_prefix\" : {\r\n" + 
					"          \""+ fieldName+ "\" : \""+ searchField   +"\"\r\n" + 
					"        }\r\n" + 
					"    }\r\n" + 
					"}";

		default:
			break;
		}
		
		return null;
	}
}
