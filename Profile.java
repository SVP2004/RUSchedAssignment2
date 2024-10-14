import java.util.Calendar;

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor for the Profile class.
     * @param fname The first name of the patient.
     * @param lname The last name of the patient.
     * @param dob The date of birth of the patient.
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Getter for first name.
     * @return The first name.
     */
    public String getFirstName() {
        return fname;
    }

    /**
     * Getter for last name.
     * @return The last name.
     */
    public String getLastName() {
        return lname;
    }

    /**
     * Getter for date of birth.
     * @return The date of birth.
     */
    public Date getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Profile profile = (Profile) obj;
        return fname.equals(profile.fname) &&
                lname.equals(profile.lname) &&
                dob.equals(profile.dob);
    }

    @Override
    public int compareTo(Profile other) {
        // Compare last names
        int lastNameComparison = this.lname.compareTo(other.lname);
        if (lastNameComparison != 0) {
            return Integer.signum(lastNameComparison); // Return -1, 0, or 1
        }

        // If last names are the same, compare first names
        int firstNameComparison = this.fname.compareTo(other.fname);
        if (firstNameComparison != 0) {
            return Integer.signum(firstNameComparison); // Return -1, 0, or 1
        }

        // If first names are the same, compare dates of birth
        return Integer.signum(this.dob.compareTo(other.dob)); // Return -1, 0, or 1
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", fname, lname, dob);
    }

    // Testbed main
        public static void main(String[] args) {
            // Test Case 1: Both profiles are identical (expected: 0)
            Profile profile1 = new Profile("Mary", "Smith", new Date(2020, 11, 19));
            Profile profile2 = new Profile("Mary", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 1: " + profile1.compareTo(profile2)); // Expected: 0

            // Test Case 2: profile3 comes before profile4 (expected: -1)
            Profile profile3 = new Profile("Amy", "Smith", new Date(2020, 11, 19));
            Profile profile4 = new Profile("Bob", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 2: " + profile3.compareTo(profile4)); // Expected: -1

            // Test Case 3: profile5 comes before profile6 (expected: -1)
            Profile profile5 = new Profile("Amy", "Adam", new Date(2020, 11, 19));
            Profile profile6 = new Profile("Bob", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 3: " + profile5.compareTo(profile6)); // Expected: -1

            // Test Case 4: profile7 comes before profile8 (expected: -1)
            Profile profile7 = new Profile("Amy", "Adam", new Date(2020, 11, 19));
            Profile profile8 = new Profile("Bob", "Smith", new Date(2021, 11, 19));
            System.out.println("Test case 4: " + profile7.compareTo(profile8)); // Expected: -1

            // Test Case 5: profile9 comes after profile10 (expected: 1)
            Profile profile9 = new Profile("Eve", "Smith", new Date(2020, 11, 19));
            Profile profile10 = new Profile("Alice", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 5: " + profile9.compareTo(profile10)); // Expected: 1

            // Test Case 6: profile11 comes after profile12 (expected: 1)
            Profile profile11 = new Profile("Frank", "Johnson", new Date(2020, 11, 19));
            Profile profile12 = new Profile("Bob", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 6: " + profile11.compareTo(profile12)); // Expected: 1

            // Test Case 7: profile13 comes after profile14 (expected: 1)
            Profile profile13 = new Profile("George", "Brown", new Date(2020, 11, 19));
            Profile profile14 = new Profile("Charlie", "Smith", new Date(2020, 11, 19));
            System.out.println("Test case 7: " + profile13.compareTo(profile14)); // Expected: 1
        }
    }

