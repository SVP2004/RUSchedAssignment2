/**
 * Represents a list of appointments for scheduling.
 * It provides functionalities to add, remove, and print appointments
 * in various orderings (by patient, location, or appointment).
 */

public class List {
    private Appointment[] appointments;
    private int size;

    /**
     * Constructor for the List class.
     * Initializes an empty list with a capacity of 4 appointments.
     */
    public List() {
        appointments = new Appointment[4];
        size = 0;
    }


    /**
     * This is used to get the size of the appointmentLists array
     * @return the size of the list
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Finds a appointment in the List of appointments based on the index given.
     * @param int The index of the appointment we are looking for
     * @return the appoiuntment of the index we are searching
     */
    public Appointment getAppointment (int index) {
        if( index >= 0 || index < appointments.length){
            return appointments[index];
        }
        return null;
    }

    /**
     * Finds the index of a given appointment in the list.
     * @param appointment The appointment to find.
     * @return The index of the appointment if found, -1 otherwise.
     */
    private int find(Appointment appointment) {
        for (int i = 0; i < size; i++) {
            if (appointments[i].equals(appointment)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Increases the capacity of the appointments array by 4.
     */
    private void grow() {
        Appointment[] newAppointments = new Appointment[appointments.length + 4];
        for (int i = 0; i < size; i++) {
            newAppointments[i] = appointments[i];
        }
        appointments = newAppointments;
    }

    /**
     * Checks if the list contains a specific appointment.
     * @param appointment The appointment to check for.
     * @return true if the appointment is in the list, false otherwise.
     */
    public boolean contains(Appointment appointment) {
        return find(appointment) != -1;
    }

    /**
     * Adds an appointment to the list.
     * @param appointment The appointment to add.
     */
    public void add(Appointment appointment) {
        if (size == appointments.length) {
            grow();
        }
        appointments[size++] = appointment;
    }

    /**
     * Removes an appointment from the list.
     * @param appointment The appointment to remove.
     */
    public void remove(Appointment appointment) {
        int index = find(appointment);
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                appointments[i] = appointments[i + 1];
            }
            appointments[--size] = null;
        }
    }

    private void swap(int i, int j) {
        Appointment temp = appointments[i];
        appointments[i] = appointments[j];
        appointments[j] = temp;
    }

    private void sortByPatient() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (comparePatients(appointments[j], appointments[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            swap(i, minIdx);
        }
        sortByDateAfterPatient();
    }

    private void sortByDateAfterPatient() {
        for (int i = 0; i < size - 1; i++) {
            if (appointments[i].getPatient().equals(appointments[i + 1].getPatient())) {
                int j = i + 1;
                while (j < size && appointments[j].getPatient().equals(appointments[i].getPatient())) {
                    j++;
                }
                sortSubArrayByDate(i, j - 1);
                i = j - 1;
            }
        }
    }

    private int comparePatients(Appointment a1, Appointment a2) {
        int lastNameComparison = a1.getPatient().getLastName().compareTo(a2.getPatient().getLastName());
        if (lastNameComparison != 0) return lastNameComparison;
        return a1.getPatient().getFirstName().compareTo(a2.getPatient().getFirstName());
    }

    private void sortByLocation() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (compareLocations(appointments[j], appointments[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            swap(i, minIdx);
        }
        sortByDateAfterLocation();
    }

    private void sortByDateAfterLocation() {
        for (int i = 0; i < size - 1; i++) {
            String county = appointments[i].getProvider().getLocation().getCounty();
            int j = i + 1;
            while (j < size && appointments[j].getProvider().getLocation().getCounty().equals(county)) {
                j++;
            }
            sortSubArrayByDate(i, j - 1);
            i = j - 1;
        }
    }

    private int compareLocations(Appointment a1, Appointment a2) {
        return a1.getProvider().getLocation().getCounty().compareToIgnoreCase(a2.getProvider().getLocation().getCounty());
    }

    private void sortByAppointment() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                if (compareAppointments(appointments[j], appointments[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            swap(i, minIdx);
        }
        sortByProviderAfterDate();
    }

    private void sortByProviderAfterDate() {
        for (int i = 0; i < size - 1; i++) {
            if (appointments[i].getDate().equals(appointments[i + 1].getDate()) &&
                    appointments[i].getTimeslot().equals(appointments[i + 1].getTimeslot())) {
                int j = i + 1;
                while (j < size && appointments[j].getDate().equals(appointments[i].getDate()) &&
                        appointments[j].getTimeslot().equals(appointments[i].getTimeslot())) {
                    j++;
                }
                sortSubArrayByProvider(i, j - 1);
                i = j - 1;
            }
        }
    }

    private int compareAppointments(Appointment a1, Appointment a2) {
        int dateComparison = a1.getDate().compareTo(a2.getDate());
        if (dateComparison != 0) return dateComparison;
        return a1.getTimeslot().compareTo(a2.getTimeslot());
    }

    private void sortSubArrayByDate(int start, int end) {
        for (int i = start; i < end; i++) {
            int minIdx = i;
            for (int j = i + 1; j <= end; j++) {
                if (appointments[j].compareTo(appointments[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            swap(i, minIdx);
        }
    }

    private void sortSubArrayByProvider(int start, int end) {
        for (int i = start; i < end; i++) {
            int minIdx = i;
            for (int j = i + 1; j <= end; j++) {
                if (appointments[j].getProvider().getLastName().compareTo(appointments[minIdx].getProvider().getLastName()) < 0) {
                    minIdx = j;
                }
            }
            swap(i, minIdx);
        }
    }

    public void printByPatient() {
        if (size == 0) {
            System.out.println("The schedule calendar is empty.");
            return;
        }
        System.out.println("\n** Appointments ordered by patient/date/time **");
        sortByPatient();
        printAppointments();
        System.out.println("** end of list **");
    }

    public void printByLocation() {
        if (size == 0) {
            System.out.println("The schedule calendar is empty.");
            return;
        }
        System.out.println("\n** Appointments ordered by county/date/time **");
        sortByLocation();
        printAppointments();
        System.out.println("** end of list **");
    }

    public void printByAppointment() {
        if (size == 0) {
            System.out.println("The schedule calendar is empty.");
            return;
        }
        System.out.println("\n** Appointments ordered by date/time/provider **");
        sortByAppointment();
        printAppointments();
        System.out.println("** end of list **");
    }

    private void printAppointments() {
        for (int i = 0; i < size; i++) {
            System.out.println(appointments[i].toString());
        }
    }

    /**
     * Compares two appointments by patient name, then by date and time.
     * @param a1 The first appointment to compare.
     * @param a2 The second appointment to compare.
     * @return A negative integer, zero, or a positive integer as the first appointment
     *         is less than, equal to, or greater than the second.
     */
    private int compareByPatient(Appointment a1, Appointment a2) {
        int lastNameComparison = a1.getPatient().getLastName().compareTo(a2.getPatient().getLastName());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        int firstNameComparison = a1.getPatient().getFirstName().compareTo(a2.getPatient().getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        return a1.compareTo(a2);
    }

    /**
     * Compares two appointments by county, then by date and time.
     * @param a1 The first appointment to compare.
     * @param a2 The second appointment to compare.
     * @return A negative integer, zero, or a positive integer as the first appointment
     *         is less than, equal to, or greater than the second.
     */
    private int compareByLocation(Appointment a1, Appointment a2) {
        Provider provider1 = a1.getProvider();
        Provider provider2 = a2.getProvider();

        // Compare providers
        if (provider1 == null && provider2 == null) return 0;
        if (provider1 == null) return -1;
        if (provider2 == null) return 1;

        // Compare counties
        String county1 = provider1.getLocation() != null ? provider1.getLocation().getCounty() : null;
        String county2 = provider2.getLocation() != null ? provider2.getLocation().getCounty() : null;

        if (county1 == null && county2 == null) return 0;
        if (county1 == null) return -1;
        if (county2 == null) return 1;

        int countyComparison = county1.compareTo(county2);
        return countyComparison != 0 ? countyComparison : a1.compareTo(a2);
    }

    /**
     * Compares two appointments by date, time, and provider name.
     * @param a1 The first appointment to compare.
     * @param a2 The second appointment to compare.
     * @return A negative integer, zero, or a positive integer as the first appointment
     *         is less than, equal to, or greater than the second.
     */
    private int compareByAppointment(Appointment a1, Appointment a2) {
        int dateComparison = a1.getDate().compareTo(a2.getDate());
        if (dateComparison != 0) {
            return dateComparison;
        }
        int timeComparison = a1.getTimeslot().compareTo(a2.getTimeslot());
        if (timeComparison != 0) {
            return timeComparison;
        }
        return a1.getProvider().getLastName().compareTo(a2.getProvider().getLastName());
    }

    /**
     * Prints billing statements for all patients.
     */
    public void printBillingStatements() {
        if (size == 0) {
            System.out.println("No appointments to generate billing statements.");
            return;
        }

        System.out.println("** Billing statement ordered by patient **");

        // Sort appointments by patient
        sortByPatient();

        Profile currentPatient = null;
        double totalCharge = 0;
        int count = 1;

        for (int i = 0; i < size; i++) {
            Appointment appointment = appointments[i];
            Profile patientProfile = appointment.getPatient();

            // If we've moved to a new patient, print the previous patient's statement
            if (currentPatient != null && !currentPatient.equals(patientProfile)) {
                printPatientStatement(currentPatient, totalCharge, count);
                totalCharge = 0;
                count++;
            }

            // Update current patient and add charge
            currentPatient = patientProfile;
            totalCharge += appointment.getProvider().getSpecialty().getCharge();

            // If this is the last appointment, print the last patient's statement
            if (i == size - 1) {
                printPatientStatement(currentPatient, totalCharge, count);
            }
        }

        System.out.println("** end of list **");

        // Clear the appointment list
        clearAppointments();
    }

    /**
     * Helper method to print an individual patient's billing statement in the desired format.
     */
    private void printPatientStatement(Profile patient, double totalCharge, int count) {
        // Format the total charge with commas
        String formattedCharge = String.format("$%,.2f", totalCharge);

        // Print the statement in the desired format
        System.out.printf("(%d) %s %s %s [amount due: %s]\n",
                count,
                patient.getFirstName(),
                patient.getLastName(),
                patient.getDob(),
                formattedCharge);
    }

    /**
     * Helper Method to clear all appointments from the list.
     */
    private void clearAppointments() {
        for (int i = 0; i < size; i++) {
            appointments[i] = null;
        }
        size = 0;
    }

}
