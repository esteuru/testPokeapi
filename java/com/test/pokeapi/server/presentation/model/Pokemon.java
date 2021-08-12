package com.test.pokeapi.server.presentation.model;
import org.json.JSONObject;

public class Pokemon{
	
	private Integer weight;
	private Integer height;
	private Integer baseExperience;
	private String name;

	
	
	
	
	
	public Pokemon(JSONObject json) {
		
		if(json!=null && json.has("weight") && json.has("height") && json.has("base_experience") && json.has("name") ) {
			Integer weight = json.getInt("weight");
			Integer height = json.getInt("height");
			Integer baseEperience = json.getInt("base_experience");
			String name = json.getString("name");
		
			this.weight = weight;
			this.height = height;
			this.baseExperience = baseEperience;
			this.name = name;
			
		}
		
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getBaseExperience() {
		return baseExperience;
	}

	public void setBaseExperience(Integer baseExperience) {
		this.baseExperience = baseExperience;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
