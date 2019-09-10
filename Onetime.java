/**
 * Implements a child class of Appointment.
 * 
 * @author Kareem Ghumrawi
 *
 */

public class Onetime extends Appointment {
	
	/**
	 * Constructs a Onetime appointment
	 * 
	 * @param description the description of the appointment
	 * @param month       the month of the appointment
	 * @param day         the day of the appointment
	 * @param year        the year of the appointment
	 */
	public Onetime(String description, int month, int day, int year) {
		super(description, month, day, year);
		
	}
	
	/**
	 * Basic toString() that calls the parent class toString() but adds Onetime to
	 * it to let users know what kind of appointment it is
	 */
	public String toString() {
		return "Onetime" + super.toString();
	}
	
}
