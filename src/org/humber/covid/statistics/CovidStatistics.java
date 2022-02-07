package org.humber.covid.statistics;

import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.swing.JFrame;

public class CovidStatistics {
	public static void main(String[] args) throws FileNotFoundException, ParseException {
		
		JFrame mainMenu = new HomePage();
		mainMenu.setVisible(true);
		mainMenu.setDefaultCloseOperation(3);
	}
}
