package com.mao.work2.settings;

import java.io.*;

public class Settings implements Serializable
{
	private float basePay = 2000;
	private int startDay = 1;
	private float performance = 1000;
	private float middleShiftSubsidy = 10;
	private float nightShiftSubsidy = 15;
	private float socialInsurance = 400;
	private float housingFund = 400;
	private float temperatureSubsidy = 0;
	private float transportationSubsidy = 0;
	private float postSubsidy = 100;
	private float otherSubsidy = 0;
	private float otherDeductions = 0;
	private float specialDeduction = 0;

	public void setSpecialDeduction(float specialDeduction)
	{
		this.specialDeduction = specialDeduction;
	}

	public float getSpecialDeduction()
	{
		return specialDeduction;
	}

	public void setBasePay(float basePay)
	{
		this.basePay = basePay;
	}

	public float getBasePay()
	{
		return basePay;
	}

	public void setStartDay(int startDay)
	{
		this.startDay = startDay;
	}

	public int getStartDay()
	{
		return startDay;
	}

	public void setPerformance(float performance)
	{
		this.performance = performance;
	}

	public float getPerformance()
	{
		return performance;
	}

	public void setMiddleShiftSubsidy(float middleShiftSubsidy)
	{
		this.middleShiftSubsidy = middleShiftSubsidy;
	}

	public float getMiddleShiftSubsidy()
	{
		return middleShiftSubsidy;
	}

	public void setNightShiftSubsidy(float nightShiftSubsidy)
	{
		this.nightShiftSubsidy = nightShiftSubsidy;
	}

	public float getNightShiftSubsidy()
	{
		return nightShiftSubsidy;
	}

	public void setSocialInsurance(float socialInsurance)
	{
		this.socialInsurance = socialInsurance;
	}

	public float getSocialInsurance()
	{
		return socialInsurance;
	}

	public void setHousingFund(float housingFund)
	{
		this.housingFund = housingFund;
	}

	public float getHousingFund()
	{
		return housingFund;
	}

	public void setTemperatureSubsidy(float temperatureSubsidy)
	{
		this.temperatureSubsidy = temperatureSubsidy;
	}

	public float getTemperatureSubsidy()
	{
		return temperatureSubsidy;
	}

	public void setTransportationSubsidy(float transportationSubsidy)
	{
		this.transportationSubsidy = transportationSubsidy;
	}

	public float getTransportationSubsidy()
	{
		return transportationSubsidy;
	}

	public void setPostSubsidy(float postSubsidy)
	{
		this.postSubsidy = postSubsidy;
	}

	public float getPostSubsidy()
	{
		return postSubsidy;
	}

	public void setOtherSubsidy(float otherSubsidy)
	{
		this.otherSubsidy = otherSubsidy;
	}

	public float getOtherSubsidy()
	{
		return otherSubsidy;
	}

	public void setOtherDeductions(float otherDeductions)
	{
		this.otherDeductions = otherDeductions;
	}

	public float getOtherDeductions()
	{
		return otherDeductions;
	}
}
