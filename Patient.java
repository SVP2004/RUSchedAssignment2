public class Patient implements Comparable<Patient> {
    private Profile profile;
    private Visit visits; // a linked list of visits (completed appt.)

    /**
     * Constructor for the Patient class.
     * @param profile The patient's profile.
     */
    public Patient(Profile profile) {
        this.profile = profile;
        this.visits = null;
    }

    /**
     * Adds a completed appointment to the patient's visit history.
     * @param appointment The completed appointment to add.
     */
    public void addVisit(Appointment appointment) {
        Visit newVisit = new Visit(appointment);
        if (visits == null) {
            visits = newVisit;
        } else {
            Visit current = visits;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newVisit);
        }
    }

    /**
     * Calculates the total charge for all visits.
     * @return The total charge for all visits.
     */
    public int charge() {
        int totalCharge = 0;
        Visit current = visits;
        while (current != null) {
            totalCharge += current.getAppointment().getProvider().getSpecialty().getCharge();
            current = current.getNext();
        }
        return totalCharge;
    }

    /**
     * Getter for the patient's profile.
     * @return The patient's profile.
     */
    public Profile getProfile() {
        return profile;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Patient patient = (Patient) obj;
        return profile.equals(patient.profile);
    }

    @Override
    public int compareTo(Patient other) {
        return this.profile.compareTo(other.profile);
    }

    @Override
    public String toString() {
        return profile.toString();
    }
}