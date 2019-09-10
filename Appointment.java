/**
 * Implements an Appointment parent classes that has 3 other child classes.
 * Appointment object is built from a description, and a date which is made of a
 * month, day, and year. There are simple getters and setters in this class.
 * There is also an occursOn() that checks what appointments occurs on a
 * specified date.
 * 
 * @author Kareem Ghumrawi
 *
 */

public class Appointment {

	private String description;
	private int month;
	private int day;
	private int year;

	/**
	 * Constructs an Appointments with 4 parameters, description, and the month,
	 * day, and year of the appointment
	 * 
	 * @param description the description of the appointment, what you are going for
	 * @param month       the month the appointment is on
	 * @param day         the day the appointment is on
	 * @param year        the year the appointment is in
	 */
	public Appointment(String description, int month, int day, int year) {
		if ((month <= 0 && month > 12) || day <= 0 || year <= 0) {
			throw new IllegalArgumentException("Illegal month, day, or year, try again.");
		}
		this.description = description;
		this.month = month;
		this.day = day;
		this.year = year;
	}

	/**
	 * Simple copy constructor that takes another Appointment and makes one just
	 * like with the same description and date
	 * 
	 * @param other the Appointment object you are copying
	 */
	public Appointment(Appointment other) {
		this(other.description, other.month, other.day, other.year);

	}

	/**
	 * No parameter Appointment constructor that its description is empty and the
	 * month and day are 0, and the year is -1. All can be set using setters.
	 */
	public Appointment() {
		this.description = "";
		this.month = 0;
		this.day = 0;
		this.year = -1;
	}

	/**
	 * Simple getter of the description of the Appointment
	 * 
	 * @return the description of the appointment
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Simple getter of the month of the Appointment
	 * 
	 * @return the month of the appointment
	 */
	public int getMonth() {
		return this.month;
	}

	/**
	 * Simple getter of the day of the Appointment
	 * 
	 * @return the day of the appointment
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * Simple getter of the year of the Appointment
	 * 
	 * @return the year of the appointment
	 */
	public int getYear() {
		return this.year;
	}

	/**
	 * Simple getter of the date of the Appointment
	 * 
	 * @return the date of the appointment which includes the month, day, and year
	 */
	public String getDate() {
		return this.month + "/" + this.day + "/" + this.year;
	}

	/**
	 * Simple setter of the date of the Appointment
	 * 
	 * @param month the month of the Appointment
	 * @param day   the day of the appointment
	 * @param year  the year of the appointment
	 */
	public void setDate(int month, int day, int year) {
		if (month <= 0 || day <= 0 || year <= 0) {
			throw new IllegalArgumentException("Illegal month, day, or year, try again.");
		}

		this.month = month;
		this.day = day;
		this.year = year;
	}

	/**
	 * Simple setter of the description of the Appointment
	 * 
	 * @param description the description of the appointment
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks whether an appointment occurs on a certain date. Returns a boolean
	 * 
	 * @param month the month you want to check
	 * @param day   the day you want to check
	 * @param year  the year you want to check
	 * @return true if the appointment occurs on that date, false otherwise
	 */
	public boolean occursOn(int month, int day, int year) {
		if (this.month == month && this.day == day && this.year == year) {
			return true;
		}

		return false;
	}

	@Override
	/**
	 * Basic toString() that gives back the description and the date the Appointment
	 * occurs on.
	 */
	public String toString() {
		return "[" + description + " on " + month + "/" + day + "/" + year + "]";
	}

}
