package com.mao.work2.enum;
import java.io.*;

public enum Rate implements Serializable
{
	ONE_AND_HALF("1.5倍"), TWO("2倍"), THREE("3倍");
	
	private String rate;
	
	private Rate(String rate){
		this.rate = rate;
	}
	
	public static Rate getByString(String rateName) {
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
	
	public static int indexOf(Rate rate) {
		Rate[] rates = Rate.values();
		for (int i=0;i<rates.length;i++) {
			Rate tmp = rates[i];
			if (tmp.equals(rate)) {
				return i;
			}
		}
		return -1;
	}
}
