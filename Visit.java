public class Visit {
    private Appointment appointment;
    private Visit next;

    /**
     * Constructor for the Visit class.
     * @param appointment The completed appointment.
     */
    public Visit(Appointment appointment) {
        this.appointment = appointment;
        this.next = null;
    }

    /**
     * Getter for the appointment.
     * @return The appointment associated with this visit.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Getter for the next visit in the linked list.
     * @return The next visit.
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Setter for the next visit in the linked list.
     * @param next The next visit to set.
     */
    public void setNext(Visit next) {
        this.next = next;
    }
}