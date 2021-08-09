package com.mao.work2.enum;

import java.io.*;

public enum Fake implements Serializable
{
	//正常出勤,事假,病假,带薪假
	NORMAL("加班"),LEAVE("事假"),SICK("病假"),TAKEOFF("调休"),PAID("年假");
	
	private String fake;
	private Fake(String fake){
		this.fake = fake;
	}
	
	public static Fake get(String fakeName) {
		for (Fake fake : Fake.values()) {
			if (fake.getFakeName().equals(fakeName)) {
				return fake;
			}
		}
		return null;
	}

	public String getFakeName(){
		return this.fake;
	}
}
