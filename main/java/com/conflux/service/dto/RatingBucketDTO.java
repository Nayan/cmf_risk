package com.conflux.service.dto;

public class RatingBucketDTO {

	private int ratingBucketId;
	private String name;
	private String displayValue;

	public int getRatingBucketId() {
		return ratingBucketId;
	}

	public void setRatingBucketId(int ratingBucketsId) {
		this.ratingBucketId = ratingBucketsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

}
