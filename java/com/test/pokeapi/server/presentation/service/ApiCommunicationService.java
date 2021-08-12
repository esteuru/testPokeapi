package com.test.pokeapi.server.presentation.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.test.pokeapi.server.presentation.controller.RedPokemonsController;
import com.test.pokeapi.server.presentation.model.Pokemon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiCommunicationService {
	
	
	String urlFound = "";
	
	private void getUrl(URL url, String arrayKey, Map<String,String> valuesToFind){
		
		try {

	        JSONArray array = connectToApi(url).getJSONArray(arrayKey);
	        
	        for(int n = 0; n < array.length(); n++)
	        {
	            JSONObject arrayObject = array.getJSONObject(n);
	            
	            if(valuesToFind!= null) {
	            	valuesToFind.forEach((k,v) -> {
		            	if(arrayObject.get(k).equals(v)) {
		            		urlFound = (String) arrayObject.get("url");
		            	}
		            });
		            if(!urlFound.isEmpty()) {
		            	break;
		            }
	            }
	            
	        }
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
	
	public List<String> getPokemonNames(URL url, String arrayKey){
		log.info("Getting red pokemons names");
		JSONArray array = connectToApi(url).getJSONArray(arrayKey);
		
		List<String> names = StreamSupport.stream(array.spliterator(), false)
		.map(name -> (JSONObject) name)
		.map(name -> (String) name.get("name"))
		.collect(Collectors.toList());
		
		
		return names;
	}
	
	public List<Pokemon> getPokemons(){
		List<Pokemon> pokeList = new ArrayList<>();
		log.info("Getting url for red pokemons");
		URL url = null;
		try {
			url = new URL("https://pokeapi.co/api/v2/pokemon-color");
			Map<String,String> values = new HashMap<>();
		    values.put("name", "red");
		    getUrl(url, "results", values );
		    
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		URL red = null;
		try {
			red = new URL(urlFound);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

        List<String> pokemonNames = getPokemonNames(red, "pokemon_species");
        log.info("Getting detailed info for pokemons");
		for(String name : pokemonNames) {
			try {
				Pokemon pokemon = new Pokemon(connectToApi(new URL("https://pokeapi.co/api/v2/pokemon/" + name)));
				if(!StringUtils.isEmpty(pokemon.getName())) {
					pokeList.add(pokemon);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return pokeList;
	}
	
	public JSONObject connectToApi(URL url) {
		JSONObject object = null;
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = in.readLine()) != null) {
	        	response.append(inputLine);
	        }
	        in.close();
	        object = new JSONObject(response.toString());
	        
		} catch(Exception e) {
		}
		
		return object;
	}

}
