package org.humber.covid.statistics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.humber.covid.statistics.entity.City;
import org.humber.covid.statistics.entity.Report;

public class HomePage extends JFrame {

	static ArrayList<Report> reports = new ArrayList<>();
	
	//All the components to be included inside the frame
	JPanel mainPanel;
	JPanel addRecordPanel;
	JPanel modifyRecordPanel;
	JPanel fetchRecordPanel;
	JPanel displayPanel;
	JTable displayTable;
	DefaultTableModel model;
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
	JLabel feedbackLabel;

	JButton addRecordButton;
	JButton modifyRecordButton;
	JButton fetchRecordByDateButton;
	JButton fetchRecordByCityButton;
	JButton fetchRecordByVaccineTypeButton;

	//Action listener to catch events
	ActionListener listener;
	
	//helper variables for data manipulation
	String oldModerna,oldAstra,oldPfizer;

	//File object variable to read and write data to a file
	File file;
	
	//Scanner to read from file
	Scanner sc;

	//Helper variable for data modification
	String records[][];
		
	//Helper variable to set flag for update
	int modifyFlag = 0;

	public HomePage() throws FileNotFoundException, ParseException {

		//setting size of the frame
		setSize(600, 600);
		//setting title of the frame
		setTitle("Covid Statistics");
		// TODO Auto-generated constructor stub
		//inner class action listener implementation
		class StatisticsActionListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// System.out.println("Here");

				//listener for add record button
				if (e.getSource() == addRecordButton) {
					// addRecord();
					showCreateUpdate();

				}

				//listener for submit button
				if (e.getSource() == submitBtn) {
					try {
						updateData();
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				//listener for modify button
				if (e.getSource() == modifyRecordButton) {
					modifyFlag = 1;
					showCreateUpdate();
				}
				
				//listener to fetch records by cities button
				if(e.getSource()==fetchRecordByCityButton) {
					showCityStats();
				}
				
				//listener to fetch records by date button
				if(e.getSource()==fetchRecordByDateButton) {
					showDateStats();
				}
				
				//listener to fetch records by vaccine type button
				if(e.getSource()==fetchRecordByVaccineTypeButton) {
					showTypeStats();
				}
			}

			

			
		}

		listener = new StatisticsActionListener(); 	//initiating listener
		fetchData();								//fetch data from file record.txt
		createAddRecord();							//create the panel for add record button
		createModifyRecord();						//create the panel for modify record button
		createFetchRecord();						//create the panel for fetch record button
		createUpdatePanel();						//a hidden panel where users can modify or set data
		createDisplayRecord();						//panel to place a jtable to display data
		createLayout();								//pack all the panels into the frame

	}
	
	/*
	 * function to count the total number of vaccines by their type Here i have
	 * looped through my array list and added the counts for each objects into
	 * counter variables. The result is then displayed in a new frame
	 */
	private void showTypeStats() {
		// TODO Auto-generated method stub
		int pfizerCount=0;
		int modernaCount=0;
		int astraCount=0;
		
		//counting the sum of doses by type
		for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			pfizerCount=pfizerCount+report.getAstrazenecaDosesAdministered();
			modernaCount+=report.getModernaDosesAdministered();
			astraCount+=report.getAstrazenecaDosesAdministered();
			
		}
		
