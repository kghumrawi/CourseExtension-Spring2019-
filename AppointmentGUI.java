import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AppointmentGUI extends JFrame {

	private JFrame frame;
	private JButton addBtn;
	private JButton occurBtn;
	private JButton loadBtn;
	private JPanel buttonPanel;
	private JLabel label;
	private buttonListener listener;
	private ArrayList<Appointment> appt;
	private static final String FileName = "appointmentsFile";

	/**
	 * Constructs a GUI by setting up the 3 main components, the frame, the south label, and the central buttons
	 * @param appt the ArrayList of appointments
	 */
	public AppointmentGUI(ArrayList<Appointment> appt) {
		super();
		this.appt = appt;
		listener = new buttonListener();

		labelSetup();
		buttonPanelSetup();
		frameSetup();
	}

	/**
	 * Sets up the frame
	 */
	private void frameSetup() {
		frame = new JFrame("Appointments");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(400, 400, 100, 100);
		frame.setLayout(new BorderLayout());
		frame.add(buttonPanel, BorderLayout.CENTER);
		frame.add(label, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Sets up the label on bottom of frame
	 */
	private void labelSetup() {
		label = new JLabel(this.appt.size() + " total appointments");
		label.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/**
	 * Sets up the panel that will hold buttons in middle of frame
	 */
	private void buttonPanelSetup() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JPanel addBtnPanel = new JPanel();
		addBtn = new JButton("Add Appointment");
		addBtnPanel.add(addBtn);
		JPanel occurBtnPanel = new JPanel();
		occurBtn = new JButton("Check Appointment Occurences");
		occurBtnPanel.add(occurBtn);
		JPanel loadBtnPanel = new JPanel();
		loadBtn = new JButton("Load Appointments");
		loadBtnPanel.add(loadBtn);

		buttonPanel.add(addBtnPanel);
		buttonPanel.add(occurBtnPanel);
		buttonPanel.add(loadBtnPanel);

		addBtn.addActionListener(listener);
		occurBtn.addActionListener(listener);
		loadBtn.addActionListener(listener);
	}

	/**
	 * loads in appointments from a file. Reads in text and determines what appointments type it is, the description
	 * and its date
	 * @throws FileNotFoundException
	 */
	private void load() throws FileNotFoundException {
		File f = new File(FileName + ".txt");
		if (f.exists()) {
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
			loadCompleteScreen(appointments);
			frame.dispose();
			new AppointmentGUI(appointments);
		}
		else {
			JOptionPane.showMessageDialog(frame, "File Not Found");
		}
	}

	/**
	 * Displays screen that loading is complete
	 * @param appointments ArrayList of appointments
	 */
	private void loadCompleteScreen(ArrayList<Appointment> appointments) {
		JOptionPane.showMessageDialog(frame, "Loading Complete!" + apptList(appointments));
	}
	
	/**
	 * Prints a list of appointments
	 * @param a the ArrayList of appointments
	 * @return the string of the appointments in a list
	 */
	private String apptList(ArrayList<Appointment> a) {
		String list = "";
		for (Appointment ap : a) {
			list += "\n" + ap.toString();
		}
		return list;
	}

	public class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == addBtn) {
				frame.dispose();
				new AddApptGUI(appt);
			} else if (e.getSource() == occurBtn) {
				frame.dispose();
				new OccurApptGUI(appt);
			} else if (e.getSource() == loadBtn) {
				try {
					load();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "Appointments not found!");
				}
			}
		}
	}
}
