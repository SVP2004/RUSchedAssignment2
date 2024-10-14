import java.util.Calendar;

/**
 * Represents a date with year, month, and day.
 * Provides methods for validating dates and checking relationships with other dates.
 */
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;

    // Constants for date validation
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    /**
     * Constructor for the Date class.
     * @param year The year of the date.
     * @param month The month of the date (1-12).
     * @param day The day of the date.
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }



    /**
     * Checks if the date is a valid calendar date.
     * @return true if the date is valid, false otherwise.
     */
    public boolean isValid() {

        //simple error handling for days and months
        if (this.year < 1 || this.month < 1 || this.month > 12 || this.day < 1) {
            return false;
        }

        int[] daysInMonth = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //im just adding 0 so I dont need to worry about subrtacting 1 from index later

        if (isLeapYear() && month == 2) {
            return day <= 29;
        }

        return day <= daysInMonth[month];
    }

    /**
     * Checks if the year is a leap year.
     * @return true if it's a leap year, false otherwise.
     */
    private boolean isLeapYear() {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAL == 0;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if the date is today or a date before today.
     * @return true if it's today or a past date, false otherwise.
     */
    public boolean isBeforeOrEqualToToday() {
        Calendar today = Calendar.getInstance();
        Calendar thisDate = Calendar.getInstance();
        thisDate.set(year, month - 1, day);
        return thisDate.compareTo(today) <= 0;
    }

    /**
     * Checks if the date, used for date of birth, is today or a date after today.
     * @return true if it's today or a date after, false otherwise.
     */
    public boolean isAfterOrEqualToToday() {
        Calendar today = Calendar.getInstance();
        Calendar thisDate = Calendar.getInstance();
        thisDate.set(year, month - 1, day);
        return thisDate.compareTo(today) >= 0;
    }

    /**
     * Checks if the date is a weekend (Saturday or Sunday).
     * @return true if it's a weekend, false otherwise.
     */
    public boolean isWeekend() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }

    /**
     * Checks if the date is within six months from today.
     * @return true if it's within six months, false otherwise.
     */
    public boolean isWithinSixMonths() {
        Calendar today = Calendar.getInstance();
        Calendar sixMonthsLater = (Calendar) today.clone();
        sixMonthsLater.add(Calendar.MONTH, 6);

        Date todayDate = new Date(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH));
        Date sixMonthsLaterDate = new Date(sixMonthsLater.get(Calendar.YEAR), sixMonthsLater.get(Calendar.MONTH) + 1, sixMonthsLater.get(Calendar.DAY_OF_MONTH));

        return this.compareTo(todayDate) >= 0 && this.compareTo(sixMonthsLaterDate) <= 0;
    }

    /**
     * Returns a string representation of the date in the format MM/DD/YYYY.
     * @return the formatted date string.
     */
    @Override
    public String toString() {
        return String.format("%d/%d/%04d", month, day, year);
    }

    /**
     * Checks if this date is equal to another object.
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Date date = (Date) obj;
        return year == date.year && month == date.month && day == date.day;
    }

    /**
     * Compares this date with another date.
     * @param other The date to compare with.
     * @return a negative integer, zero, or a positive integer as this date is before, equal to, or after the specified date.
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        }
        if (this.month != other.month) {
            return Integer.compare(this.month, other.month);
        }
        return Integer.compare(this.day, other.day);
    }

    // Getter methods

    /**
     * Gets the year of the date.
     * @return the year.
     */
    public int getYear() { return year; }

    /**
     * Gets the month of the date.
     * @return the month.
     */
    public int getMonth() { return month; }

    /**
     * Gets the day of the date.
     * @return the day.
     */
    public int getDay() { return day; }

    // Testbed main

    /**
     * Main method for testing the Date class.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Test case 1
        Date testDate1 = new Date(1989, 12, 32);
        System.out.println("Test case 1: " + testDate1.isValid()); // Expected: false

        // Test case 2
        Date testDate2 = new Date(2024, 9, 30);
        System.out.println("Test case 2: " + testDate2.isValid()); // Expected: false

        // Test case 3
        Date testDate3 = new Date(2025, 1, 13);
        System.out.println("Test case 3: " + testDate3.isValid()); // Expected: true

        // Test case 4
        Date testDate4 = new Date(2024, 9, 29); // Sunday
        System.out.println("Test case 4: " + testDate4.isValid()); // Expected: false

        // Test case 5
        Date testDate5 = new Date(2022, 4, 2);
        System.out.println("Test case 5: " + testDate5.isValid()); // Expected: false

        // Test case 6
        Date testDate6 = new Date(2024, 10, 11);
        System.out.println("Test case 6: " + testDate6.isValid()); // Expected: true
    }
}