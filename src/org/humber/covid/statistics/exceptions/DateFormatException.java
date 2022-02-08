package org.humber.covid.statistics.exceptions;

public class DateFormatException extends Exception {
public DateFormatException() {
	// TODO Auto-generated constructor stub
}
@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Date Format is not accepted";
	}
}
