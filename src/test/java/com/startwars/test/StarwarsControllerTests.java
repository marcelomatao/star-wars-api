package com.startwars.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.starwars.Application;
import com.starwars.domain.PlanetVM;
import com.starwars.domain.entity.Planet;
import com.starwars.domain.swapi.PlanetSwapi;
import com.starwars.domain.swapi.StarWarsPlanets;
import com.starwars.repository.SwapiPlanetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.properties")
public class StarwarsControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private RestTemplate restTemplateSsl;
	
	@Test
	public void testCreatePlanetAndDeletePlanet() throws Exception {
		addPlanetNamePost("Septo");
		deleteById(1);
	}
	
	@Test
	public void testCreatePlanetGetPlanetByNameDeletePlanet() throws Exception {
		String name = "Septo";
		addPlanetNamePost(name);
		Planet planet = getPlanetByName(name);
		assertNotNull(planet);
		deleteById(1);
	}
	
	@Test
	public void testListPlanetsSwapi() throws Exception {
		int numberOfPLanets = getPlanetsSwapiWebsite().getCount();
		List<PlanetSwapi> planets = getPlanetsSwapiStarwars();
		assertEquals(numberOfPLanets, planets.size());
	}
	
	@Test
	public void testListPlanets() throws Exception {
		addPlanetNamePost("Totoru");
		addPlanetNamePost("Jacuta");
		int planetsAdded = getPlanets().size();
		assertEquals(2, planetsAdded);
		deleteById(1);
		deleteById(2);
	}
	
	@Test
	public void testCreatePlanetGetPlanetByIdDeletePlanet() throws Exception {
		String name = "Septo";
		addPlanetNamePost(name);
		Planet planet = getPlanetById(1);
		assertNotNull(planet);
		assertEquals(name, planet.getName());
		deleteById(1);
	}
	
	private Planet getPlanetByName(String name) {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<Planet> response = this.restTemplate.exchange("/api/starwars/planet/name/"+name, HttpMethod.GET, request,
				Planet.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return (Planet) response.getBody();
	}
	
	private Planet getPlanetById(int id) {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<Planet> response = this.restTemplate.exchange("/api/starwars/planet/"+id, HttpMethod.GET, request,
				Planet.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		return (Planet) response.getBody();
	}
	
	private List<PlanetSwapi> getPlanetsSwapiStarwars() {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<List> response = this.restTemplate.exchange("/api/starwars/swapi/planets", HttpMethod.GET, request,
				List.class); 
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertNotNull(response.getBody());
		return (List<PlanetSwapi>) response.getBody();
	}
	
	private List<Planet> getPlanets() {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<List> response = this.restTemplate.exchange("/api/starwars/planets", HttpMethod.GET, request,
				List.class); 
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertNotNull(response.getBody());
		return (List<Planet>) response.getBody();
	}
	
	private StarWarsPlanets getPlanetsSwapiWebsite() {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<StarWarsPlanets> response = this.restTemplateSsl.exchange(SwapiPlanetRepository.SWAPI_PLANETS_URL, HttpMethod.GET, request,
				StarWarsPlanets.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertNotNull(response.getBody());
		return response.getBody();
	}

	private void addPlanetNamePost(String name) throws Exception {
		PlanetVM planet = PlanetVM.builder().build();
		planet.setName(name);
		planet.setClimate("subartic");
		planet.setTerrain("barren");

		HttpHeaders headers = setHeaders();
		HttpEntity<PlanetVM> request = new HttpEntity<PlanetVM>(planet, headers);
		ResponseEntity<?> response = this.restTemplate.exchange("/api/starwars/planet", HttpMethod.POST, request,
				String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	public void deleteById(int id) throws Exception {
		HttpHeaders headers = setHeaders();
		HttpEntity<?> request = new HttpEntity<>(headers);
		ResponseEntity<?> response = this.restTemplate.exchange("/api/starwars/planet/"+id, HttpMethod.DELETE, request,
				Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	private HttpHeaders setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, "application/json");
		headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
		return headers;
	}

}