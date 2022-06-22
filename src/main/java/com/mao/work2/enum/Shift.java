package com.mao.work2.enum;
import java.io.*;

public enum Shift implements Serializable
{
	DAY("白班"), MIDDLE("中班"), NIGHT("夜班"), REST("休班");
	
	private String shift;
	private Shift(String shift){
		this.shift = shift;
	}
	
	public static Shift getByString(String shiftName) {
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
	
	public static int indexOf(Shift shift) {
		Shift[] shifts = Shift.values();
		for (int i=0;i<shifts.length;i++) {
			Shift tmp = shifts[i];
			if (tmp.equals(shift)) {
				return i;
			}
		}
		return -1;
	}
}
