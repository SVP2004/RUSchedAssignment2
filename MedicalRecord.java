public class MedicalRecord {
    private Patient[] patients;
    private int size; // number of patient objects in the array

    /**
     * Constructor for the MedicalRecord class.
     */
    public MedicalRecord() {
        patients = new Patient[4]; // Initial capacity of 4
        size = 0;
    }

    /**
     * Adds a patient to the medical record.
     * @param patient The patient to add.
     */
    public void add(Patient patient) {
        if (size == patients.length) {
            grow();
        }
        patients[size++] = patient;
    }

    /**
     * Helper method to increase the capacity of the array.
     */
    private void grow() {
        Patient[] newPatients = new Patient[patients.length * 2];
        for (int i = 0; i < size; i++) {
            newPatients[i] = patients[i];
        }
        patients = newPatients;
    }

    /**
     * Finds a patient in the medical record.
     * @param profile The profile of the patient to find.
     * @return The patient if found, null otherwise.
     */
    public Patient findPatient(Profile profile) {
        for (int i = 0; i < size; i++) {
            if (patients[i].getProfile().equals(profile)) {
                return patients[i];
            }
        }
        return null;
    }
}