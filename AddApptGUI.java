import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AddApptGUI extends JFrame {

	private JFrame frame;
	private JButton returnBtn;
	private JPanel returnBtnPanel;
	private JPanel mainField;
	private JButton addApptBtn;
	private JPanel addApptPanel;
	private JTextField typeText;
	private JTextField descText;
	private JTextField yearText;
	private JTextField monthText;
	private JTextField dayText;
	private JLabel label;
	private buttonListener listener;
	private ArrayList<Appointment> appt;
	private static final String FileName = "appointmentsFile";

	/**
	 * Constructs a GUI for user to be able to add/save appointments to an array list and file
	 * @param appt ArrayList of appointments to be used
	 */
	public AddApptGUI(ArrayList<Appointment> appt) {
		super();
		this.appt = appt;
		listener = new buttonListener();

		returnBtnSetup();
		mainFieldSetup();
		addApptPanelSetup();
		frameSetup();
	}

	/**
	 * Sets up the frame
	 */
	private void frameSetup() {
		frame = new JFrame("Add Appointments");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(400, 400, 600, 300);
		frame.setLayout(new BorderLayout());
		frame.add(returnBtnPanel, BorderLayout.NORTH);
		frame.add(mainField, BorderLayout.CENTER);
		frame.add(addApptPanel, BorderLayout.SOUTH);
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
				frame.dispose();
				new AppointmentGUI(appt);
			}
		});
	}

	/**
	 * Sets up the panel that contains the return button on the north side of the frame
	 */
	private void returnBtnSetup() {
		returnBtnPanel = new JPanel();
		returnBtnPanel.setLayout(new BorderLayout());
		returnBtn = new JButton("Return");
		returnBtnPanel.add(returnBtn, BorderLayout.WEST);

		returnBtn.addActionListener(listener);
	}

	/**
	 * Sets up the panel that contains the text fields that is central of the frame
	 */
	private void mainFieldSetup() {
		mainField = new JPanel();
		mainField.setLayout(new FlowLayout());

		JPanel apptTypePanel = new JPanel();
		JLabel typeLabel = new JLabel("Appointment Type (Onetime, Monthly, or Daily)");
		typeText = new JTextField();
		typeText.setColumns(15);
		apptTypePanel.add(typeLabel);
		apptTypePanel.add(typeText);

		JPanel apptDescPanel = new JPanel();
		JLabel descLabel = new JLabel("Appointment Description (doctor, class, etc...)");
		descText = new JTextField();
		descText.setColumns(15);
		apptDescPanel.add(descLabel);
		apptDescPanel.add(descText);

		JPanel apptDatePanel = new JPanel();
		apptDatePanel.setLayout(new GridLayout(3, 1));

		JPanel monthPanel = new JPanel();
		JLabel monthLabel = new JLabel("Appointment Month (in numbers: ex: January = 01 or 1)");
		monthText = new JTextField();
		monthText.setColumns(15);
		monthPanel.add(monthLabel);
		monthPanel.add(monthText);

		JPanel dayPanel = new JPanel();
		JLabel dayLabel = new JLabel("Appointment Day");
		dayText = new JTextField();
		dayText.setColumns(15);
		dayPanel.add(dayLabel);
		dayPanel.add(dayText);

		JPanel yearPanel = new JPanel();
		JLabel yearLabel = new JLabel("Appointment Year");
		yearText = new JTextField();
		yearText.setColumns(15);
		yearPanel.add(yearLabel);
		yearPanel.add(yearText);

		apptDatePanel.add(monthPanel);
		apptDatePanel.add(dayPanel);
		apptDatePanel.add(yearPanel);

		mainField.add(apptTypePanel);
		mainField.add(apptDescPanel);
		mainField.add(apptDatePanel);
	}

	/**
	 * Sets up the panel that contains the add button on the south side of the frame
	 */
	private void addApptPanelSetup() {
		addApptPanel = new JPanel();
		addApptPanel.setLayout(new BorderLayout());
		addApptBtn = new JButton("Add");
		addApptPanel.add(addApptBtn, BorderLayout.EAST);

		label = new JLabel(this.appt.size() + " total appointments");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		addApptPanel.add(label, BorderLayout.CENTER);

		addApptBtn.addActionListener(listener);
	}

	/**
	 * Reads in the text the text if it is valid
	 * @return true if the input is valid, false otherwise
	 */
	private boolean readText() {
		Appointment appointment = new Appointment();
		String typeFieldText = typeText.getText();
		if (typeFieldText.equalsIgnoreCase("onetime") || typeFieldText.equalsIgnoreCase("monthly")
				|| typeFieldText.equalsIgnoreCase("daily")) {
			String descFieldText = descText.getText();
			String monthString = monthText.getText();
			String dayString = dayText.getText();
			String yearString = yearText.getText();
			if (isInt(monthString) && isInt(dayString) && isInt(yearString)) {
				int month = Integer.parseInt(monthString);
				int day = Integer.parseInt(dayString);
				int year = Integer.parseInt(yearString);

				if (typeFieldText.equalsIgnoreCase("onetime")) {
					appointment = new Onetime(descFieldText, month, day, year);
					appt.add(appointment);
				}

				else if (typeFieldText.equalsIgnoreCase("monthly")) {
					appointment = new Monthly(descFieldText, month, day, year);
					appt.add(appointment);
				}

				else {
					appointment = new Daily(descFieldText, month, day, year);
					appt.add(appointment);
				}
				return true;
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid Entry! Use numbers for Month, Day, Year");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Invalid appointment type");
		}
		return false;
	}

	/**
	 * Checks to see if a String can become an int
	 * @param text the string to be tried to convert to text
	 * @return true if it can be an int, false otherwise
	 */
	private boolean isInt(String text) {
		try {
			Integer.parseInt(text);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	/**
	 * Saves the appointment created to a file
	 * @throws FileNotFoundException
	 */
	private void save() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(FileName + ".txt"));
		for (Appointment appointment : appt) {
			writer.println(appointment.toString());
		}
		writer.close();
		JOptionPane.showMessageDialog(frame, "Saving Complete!");
		writer.close();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.dispose();
	}

	/**
	 * Loads in the appointments from a file
	 * @throws FileNotFoundException
	 */
	private void load() throws FileNotFoundException {
		ArrayList<Appointment> appointments = new ArrayList<>();
		Scanner fileRead = new Scanner(new File(FileName + ".txt"));
		fileRead.useDelimiter("\\n");
		while (fileRead.hasNext()) {
			String appointmentString = fileRead.next();
			String apptType = appointmentString.substring(0, 1);
			String description = appointmentString.substring(appointmentString.indexOf("[") + 1,
					appointmentString.indexOf(" "));
			int month = Integer.parseInt(appointmentString.substring(appointmentString.lastIndexOf(" ") + 1,
					appointmentString.indexOf("/")));
			int day = Integer.parseInt(appointmentString.substring(appointmentString.indexOf("/") + 1,
					appointmentString.lastIndexOf("/")));
			int year = Integer.parseInt(appointmentString.substring(appointmentString.lastIndexOf("/") + 1,
					appointmentString.indexOf("]")));

			if (apptType.substring(0, 1).equals("M")) {
				Appointment appointment = new Monthly(description, month, day, year);
				appointments.add(appointment);
			}

			else if (apptType.substring(0, 1).equals("D")) {
				Appointment appointment = new Daily(description, month, day, year);
				appointments.add(appointment);
			}

			else {
				Appointment appointment = new Onetime(description, month, day, year);
				appointments.add(appointment);
			}
		}
		fileRead.close();
	}

	public class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == returnBtn) {
				frame.dispose();
				new AppointmentGUI(appt);
			} else if (e.getSource() == addApptBtn) {
				try {
					load();
				} catch (FileNotFoundException e1) {
					
				}

				if (readText()) {

					try {
						save();
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "File Not Found!");
					}

					new AppointmentGUI(appt);

				}
			}
		}
	}
}
