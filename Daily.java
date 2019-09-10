/**
 * Implements a child class of Appointment.
 * 
 * @author Kareem Ghumrawi
 *
 */

public class Daily extends Appointment {
	
	/**
	 * Constructs a Daily appointment
	 * 
	 * @param description the description of the appointment
	 * @param month       the month of the appointment
	 * @param day         the day of the appointment
	 * @param year        the year of the appointment
	 */
	public Daily(String description, int month, int day, int year) {
		super(description, month, day, year);
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
		if(this.getYear() > year) {
			return false;
		}
		else if(this.getYear() < year) {
			return true;
		}
		else {
			if(this.getMonth() > month) {
				return false;
			}
			else if(this.getMonth() < month) {
				return true;
			}
			else {
				if(this.getDay() <= day) {
					return true;
				}
				
				return false;
			}
		}
	}
	
	/**
	 * Basic toString() that calls the parent class toString() but adds Daily to
	 * it to let users know what kind of appointment it is
	 */
	public String toString() {
		return "Daily" + super.toString();
	}
}


