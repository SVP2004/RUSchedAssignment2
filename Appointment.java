
/**
 * Represents an appointment with a patient and a provider at a specific date and timeslot.
 */

public class Appointment implements Comparable<Appointment> {

    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;
    protected Person provider;

    /**
     * Constructor for the Appointment class.
     * @param date The date of the appointment.
     * @param timeslot The timeslot of the appointment.
     * @param patient The patient's profile.
     * @param provider The provider for the appointment.
     */
    public Appointment(Date date, Timeslot timeslot, Profile patient, Provider provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Getter for date.
     * @return The appointment date.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for timeslot.
     * @return The appointment timeslot.
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }


    /**
     * Getter for patient profile.
     * @return The patient's profile.
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Getter for provider.
     * If provider is null, return a default message instead of causing a NullPointerException.
     * @return The appointment provider or a default value if null.
     */
    public Person getProvider() {
        if (provider == null) {

            return null;
        }
        return provider;
    }


    /**
     * Setter for timeslot (needed for rescheduling).
     * @param timeslot The new timeslot.
     */
    public void setTimeslot(Timeslot timeslot) {
        this.timeslot = timeslot;
    }

    /**
     * Checks if this appointment is equal to another object.
     * @param obj The object to compare to.
     * @return true if the object is an Appointment and has the same date, timeslot, and patient; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if( obj instanceof Appointment ){
            Appointment appointment = (Appointment) obj;
            return (this.date != null ? this.date.equals(appointment.date) : appointment.date == null)
                    && (this.timeslot != null ? this.timeslot.equals(appointment.timeslot) : appointment.timeslot == null)
                    && (this.patient != null ? this.patient.equals(appointment.patient) : appointment.patient == null)
                    && (this.provider != null ? this.provider.equals(appointment.provider) : appointment.provider == null);
        }
        return false;
    }


    /**
     * Compares this appointment with another appointment for order.
     * @param otherAppointment The appointment to compare to.
     * @return A negative integer, zero, or a positive integer as this appointment is less than, equal to, or greater than the specified appointment.
     */
    @Override
    public int compareTo(Appointment otherAppointment) {
        int dateComparison = this.date.compareTo(otherAppointment.date);
        if (dateComparison != 0) {
            return dateComparison;
        }
        return this.timeslot.compareTo(otherAppointment.timeslot);
    }


    /**
     * Returns a string representation of the appointment.
     * @return A formatted string containing the date, timeslot, patient, and provider.
     */
    @Override
    public String toString() {
        return String.format("%s %s %s [%s]", date, timeslot, patient, provider);
    }

    // Testbed main
    /**
     * Main method for testing the Appointment class.
     * It runs several test cases to verify the behavior of the equals,
     * compareTo, and toString methods.
     */
    public static void main(String[] args) {
        // Test case 1: Equality
        Date date1 = new Date(2024, 9, 30);
        Profile patient1 = new Profile("John", "Doe", new Date(1989, 12, 13));
        Appointment appt1 = new Appointment(date1, Timeslot.SLOT1, patient1, Provider.PATEL);
        Appointment appt2 = new Appointment(date1, Timeslot.SLOT1, patient1, Provider.PATEL);
        System.out.println("Test case 1: appt1 equals appt2: " + appt1.equals(appt2));

        // Test case 2: Comparison (different date)
        Date date2 = new Date(2024, 10, 1);
        Appointment appt3 = new Appointment(date2, Timeslot.SLOT1, patient1, Provider.PATEL);
        System.out.println("Test case 2: appt1 compareTo appt3: " + appt1.compareTo(appt3));

        // Test case 3: Comparison (same date, different timeslot)
        Appointment appt4 = new Appointment(date1, Timeslot.SLOT2, patient1, Provider.PATEL);
        System.out.println("Test case 3: appt1 compareTo appt4: " + appt1.compareTo(appt4));

        // Test case 4: toString
        System.out.println("Test case 4: appt1 toString: " + appt1);
    }
}