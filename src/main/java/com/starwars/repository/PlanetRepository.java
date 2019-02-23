package com.starwars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.starwars.domain.entity.Planet;


@Repository
public interface PlanetRepository extends JpaRepository<Planet, Integer> {

	Planet findByName(String name);
	
}