		//creating a new frame to display the result
		JFrame vaccineTypeData = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Vaccine type stats"));
		String temp1 = "Total Pfizer doses administered: "+pfizerCount;
		String temp2 = "Total Moderna doses administered: "+modernaCount;
		String temp3 = "Total Astrazeneca doses administered: "+astraCount;
		JLabel jtf1 = new JLabel(temp1);
		JLabel jtf2 = new JLabel(temp2);
		JLabel jtf3 = new JLabel(temp3);
		panel.add(jtf1);
		panel.add(jtf2);
		panel.add(jtf3);
		vaccineTypeData.add(panel);
		vaccineTypeData.setSize(400,200);
		vaccineTypeData.setVisible(true);
		vaccineTypeData.setDefaultCloseOperation(JFrame.ABORT);
		
		
	}

	/*
	 * A hash map is used with date as its key and with each key recors of all the
	 * dose for all the vaccine types over all the cities have been stored. Later
	 * that hashmap data is displayed over a JOptionPane
	 */
	private void showDateStats() {
		// TODO Auto-generated method stub
		//Hashmap to store dates uniquely
		HashMap< String, String> dateRecords= new HashMap<String, String>();
		String temp=" ";
		for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			temp=temp+":\n";
			for (Iterator iterator2 = reports.iterator(); iterator2.hasNext();) {
				Report report2 = (Report) iterator2.next();
				if(report.getDate().equals(report2.getDate()))
				{
					temp= temp+report2.getCityName()+" : => Astrazeneca Doses: "+report2.getAstrazenecaDosesAdministered()+" | Pfizer Doses: "+report2.getPfizerDosesAdmisnistered()+" | Moderna Doses: "+report2.getModernaDosesAdministered()+"\n";
				}
			}
			//putting records of same date into the date key
			dateRecords.put(report.getDate(), temp);
			temp= " ";
		}
		//Showing the data
		JOptionPane.showMessageDialog(addRecordButton, dateRecords.toString());
	}


	//This function is using a hash map to store all the cities we have in our records
	//Once those cities are stored as unique key value pairs, the number of vaccine administerd for those cities over all the dates are summed up and stored with respect to each vaccine type
	private void showCityStats() {
		// TODO Auto-generated method stub
		HashMap<String, City> cityRecords = new HashMap<>();
		String output;
		for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			cityRecords.put(report.getCityName(), new City(report.getCityName(), 0, 0, 0));
		}
		
		for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
			Report report = (Report) iterator.next();
			City tempCity = cityRecords.get(report.getCityName());
			tempCity.setAstraCount(tempCity.getAstraCount()+report.getAstrazenecaDosesAdministered());
			tempCity.setModernaCount(tempCity.getModernaCount()+report.getModernaDosesAdministered());
			tempCity.setPfizerCount(tempCity.getPfizerCount()+report.getPfizerDosesAdmisnistered());
			cityRecords.put(report.getCityName(), tempCity);
		}
		//showing output
		JOptionPane.showMessageDialog(null, cityRecords.toString());
	}
	
	
	
	//using this function to add or modify data depending on the flags set when this function is called by the action listener
	private void updateData() throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		int flag = 0;
		//Checking if it is not an update request
		if (modifyFlag == 0) 
			{
			for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
				Report report = (Report) iterator.next();
				// System.out.println(report.getDate());
				//check if data for the given date and for the given city exists or not
				if (dateField.getText().equals(report.getDate())) {

					if (report.getCityName().equals(cityField.getText()))
						flag = 1;
					}

				}
			//if data does not exist then only add data else dont add.
			if (flag == 0) {
				//adding the data to our arraylist of reports
				reports.add(
						new Report(dateField.getText(), cityField.getText(), Integer.parseInt(pfizerField.getText()),
								Integer.parseInt(modernaField.getText()), Integer.parseInt(astraField.getText())));
				model.addRow(new Object[] { dateField.getText(), cityField.getText(),
						Integer.parseInt(pfizerField.getText()), Integer.parseInt(modernaField.getText()),
						Integer.parseInt(astraField.getText()) });
				modifyUpdatePanel.setVisible(false);
				FileWriter fileWritter = new FileWriter(file.getAbsolutePath(), true);
				BufferedWriter bw = new BufferedWriter(fileWritter);
				bw.write("\n" + dateField.getText() + "," + cityField.getText() + ","
						+ Integer.parseInt(pfizerField.getText()) + "," + (modernaField.getText()) + ","
						+ (astraField.getText()));
				bw.close();
			}

		}
		//if it is a update request
		else if (modifyFlag == 1) {
			
			System.out.println("here");
			for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
				Report report = (Report) iterator.next();
				if (dateField.getText().equals(report.getDate())) {

					if (cityField.getText().equals(report.getCityName())) {
						oldModerna = String.valueOf(report.getModernaDosesAdministered()) ;
						oldAstra = String.valueOf(report.getAstrazenecaDosesAdministered());
						oldPfizer = String.valueOf(report.getPfizerDosesAdmisnistered());
						report.setAstrazenecaDosesAdministered(Integer.parseInt(astraField.getText()));
						report.setModernaDosesAdministered(Integer.parseInt(modernaField.getText()));
						report.setPfizerDosesAdmisnistered(Integer.parseInt(pfizerField.getText()));
					}
				}	
			}
			
			
			int len = reports.size();
			String [] str = new String[len];
			int i = 0;
			for (Iterator iterator = reports.iterator(); iterator.hasNext();) {
				Report report = (Report) iterator.next();
				str[i] = report.getDate() +","+report.getCityName()+","+String.valueOf(report.getPfizerDosesAdmisnistered())+","+String.valueOf(report.getModernaDosesAdministered())+","+String.valueOf(report.getAstrazenecaDosesAdministered());;
				i++;
			}
			PrintWriter pr = new PrintWriter(file);
			
			for (String string : str) {
				pr.println(string);
			}
			pr.close();
			//after submitting data..hiding the input fields
			modifyUpdatePanel.setVisible(false);
			//resetting flag
			modifyFlag = 0;
			
			
			
			
		}
		
		//clearing the text fields
		dateField.setText("");
		cityField.setText("");
		pfizerField.setText("");
		modernaField.setText("");
		astraField.setText("");
		flag = 0;

	}

	private void showCreateUpdate() {
		// TODO Auto-generated method stub
		//showing the input fields
		modifyUpdatePanel.setVisible(true);
	}

	
	//function to create the fields for input data
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

	//function to create the display panel
	private void createDisplayRecord() throws FileNotFoundException {
		// TODO Auto-generated method stub

		
		String recordHeadings[] = { "Date", "City", "N_Pfizer", "N_Moderna", "N_Astra" };
		int numOfRecords = reports.size();
		records = new String[numOfRecords][5];
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

		model = new DefaultTableModel(records, recordHeadings);

		displayTable = new JTable(model);

		recSp = new JScrollPane(displayTable);
		recSp.setPreferredSize(new Dimension(500, 200));

	}

	//function to fetch data from file
	private void fetchData() throws FileNotFoundException {
		// TODO Auto-generated method stub

		file = new File(
				"C:\\Users\\sdsts\\Documents\\workspace-spring-tool-suite-4-4.13.0.RELEASE\\SwingJPAAssignment1\\src\\org\\humber\\covid\\statistics\\record.txt");
		sc = new Scanner(file);
		while (sc.hasNextLine()) {
			Report tempReport;
			String tempDate;
			String tempcityName;
			int temppfizerDosesAdmisnistered;
			int tempmodernaDosesAdministered;
			int tempastrazenecaDosesAdministered;

			String tempRecord = sc.nextLine();
			String[] splicedRecord = tempRecord.split(",");

			tempDate = splicedRecord[0];
			tempcityName = splicedRecord[1];
			temppfizerDosesAdmisnistered = Integer.parseInt(splicedRecord[2]);
			tempmodernaDosesAdministered = Integer.parseInt(splicedRecord[3]);
			tempastrazenecaDosesAdministered = Integer.parseInt(splicedRecord[4]);

			tempReport = new Report(tempDate, tempcityName, temppfizerDosesAdmisnistered, tempmodernaDosesAdministered,
					tempastrazenecaDosesAdministered);
			reports.add(tempReport);

		}

	}

	//Create the fetch by menu panel
	private void createFetchRecord() {
		// TODO Auto-generated method stub
		fetchRecordByCityButton = new JButton("City");
		fetchRecordByDateButton = new JButton("Date");
		fetchRecordByVaccineTypeButton = new JButton("Vaccine Type");

		fetchRecordPanel = new JPanel();
		fetchRecordPanel.setLayout(new GridLayout(3, 1));
		fetchRecordPanel.setBorder(new TitledBorder(new EtchedBorder(), "Fetch Records By"));
		fetchRecordPanel.add(fetchRecordByCityButton);
		fetchRecordPanel.add(fetchRecordByDateButton);
		fetchRecordPanel.add(fetchRecordByVaccineTypeButton);
		fetchRecordByCityButton.addActionListener(listener);
		fetchRecordByDateButton.addActionListener(listener);
		fetchRecordByVaccineTypeButton.addActionListener(listener);

	}

	//creating the modify record panel
	private void createModifyRecord() {
		// TODO Auto-generated method stub

		modifyRecordButton = new JButton("Modify Records");
		modifyRecordButton.addActionListener(listener);
		modifyRecordPanel = new JPanel();
		modifyRecordPanel.setLayout(new GridLayout(1, 1));
		modifyRecordPanel.setBorder(new TitledBorder(new EtchedBorder(), "Modify Record"));
		modifyRecordPanel.add(modifyRecordButton);

	}

	//creating the add record panel
	private void createAddRecord() {
		// TODO Auto-generated method stub
		addRecordButton = new JButton("Add Records");
		addRecordButton.addActionListener(listener);
		addRecordPanel = new JPanel();
		addRecordPanel.setLayout(new GridLayout(1, 1));
		addRecordPanel.setBorder(new TitledBorder(new EtchedBorder(), "Add Record"));
		addRecordPanel.add(addRecordButton);

	}

	//packing everything into the frame
	private void createLayout() {
		// TODO Auto-generated method stub
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 3));
		mainPanel.add(addRecordPanel);
		mainPanel.add(modifyRecordPanel);
		mainPanel.add(fetchRecordPanel);

		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1, 1));
		displayPanel.add(recSp);

	
		
		add(mainPanel, BorderLayout.NORTH);
		add(modifyUpdatePanel, BorderLayout.CENTER);
		add(displayPanel, BorderLayout.SOUTH);
	}

}
