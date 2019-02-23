package com.starwars.domain.swapi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "count", "next", "previous", "results" })
public class StarWarsPlanets implements Serializable {

	@JsonProperty("count")
	private Integer count;
	@JsonProperty("next")
	private String next;
	@JsonProperty("previous")
	private Object previous;
	@JsonProperty("results")
	private List<PlanetSwapi> planets = new ArrayList<PlanetSwapi>();
	private final static long serialVersionUID = 6858541742497865746L;

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public StarWarsPlanets() {
	}

	/**
	 * 
	 * @param planets
	 * @param previous
	 * @param count
	 * @param next
	 */
	public StarWarsPlanets(Integer count, String next, Object previous, List<PlanetSwapi> planets) {
		super();
		this.count = count;
		this.next = next;
		this.previous = previous;
		this.planets = planets;
	}

	@JsonProperty("count")
	public Integer getCount() {
		return count;
	}

	@JsonProperty("count")
	public void setCount(Integer count) {
		this.count = count;
	}

	@JsonProperty("next")
	public String getNext() {
		return next;
	}

	@JsonProperty("next")
	public void setNext(String next) {
		this.next = next;
	}

	@JsonProperty("previous")
	public Object getPrevious() {
		return previous;
	}

	@JsonProperty("previous")
	public void setPrevious(Object previous) {
		this.previous = previous;
	}

	@JsonProperty("planets")
	public List<PlanetSwapi> getPlanets() {
		return planets;
	}

	@JsonProperty("results")
	public void setPlanets(List<PlanetSwapi> planets) {
		this.planets = planets;
	}

}