package com.ssafy.cooking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SNS {
	@JsonIgnore
//	public static final String[] sns_list = {"facebook", "twitter", "instagram", "youtube", "naverblog"};
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
