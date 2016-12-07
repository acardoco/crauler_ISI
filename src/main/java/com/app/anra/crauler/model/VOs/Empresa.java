package com.app.anra.crauler.model.VOs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "empresas")
public class Empresa {

	private String name;
	private String description;
	private String type;
	private int numEmployers;
	private String location;
	private List<Tweet> tweets;
	private String mediaValoraciones;

	public Empresa(String name, String description, String type, int numEmployers, String location) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.numEmployers = numEmployers;
		this.location = location;

		this.tweets = new ArrayList<Tweet>();
	}

	public Empresa() {
		// TODO Auto-generated constructor stub
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void addTweet(Tweet tweet) {
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

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public String getMediaValoraciones() {
		return mediaValoraciones;
	}

	public void setMediaValoraciones(String mediaValoraciones) {
		this.mediaValoraciones = mediaValoraciones;
	}

	@Override
	public boolean equals(Object object) {
		boolean equal = false;
		if (object != null && object instanceof Empresa) {
			equal = this.getName().equalsIgnoreCase(((Empresa) object).getName());
		}

		return equal;
	}
}
