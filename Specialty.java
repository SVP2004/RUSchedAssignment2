/**
 * Represents the available medical specialties.
 * @author Shreyas Santosh
 */
public enum Specialty {
    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    private final int charge;

    /**
     * Constructs a new Specialty with the given charge.
     * @param charge The charge per visit for this specialty
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Gets the charge per visit for this specialty.
     * @return The charge
     */
    public int getCharge() {
        return charge;
    }
}