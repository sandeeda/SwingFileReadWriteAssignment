package org.humber.covid.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.humber.covid.statistics.entity.Report;

public class HomePage extends JFrame {
	
	static ArrayList<Report> reports = new ArrayList<>();
	
	JPanel mainPanel;
	JPanel addRecordPanel;
	JPanel modifyRecordPanel;
	JPanel fetchRecordPanel;
	JPanel displayPanel;
	JTable displayTable;
	JScrollPane recSp;
	
	
	JPanel modifyUpdatePanel;
	JLabel dateLabel;
	JTextField dateField;
	JLabel cityLabel;
	JTextField cityField;
	JLabel pfizerLabel;
	JTextField pfizerField;
	JLabel astraLabel;
	JTextField astraField;
	JLabel modernaLabel;
	JTextField modernaField;
	JButton submitBtn;
	
	
	JButton addRecordButton;
	JButton modifyRecordButton;
	JButton fetchRecordByDateButton;
	JButton fetchRecordByCityButton;
	JButton fetchRecordByVaccineTypeButton;
	
	
	ActionListener listener;
	
	
	File file;
	Scanner sc;
	
	public HomePage() throws FileNotFoundException, ParseException {
		
		
		
		setSize(600,600);
		setTitle("Covid Statistics");
		// TODO Auto-generated constructor stub
		class StatisticsActionListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("Here");
				
				if(e.getSource()==addRecordButton) {
					//addRecord();
					showCreateUpdate();
					
				}
				
				
			}

		
			
		}
		
		
		listener = new StatisticsActionListener();
		fetchData();
		createAddRecord();
		createModifyRecord();
		createFetchRecord();
		createUpdatePanel();
		createDisplayRecord();
		createLayout();

		
	}
	private void showCreateUpdate() {
		// TODO Auto-generated method stub
		modifyUpdatePanel.setVisible(true);
	}

	private void createUpdatePanel() {
		// TODO Auto-generated method stub
		modifyUpdatePanel = new JPanel();

		dateLabel = new JLabel("Date");
		dateField = new JTextField(20);

		cityLabel = new JLabel("City");
		cityField = new JTextField(20);

		pfizerLabel = new JLabel("Number of Pfizer doses administered");
		pfizerField = new JTextField(5);

		astraLabel = new JLabel("Number of Astrazeneca doses administered");
		astraField = new JTextField(5);

		modernaLabel = new JLabel("Number of Moderna doses administered");
		modernaField = new JTextField(5);

		submitBtn = new JButton("Submit");
		submitBtn.addActionListener(listener);

		modifyUpdatePanel.setLayout(new GridLayout(6, 2));
		modifyUpdatePanel.setBorder(new TitledBorder(new EtchedBorder(), "Enter new record"));
		modifyUpdatePanel.add(dateLabel);
		modifyUpdatePanel.add(dateField);
		modifyUpdatePanel.add(cityLabel);
		modifyUpdatePanel.add(cityField);
		modifyUpdatePanel.add(pfizerLabel);
		modifyUpdatePanel.add(pfizerField);
		modifyUpdatePanel.add(modernaLabel);
		modifyUpdatePanel.add(modernaField);
		modifyUpdatePanel.add(astraLabel);
		modifyUpdatePanel.add(astraField);
		modifyUpdatePanel.add(submitBtn);
		modifyUpdatePanel.setVisible(false);
	}

	

	private void createDisplayRecord() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		
		String recordHeadings[] = {"Date", "City", "N_Pfizer","N_Moderna","N_Astra"};
		int numOfRecords = reports.size();
		String records[][] = new String[numOfRecords][5];
		int i = 0;
		for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			records[i][0] = report.getDate();
			records[i][1] = report.getCityName();
			records[i][2] = String.valueOf(report.getPfizerDosesAdmisnistered());
			records[i][3] = String.valueOf(report.getModernaDosesAdministered());
			records[i][4] = String.valueOf(report.getAstrazenecaDosesAdministered());
			i++;
		}
		displayTable = new JTable(records, recordHeadings);
		recSp = new JScrollPane(displayTable);
		recSp.setPreferredSize(new Dimension(500,200));
		

	}

	private void fetchData() throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		file = new File("C:\\Users\\sdsts\\Documents\\workspace-spring-tool-suite-4-4.13.0.RELEASE\\SwingJPAAssignment1\\src\\org\\humber\\covid\\statistics\\record.txt");
		sc = new Scanner(file);
		while(sc.hasNextLine()) {
			Report tempReport;
			String tempDate;
			String tempcityName;
			int temppfizerDosesAdmisnistered;
			int tempmodernaDosesAdministered;
			int tempastrazenecaDosesAdministered;
			
			String tempRecord = sc.nextLine();
			String[] splicedRecord = tempRecord.split(",");
			
			tempDate= splicedRecord[0];  
			tempcityName = splicedRecord[1];
			temppfizerDosesAdmisnistered = Integer.parseInt(splicedRecord[2]);
			tempmodernaDosesAdministered = Integer.parseInt(splicedRecord[3]);
			tempastrazenecaDosesAdministered = Integer.parseInt(splicedRecord[4]);
			
			
			tempReport = new Report(tempDate, tempcityName, temppfizerDosesAdmisnistered, tempmodernaDosesAdministered, tempastrazenecaDosesAdministered);
			reports.add(tempReport);
			
			for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
				Report report = (Report) iterator.next();
				System.out.println(report);
			}
		
		}
		
	}

	private void createFetchRecord() {
		// TODO Auto-generated method stub
		fetchRecordByCityButton = new JButton("City");
		fetchRecordByDateButton = new JButton("Date");
		fetchRecordByVaccineTypeButton = new JButton("Vaccine Type");
		
		fetchRecordPanel = new JPanel();
		fetchRecordPanel.setLayout(new GridLayout(3,1));
		fetchRecordPanel.setBorder(new TitledBorder(new EtchedBorder(),"Fetch Records By"));
		fetchRecordPanel.add(fetchRecordByCityButton);
		fetchRecordPanel.add(fetchRecordByDateButton);
		fetchRecordPanel.add(fetchRecordByVaccineTypeButton);
		
		
	}

	private void createModifyRecord() {
		// TODO Auto-generated method stub
		
		modifyRecordButton = new JButton("Modify Records");
		modifyRecordButton.addActionListener(listener);
		modifyRecordPanel = new JPanel();
		modifyRecordPanel.setLayout(new GridLayout(1,1));
		modifyRecordPanel.setBorder(new TitledBorder(new EtchedBorder(),"Modify Record"));
		modifyRecordPanel.add(modifyRecordButton);
		
	}

	private void createAddRecord() {
		// TODO Auto-generated method stub
		addRecordButton = new JButton("Add Records");
		addRecordButton.addActionListener(listener);
		addRecordPanel = new JPanel();
		addRecordPanel.setLayout(new GridLayout(1,1));
		addRecordPanel.setBorder(new TitledBorder(new EtchedBorder(),"Add Record"));
		addRecordPanel.add(addRecordButton);
		

		
	}

	private void createLayout() {
		// TODO Auto-generated method stub
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,3));
		mainPanel.add(addRecordPanel);
		mainPanel.add(modifyRecordPanel);
		mainPanel.add(fetchRecordPanel);
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,1));
		displayPanel.add(recSp);

		
		add(mainPanel,BorderLayout.NORTH);
		add(modifyUpdatePanel,BorderLayout.CENTER);
		add(displayPanel,BorderLayout.SOUTH);
	}

}
