package com.example.elasticsearch.controller;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.alibaba.fastjson.JSON;
import com.example.elasticsearch.model.User;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * This class is to demo how ElasticsearchTemplate can be used to Save/Retrieve
 */

@RestController
@RequestMapping("/rest/users")
public class UserController {

    @Autowired
    Client client;
    @PostMapping("/create")
//    public String create(@RequestBody User user) throws IOException {
    public String create() throws IOException {
    	User user = new User();
    	user.setUserId("1");
    	user.setName("ADJODA Koffi");
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("gender", "Male");
    	map.put("occupation", "CA");
    	map.put("hobby", "chess");
    	user.setUserSettings(map);
        
        IndexResponse response = client.prepareIndex("users", "employee", user.getUserId())
                .setSource(jsonBuilder()
                        .startObject()
                        .field("name", user.getName())
                        .field("userSettings", user.getUserSettings())
                        .endObject()
                )
                .get();
               System.out.println("response id:"+response.getId());
        return response.getResult().toString();
    }


    @GetMapping("/view/{id}")
    public Map<String, Object> view(@PathVariable final String id) {
        GetResponse getResponse = client.prepareGet("users", "employee", id).get();
        System.out.println(getResponse.getSource());


        return getResponse.getSource();
    }
    @GetMapping("/view/name/{field}")
    public List<Map<String,Object>> searchByName(@PathVariable final String field) {
    	List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        SearchResponse response = client.prepareSearch("users")
                                .setTypes("employee")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchQuery("name", field).minimumShouldMatch("75%"))
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        for(SearchHit searchHit : searchHits ) {
        	mapList.add(searchHit.getSource());
        }
        // map =   searchHits.get(0).getSource();
        return mapList;

    }
    
    @GetMapping("/all")
    public List<User> searchByName() {
//        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
    	List<User> results = new ArrayList<>();
        SearchResponse response = client.prepareSearch("users")
                                .setTypes("employee")
                                .setSearchType(SearchType.QUERY_AND_FETCH)
                                .setQuery(QueryBuilders.matchAllQuery())
                                
                                .get()
                                ;
        List<SearchHit> searchHits = Arrays.asList(response.getHits().getHits());
        
        searchHits.forEach(
        		  hit -> results.add(JSON.parseObject(hit.getSourceAsString(), User.class)));
        
        
//        for(SearchHit searchHit : searchHits ) {
//        	mapList.add(searchHit.getSource());
//        }
        // map =   searchHits.get(0).getSource();
        return results;

    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable final String id) throws IOException {

        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("users")
                .type("employee")
                .id(id)
                .doc(jsonBuilder()
                        .startObject()
                        .field("name", "Rajesh")
                        .endObject());
        try {
            UpdateResponse updateResponse = client.update(updateRequest).get();
            System.out.println(updateResponse.status());
            return updateResponse.status().toString();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
        return "Exception";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable final String id) {

        DeleteResponse deleteResponse = client.prepareDelete("users", "employee", id).get();

        System.out.println(deleteResponse.getResult().toString());
        return deleteResponse.getResult().toString();
    }
}