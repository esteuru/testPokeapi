package com.test.pokeapi.server;

import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.test.pokeapi.server.presentation.controller.RedPokemonsController;

@SpringBootTest
class ServerApplicationTests {

	@Test
	void contextLoads() throws JSONException {
		RedPokemonsController api = new RedPokemonsController();
		System.out.println(api.getRedPokemons());
		
	}

}
