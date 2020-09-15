package com.mao.work.enum;
import java.io.*;

public enum Shift implements Serializable
{
	DAY("白班"), MIDDLE("中班"), NIGHT("夜班"), REST("休班");
	
	private String shift;
	private Shift(String shift){
		this.shift = shift;
	}
	
	public static Shift get(String shiftName) {
		for (Shift shift : Shift.values()) {
			if (shift.getShiftName().equals(shiftName)) {
				return shift;
			}
		}
		return null;
	}
	
	public String getShiftName(){
		return this.shift;
	}
	
}
