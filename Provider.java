public enum Provider {
    PATEL("PATEL", Location.BRIDGEWATER, Specialty.FAMILY),
    LIM("LIM", Location.BRIDGEWATER, Specialty.PEDIATRICIAN),
    ZIMNES("ZIMNES", Location.CLARK, Specialty.FAMILY),
    HARPER("HARPER", Location.CLARK, Specialty.FAMILY),
    KAUR("KAUR", Location.PRINCETON, Specialty.ALLERGIST),
    TAYLOR("TAYLOR", Location.PISCATAWAY, Specialty.PEDIATRICIAN),
    RAMESH("RAMESH", Location.MORRISTOWN, Specialty.ALLERGIST),
    CERAVOLO("CERAVOLO", Location.EDISON, Specialty.PEDIATRICIAN);


    private final String lastName;
    private final Location location;
    private final Specialty specialty;

    Provider(String lastName, Location location, Specialty specialty) {
        this.lastName = lastName;
        this.location = location;
        this.specialty = specialty;
    }

    public String getLastName() {
        return lastName;
    }

    public Location getLocation() {
        return location;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", lastName, location, specialty);
    }
}