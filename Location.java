/**
 * Represents geographical locations with associated county and zip code.
 * Each location is defined by its name, county, and zip code.
 * @author Shreyas Santosh, Sahil Patel
 */

public enum Location {
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    private final String county;
    private final String zip;

    /**
     * Constructor for the Location enum.
     * Initializes the county and zip code for the location.
     * @param county The county associated with the location.
     * @param zip The zip code associated with the location.
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Retrieves the county of the location.
     * @return The county of the location.
     */
    public String getCounty() {
        return county;
    }

    /**
     * Retrieves the zip code of the location.
     * @return The zip code of the location.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Returns a string representation of the location.
     * The format is: Name, County ZipCode.
     * @return A formatted string representing the location.
     */
    @Override
    public String toString() {
        return String.format("%s, %s %s", name(), county, zip);
    }
}