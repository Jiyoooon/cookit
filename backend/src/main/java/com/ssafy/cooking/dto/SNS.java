package com.ssafy.cooking.dto;

public class SNS {
	private String sns_name;
	private String sns_url;
	public String getSns_name() {
		return sns_name;
	}
	public void setSns_name(String sns_name) {
		this.sns_name = sns_name;
	}
	public String getSns_url() {
		return sns_url;
	}
	public void setSns_url(String sns_url) {
		this.sns_url = sns_url;
	}
	@Override
	public String toString() {
		return "SNS [sns_name=" + sns_name + ", sns_url=" + sns_url + "]";
	}
	
}
