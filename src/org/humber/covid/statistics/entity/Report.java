package org.humber.covid.statistics.entity;

import java.util.Date;

public class Report {

	
	String date;
	String cityName;
	int pfizerDosesAdmisnistered;
	int modernaDosesAdministered;
	int astrazenecaDosesAdministered;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getPfizerDosesAdmisnistered() {
		return pfizerDosesAdmisnistered;
	}
	public void setPfizerDosesAdmisnistered(int pfizerDosesAdmisnistered) {
		this.pfizerDosesAdmisnistered = pfizerDosesAdmisnistered;
	}
	public int getModernaDosesAdministered() {
		return modernaDosesAdministered;
	}
	public void setModernaDosesAdministered(int modernaDosesAdministered) {
		this.modernaDosesAdministered = modernaDosesAdministered;
	}
	public int getAstrazenecaDosesAdministered() {
		return astrazenecaDosesAdministered;
	}
	public void setAstrazenecaDosesAdministered(int astrazenecaDosesAdministered) {
		this.astrazenecaDosesAdministered = astrazenecaDosesAdministered;
	}
	public Report(String tempDate, String cityName, int pfizerDosesAdmisnistered, int modernaDosesAdministered,
			int astrazenecaDosesAdministered) {
		super();
		this.date = tempDate;
		this.cityName = cityName;
		this.pfizerDosesAdmisnistered = pfizerDosesAdmisnistered;
		this.modernaDosesAdministered = modernaDosesAdministered;
		this.astrazenecaDosesAdministered = astrazenecaDosesAdministered;
	}
	@Override
	public String toString() {
		return "Report [date=" + date + ", cityName=" + cityName + ", pfizerDosesAdmisnistered="
				+ pfizerDosesAdmisnistered + ", modernaDosesAdministered=" + modernaDosesAdministered
				+ ", astrazenecaDosesAdministered=" + astrazenecaDosesAdministered + "]";
	}
	
	
	
	
}
