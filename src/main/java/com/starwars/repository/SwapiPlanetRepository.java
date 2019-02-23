package com.starwars.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.starwars.domain.swapi.PlanetSwapi;
import com.starwars.domain.swapi.StarWarsPlanets;

@Repository
public class SwapiPlanetRepository {

	@Autowired
    private RestTemplate restTemplate;
	
	public static final String SWAPI_PLANETS_URL = "https://swapi.co/api/planets/"; 
	
	public List<PlanetSwapi> getAllSwapiPlanets() {
		List<PlanetSwapi> allPlanets = new ArrayList<PlanetSwapi>();
		StarWarsPlanets planets = getPlanetsAndAddAll(SWAPI_PLANETS_URL, allPlanets);
			
		while (planets.getNext() != null) {
			planets = getPlanetsAndAddAll(planets.getNext(), allPlanets);
		}
		
		return allPlanets;
	}
	
	public StarWarsPlanets getPlanetsAndAddAll(String url, List<PlanetSwapi> allPlanets) {
		StarWarsPlanets planets = restTemplate.getForObject(url, StarWarsPlanets.class);
		allPlanets.addAll(planets.getPlanets());
		return planets;
	}

	public int getNumberOfFilmsByPlanetName(String name) {
		PlanetSwapi planet = getPlanetByName(name);
		if(planet != null) {
			return planet.getFilms().size();
		}
		return 0;
	}
	
	public PlanetSwapi getPlanetByName(String name) {
		StarWarsPlanets planets = restTemplate.getForObject(SWAPI_PLANETS_URL, StarWarsPlanets.class);
		PlanetSwapi planet = getPlanetByName(planets.getPlanets(), name);
			
		while (planets.getNext() != null && planet == null) {
			planets = restTemplate.getForObject(planets.getNext(), StarWarsPlanets.class);
			planet = getPlanetByName(planets.getPlanets(), name);
		}
		
		return planet;
	}
	
	public PlanetSwapi getPlanetByName(List<PlanetSwapi> planets, String name) {
		for (PlanetSwapi planetSwapi : planets) {
			if(planetSwapi.getName().equalsIgnoreCase(name)) {
				return planetSwapi;
			}
		}
		return null;
	}
	
}
