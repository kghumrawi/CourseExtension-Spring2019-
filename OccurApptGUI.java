import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OccurApptGUI extends JFrame {

	private JFrame frame;
	private JButton returnBtn;
	private JPanel returnBtnPanel;
	private JButton checkBtn;
	private JPanel checkPanel;
	private JLabel label;
	private JPanel mainField;
	private JTextField monthText;
	private JTextField dayText;
	private JTextField yearText;
	private buttonListener listener;
	private ArrayList<Appointment> appt;

	/**
	 * Constructs a GUI for user to be able to check which appointments occur on a certain date
	 * @param appt ArrayList of appointments to be used
	 */
	public OccurApptGUI(ArrayList<Appointment> appt) {
		this.appt = appt;
		this.listener = new buttonListener();

		returnBtnSetup();
		mainFieldSetup();
		checkPanelSetup();
		frameSetup();
	}

	/**
	 * Sets up the frame
	 */
	private void frameSetup() {
		frame = new JFrame("Check Appointment Occurences");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(400, 400, 600, 200);
		frame.setLayout(new BorderLayout());
		frame.add(returnBtnPanel, BorderLayout.NORTH);
		frame.add(mainField, BorderLayout.CENTER);
		frame.add(checkPanel, BorderLayout.SOUTH);
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
	 * Sets up the panel that contains the check button on the south side of the frame
	 */
	private void checkPanelSetup() {
		checkPanel = new JPanel();
		checkPanel.setLayout(new BorderLayout());
		checkBtn = new JButton("Check");
		checkPanel.add(checkBtn, BorderLayout.EAST);

		label = new JLabel(this.appt.size() + " total appointments");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		checkPanel.add(label, BorderLayout.CENTER);

		checkBtn.addActionListener(listener);
	}

	/**
	 * Sets up the panel that contains the text fields that is central of the frame
	 */
	private void mainFieldSetup() {
		mainField = new JPanel();
		mainField.setLayout(new FlowLayout());

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

		mainField.add(apptDatePanel);
	}

	/**
	 * Reads in the text the text if it is valid
	 * @return true if the input is valid, false otherwise
	 */
	private void readText() {
		String monthString = monthText.getText();
		String dayString = dayText.getText();
		String yearString = yearText.getText();
		if (isInt(monthString) && isInt(dayString) && isInt(yearString)) {
			int month = Integer.parseInt(monthString);
			int day = Integer.parseInt(dayString);
			int year = Integer.parseInt(yearString);
			int count = 0;
			boolean hasNone = true;
			ArrayList<Appointment> a = new ArrayList<>();

			for (Appointment b : appt) {
				if (b.occursOn(month, day, year)) {
					count++;
					hasNone = false;
					a.add(b);
				}
			}
			if (hasNone) {
				JOptionPane.showMessageDialog(frame,
						count + " appointments exist on " + month + "/" + day + "/" + year);
			} else {
				JOptionPane.showMessageDialog(frame,
						count + " appointments exist on " + month + "/" + day + "/" + year + ":" + apptList(a));
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Invalid Entry! Use numbers for Month, Day, Year");
		}

	}

	/**
	 * Displays the appointments as a list
	 * @param a the ArrayList of appointments
	 * @return the appointments printed in a list 
	 */
	private String apptList(ArrayList<Appointment> a) {
		String list = "";
		for (Appointment ap : a) {
			list += "\n" + ap.toString();
		}
		return list;
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

	public class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == returnBtn) {
				frame.dispose();
				new AppointmentGUI(appt);
			} else if (e.getSource() == checkBtn) {
				readText();
			}
		}
	}
}
