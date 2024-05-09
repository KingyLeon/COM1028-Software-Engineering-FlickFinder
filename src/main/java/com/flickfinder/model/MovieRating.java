package com.flickfinder.model;

public class MovieRating extends Movie {
	private double rating;
	private int votes;

	public MovieRating(int id, String title, int year, double rating, int votes) {
		super(id, title, year);
		this.rating = rating;
		this.votes = votes;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

}
