package com.test.pokeapi.server.presentation.controller;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;
import com.test.pokeapi.server.presentation.model.Pokemon;
import com.test.pokeapi.server.presentation.service.ApiCommunicationService;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class RedPokemonsController {
	
	@RequestMapping(method = RequestMethod.GET, value = "/getPokemons")
    public Map<String,List<Pokemon>> getRedPokemons() {
        log.info("Starting red pokemons api");
        
        LinkedHashMap<String,List<Pokemon>> response = new LinkedHashMap<>();
        
        ApiCommunicationService service = new ApiCommunicationService();
        
        List<Pokemon> pokeList = service.getPokemons();
        
        pokeList.sort(Comparator.comparing(Pokemon::getHeight).reversed());
        
        List<Pokemon> heigted = pokeList.stream().limit(5).collect(Collectors.toList());
        
        pokeList.sort(Comparator.comparing(Pokemon::getWeight).reversed());
        
        List<Pokemon> weigted = pokeList.stream().limit(5).collect(Collectors.toList());
        
        List<Pokemon> base = pokeList.stream().limit(5).collect(Collectors.toList());   

        response.put("heaviest_pokemons", weigted);
        response.put("tallest_pokemons", heigted);
        response.put("experienced_pokemons", base);
        
        log.info("Answering red pokemons info");
        
        return response;
    }
	

}
