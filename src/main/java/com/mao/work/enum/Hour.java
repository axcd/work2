package com.mao.work.enum;

import java.io.*;
import com.mao.work.*;

public enum Hour implements Serializable
{
	ZERO("0h"),HALF("0.5h"),
	ONE("1h"),ONE_AND_HALF("1.5h"),
	TWO("2h"),TWO_AND_HALF("2.5h"),
	THREE("3h"),THREE_AND_HALF("3.5h"),
	FOUR("4h"),FOUR_AND_HALF("4.5h"),
	FIVE("5h"),FIVE_AND_HALF("5.5h"),
	SIX("6h"),SIX_AND_HALF("6.5h"),
	SEVEN("7h"),SEVEN_AND_HALF("7.5h"),
	EIGHT("8h"),EIGHT_AND_HALF("8.5h"),
	NINE("9h"),NINE_AND_HALF("9.5h"),
	TEN("10h"),TEN_AND_HALF("10.5h"),
	ELEVEN("11h"),ELEVEN_AND_HALF("11.5h"),
	TWELVE("12h"),TWELVE_AND_HALF("12.5h"),
	THIRTEEN("13h"),THIRTEEN_AND_HALF("13.5h"),
	FOURTEEN("14h"),FOURTEEN_AND_HALF("14.5h"),
	FIFTEEN("15h"),FIFTEEN_AND_HALF("15.5h"),
	SIXTEEN("16h"),SIXTEEN_AND_HALF("16.5h"),
	SEVENTEEN("17h"),SEVENTEEN_AND_HALF("17.5h"),
	EIGHTEEN("18h"),EIGHTEEN_AND_HALF("18.5h"),
	NINETEEN("19h"),NINETEEN_AND_HALF("19.5h"),
	TWENTY("20h"),TWENTY_AND_HALF("20.5h"),
	TWENTY_ONE("21h"),TWENTY_ONE_AND_HALF("21.5h"),
	TWENTY_TWO("22h"),TWENTY_TWO_AND_HALF("22.5h"),
	TWENTY_THREE("23h"),TWENTY_THREE_AND_HALF("23.5h"),
	TWENTY_FOUR("24h");
	
	private String hour;
	private Hour(String hour){
		this.hour = hour;
	}
	
	public static Hour get(String hourName) {
		for (Hour hour : Hour.values()) {
			if (hour.getHourName().equals(hourName)) {
				return hour;
			}
		}
		return null;
	}

	public String getHourName(){
		return this.hour;
	}
	
	public static int getIndex(float f) {
		String hourName = "h";
		if(f==(int)f)
			hourName = (int)f+"h";
		else
			hourName = f+"h";
			
		Hour[] hours = Hour.values();
		for (int i=0;i<hours.length;i++) {
			Hour hour = hours[i];
			if (hour.getHourName().equals(hourName)) {
				return i;
			}
		}
		return -1;
	}
	
	public static Hour getHour(float f){
		String hour = "h";
		if(f==(int)f)
			hour = (int)f+"h";
		else
			hour = f+"h";
		return Hour.get(hour);
	}
	
}
