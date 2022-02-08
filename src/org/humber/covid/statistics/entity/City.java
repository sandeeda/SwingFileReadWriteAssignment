package org.humber.covid.statistics.entity;
//Store city objects for fetching records based on city
public class City {

	String name;
	int pfizerCount;
	int modernaCount;
	int astraCount;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPfizerCount() {
		return pfizerCount;
	}
	public void setPfizerCount(int pfizerCount) {
		this.pfizerCount = pfizerCount;
	}
	public int getModernaCount() {
		return modernaCount;
	}
	public void setModernaCount(int modernaCount) {
		this.modernaCount = modernaCount;
	}
	public int getAstraCount() {
		return astraCount;
	}
	public void setAstraCount(int astraCount) {
		this.astraCount = astraCount;
	}
	public City(String name, int pfizerCount, int modernaCount, int astraCount) {
		super();
		this.name = name;
		this.pfizerCount = 0;
		this.modernaCount = 0;
		this.astraCount = 0;
	}
	@Override
	public String toString() {
		return ">\npfizerCount=" + pfizerCount + "\n modernaCount=" + modernaCount
				+ "\n astraCount=" + astraCount + "\n\n\n";
	}
	
	
}
