package com.conflux.service.dto;


public class IncomeLumpingDTO {

	private Integer incomeLumpingId;
	private double jan;
	private double feb;
	private double march;
	private double april;
	private double may;
	private double june;
	private double july;
	private double august;
	private double sept;
	private double october;
	private double nov;
	private double december;
	private double totalLumping;
	public Integer getIncomeLumpingId() {
		return incomeLumpingId;
	}
	public void setIncomeLumpingId(Integer incomeLumpingId) {
		this.incomeLumpingId = incomeLumpingId;
	}
	public double getJan() {
		return jan;
	}
	public void setJan(double jan) {
		this.jan = jan;
	}
	public double getFeb() {
		return feb;
	}
	public void setFeb(double feb) {
		this.feb = feb;
	}
	public double getMarch() {
		return march;
	}
	public void setMarch(double march) {
		this.march = march;
	}
	public double getApril() {
		return april;
	}
	public void setApril(double april) {
		this.april = april;
	}
	public double getMay() {
		return may;
	}
	public void setMay(double may) {
		this.may = may;
	}
	public double getJune() {
		return june;
	}
	public void setJune(double june) {
		this.june = june;
	}
	public double getJuly() {
		return july;
	}
	public void setJuly(double july) {
		this.july = july;
	}
	public double getAugust() {
		return august;
	}
	public void setAugust(double august) {
		this.august = august;
	}
	public double getSept() {
		return sept;
	}
	public void setSept(double sept) {
		this.sept = sept;
	}
	public double getOctober() {
		return october;
	}
	public void setOctober(double october) {
		this.october = october;
	}
	public double getNov() {
		return nov;
	}
	public void setNov(double nov) {
		this.nov = nov;
	}
	public double getDecember() {
		return december;
	}
	public void setDecember(double december) {
		this.december = december;
	}
	public double getTotalLumping() {
		return jan + feb + march + april + may + june + july + august + sept + nov + december;
	}
	public void setTotalLumping(double totalLumping) {
		this.totalLumping = totalLumping;
	}
	
}
