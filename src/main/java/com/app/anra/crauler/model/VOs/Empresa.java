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
	
	public Empresa(String name, String description, String type, int numEmployers, String location){
		this.name = name;
		this.description = description;
		this.type = type;
		this.numEmployers = numEmployers;
		this.location = location;
		
		tweets = new ArrayList<String>();
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

}
