package com.app.anra.crauler.model.VOs;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

	private String name;
	private String description;
	private String type;
	private int numEmployers;
	private String location;
	private List<String> tweets;

	public Empresa(String name, String description, String type, int numEmployers, String location) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.numEmployers = numEmployers;
		this.location = location;

		tweets = new ArrayList<String>();
	}

	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getTweets() {
		return tweets;
	}

	public void addTweet(String tweet) {
		tweets.add(tweet);
	}

	public int getNumEmployers() {
		return numEmployers;
	}

	public void setNumEmployers(int numEmployers) {
		this.numEmployers = numEmployers;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTweets(List<String> tweets) {
		this.tweets = tweets;
	}

}
