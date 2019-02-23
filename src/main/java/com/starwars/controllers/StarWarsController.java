package com.starwars.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.starwars.domain.PlanetVM;
import com.starwars.domain.bo.PlanettBO;
import com.starwars.domain.entity.Planet;
import com.starwars.domain.swapi.PlanetSwapi;

@RestController
@RequestMapping("/api/starwars")
public class StarWarsController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
	private PlanettBO planetBo;

    @RequestMapping(value = "/planet", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addPlanet(@RequestBody PlanetVM planetVM) {
    	logger.info("Adding planet {}", planetVM.getName());
    	Planet planet = planetBo.findPlanetByName(planetVM.getName());
    	if(planet != null) {
    		return new ResponseEntity<>("This planet is already created.", HttpStatus.CONFLICT);
    	}
    	planetBo.addPlanet(planetVM);
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/planets", method = RequestMethod.GET)
    public ResponseEntity<List<Planet>> getPlanets() {
    	logger.info("Getting all planets {}");
    	List<Planet> listOfPlanets = planetBo.getAllPlanets();
    	if(listOfPlanets.size() == 0) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<Planet>>(listOfPlanets, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/swapi/planets", method = RequestMethod.GET)
    public ResponseEntity<List<PlanetSwapi>> getSwapiPlanets() {
    	logger.info("Getting all planets from swapi {}");
    	List<PlanetSwapi> listOfPlanets = planetBo.getAllPlanetsSwapi();
    	if(listOfPlanets.size() == 0) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<List<PlanetSwapi>>(listOfPlanets, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/planet/name/{name}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Planet> getPlanetByName(@PathVariable("name") String name) {
    	logger.info("Getting the planet with name {}" + name);
    	Planet planet = planetBo.findPlanetByName(name);
    	if(planet == null ) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<Planet>(planet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlanetById(@PathVariable("id") Integer id) {
    	logger.info("Getting the planet with id {}" + id);
    	Optional<Planet> planet = planetBo.findPlanetById(id);
    	if(planet.get() == null ) {
    		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    	}
    	return new ResponseEntity<>(planet, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/planet/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePlanetById(@PathVariable("id") Integer id) {
    	logger.info("Removing the planet with id {}" + id);
    	planetBo.deletePlanetById(id);
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
