package com.starwars.domain.bo;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.starwars.domain.PlanetVM;
import com.starwars.domain.entity.Planet;
import com.starwars.domain.swapi.PlanetSwapi;
import com.starwars.repository.PlanetRepository;
import com.starwars.repository.SwapiPlanetRepository;


@Component
public class PlanettBO {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlanetRepository planetRepository;
	
	@Autowired
	private SwapiPlanetRepository swapiPlanetRepository;

	public List<Planet> findAllPlanets() {
		logger.info("Getting all planets from repository.");
	    return planetRepository.findAll();
	}

	public void addPlanet(PlanetVM planetVM) {
		Planet planet = Planet.builder().build();
		planet.setName(planetVM.getName());
		planet.setTerrain(planetVM.getTerrain());
		planet.setClimate(planetVM.getClimate());
		planet.setNumberFilms(swapiPlanetRepository.getNumberOfFilmsByPlanetName(planetVM.getName()));
		planetRepository.save(planet);
	}

	public List<Planet> getAllPlanets() {
		return planetRepository.findAll();
	}

	public List<PlanetSwapi> getAllPlanetsSwapi() {
		return swapiPlanetRepository.getAllSwapiPlanets();
	}

	public Planet findPlanetByName(String name) {
		return planetRepository.findByName(name);
	}
	
	public Optional<Planet> findPlanetById(Integer id) {
		return planetRepository.findById(id);
	}

	public void deletePlanetById(Integer id) {
		Optional<Planet> planet = findPlanetById(id);
		if(planet.isPresent()) {
			planetRepository.delete(planet.get());
		}
	}

}