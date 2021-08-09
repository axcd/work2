package com.mao.work2.enum;
import java.io.*;

public enum Rate implements Serializable
{
	ONE_AND_HALF("1.5倍"), TWO("2倍"), THREE("3倍");
	
	private String rate;
	
	private Rate(String rate){
		this.rate = rate;
	}
	
	public static Rate get(String rateName) {
		for (Rate rate : Rate.values()) {
			if (rate.getRateName().equals(rateName)) {
				return rate;
			}
		}
		return null;
	}
	
	public String getRateName()
	{
		return this.rate;
	}
	
}
