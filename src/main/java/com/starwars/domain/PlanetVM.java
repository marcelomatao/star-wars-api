package com.starwars.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanetVM {
	
	private String name;
	
	private String climate;
	
	private String terrain;
	
}
