import java.util.Scanner;

public class Scheduler {
    private List appointmentList;
    private MedicalRecord medicalRecord;
    private Scanner scanner;

    public Scheduler() {
        appointmentList = new List();
        medicalRecord = new MedicalRecord();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Scheduler is running.");
        while (true) {
            String input = null;
            if(!scanner.hasNextLine()) {
                break;
            }
            input = scanner.nextLine().trim();

            if(input.isEmpty()) {
                continue;
            }
            String[] tokens = input.split(",");
            String command = tokens[0].trim();
            switch (command) {
                case "S":
                    scheduleAppointment(tokens);
                    break;
                case "C":
                    cancelAppointment(tokens);
                    break;
                case "R":
                    rescheduleAppointment(tokens);
                    break;
                case "PA":
                    printAppointments();
                    break;
                case "PP":
                    printPatients();
                    break;
                case "PL":
                    printLocations();
                    break;
                case "PS":
                    printBillingStatements();
                    break;
                case "Q":
                    System.out.println("Scheduler is terminated.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    private void scheduleAppointment(String[] tokens) {
        String dateStr = tokens[1].trim();
        String slotNumStr = tokens[2].trim();
        String fname = tokens[3].trim();
        String lname = tokens[4].trim();
        String dobStr = tokens[5].trim();
        String providerName = tokens[6].trim();

        if(!(slotNumStr.equals("1") || slotNumStr.equals("2") ||
                slotNumStr.equals("3") || slotNumStr.equals("4") ||
                slotNumStr.equals("5") || slotNumStr.equals("6"))){
            System.out.println(slotNumStr + " is not a valid time slot.");
            return;
        }
        int slotNum = Integer.parseInt(slotNumStr);

        Date date = parseDate(dateStr);
        Date dob = parseDate(dobStr);
        Timeslot timeslot = Timeslot.values()[slotNum - 1];
        Profile profile = new Profile(fname, lname, dob);
        Provider provider = null;

        try{
            provider = Provider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e){
            System.out.println(providerName + " - provider doesn't exist.");
            return;
        }

        if (!date.isValid()) {
            System.out.println("Appointment date: " + dateStr + " is not a valid calendar date.");
            return;
        }

        if (date.isBeforeOrEqualToToday()) {
            System.out.println("Appointment date: " + dateStr + " is today or a date before today.");
            return;
        }

        if (date.isWeekend()) {
            System.out.println("Appointment date: " + dateStr + " is Saturday or Sunday.");
            return;
        }

        if (!date.isWithinSixMonths()) {
            System.out.println("Appointment date: " + dateStr + " is not within six months.");
            return;
        }

        if(dob.isAfterOrEqualToToday()){
            System.out.println("Patient dob: "+ dobStr + " is today or a date after today.");
            return;
        }
        if(!dob.isValid()){
            System.out.println("Patient dob: " + dobStr + " is not a valid calendar date.");
            return;
        }
        if(hasExistingAppointment(date, timeslot, profile , provider)){
            System.out.println(profile.getFirstName() + " " + profile.getLastName() + " " + profile.getDob() + " has an existing appointment at the same time slot.");
            return;
        }
        if(!isProviderAvailable(date, timeslot, provider)) {
            System.out.println("[" + provider + "] is not available at slot " + (timeslot.ordinal() + 1) + ".");
            return;
        }

        Appointment appointment = new Appointment(date, timeslot, profile, provider);
        if (appointmentList.contains(appointment)) {
            System.out.println("Appointment already exists!");
            return;
        }

        appointmentList.add(appointment);
        System.out.println(appointment + " booked.");
    }

    private void cancelAppointment(String[] tokens) {
        String dateStr = tokens[1].trim();
        int slotNum = Integer.parseInt(tokens[2].trim());
        String fname = tokens[3].trim();
        String lname = tokens[4].trim();
        String dobStr = tokens[5].trim();
        String providerName = tokens[6].trim();

        Provider provider = null;

        try{
            provider = Provider.valueOf(providerName.toUpperCase());
        } catch (IllegalArgumentException e){
            System.out.println(providerName + " - provider doesn't exist.");
            return;
        }


        Date date = parseDate(dateStr);
        Date dob = parseDate(dobStr);
        Timeslot timeslot = Timeslot.values()[slotNum - 1];
        Profile profile = new Profile(fname, lname, dob);

        Appointment appointmentToCancel = new Appointment(date, timeslot, profile, provider);
        if (appointmentList.contains(appointmentToCancel)) {
            appointmentList.remove(appointmentToCancel);
            System.out.println(dateStr + " " + timeslot.toString() + " " + fname + " " + lname + " " + dobStr + " has been canceled.");
        } else {
            System.out.println(dateStr + " " + timeslot.toString() + " " + fname + " " + lname + " " + dobStr + " does not exist.");
        }
    }

    private void rescheduleAppointment(String[] tokens) {
        String dateStr = tokens[1].trim();
        int oldSlotNum = Integer.parseInt(tokens[2].trim());
        String fname = tokens[3].trim();
        String lname = tokens[4].trim();
        String dobStr = tokens[5].trim();
        int newSlotNum = Integer.parseInt(tokens[6].trim());

        Date date = parseDate(dateStr);
        Date dob = parseDate(dobStr);

        Timeslot oldTimeslot = Timeslot.values()[oldSlotNum - 1];
        Profile profile = new Profile(fname, lname, dob);


        //find the appointment based on the date,time,
        Appointment existingAppointment = null;
        for (int i = 0; i < appointmentList.getSize(); i++) {
            Appointment appointment = appointmentList.getAppointment(i);
            if (appointment.getDate().equals(date) &&
                    appointment.getTimeslot().equals(oldTimeslot) &&
                    appointment.getPatient().equals(profile)) {
                existingAppointment = appointment;
                break;
            }
        }

        if (existingAppointment == null) {
            System.out.println(dateStr + " " + oldTimeslot + " " + fname + " " + lname + " " + dobStr + " does not exist.");
            return;
        }
        Provider provider = existingAppointment.getProvider();

        if (newSlotNum < 1 || newSlotNum > 6) {
            System.out.println( newSlotNum + " is not a valid time slot.");
            return;
        }
        Timeslot newTimeslot = Timeslot.values()[newSlotNum - 1];


        if (!date.isValid()) {
            System.out.println("Appointment date: " + dateStr + " is not a valid calendar date.");
            return;
        }

        if (date.isBeforeOrEqualToToday()) {
            System.out.println("Appointment date: " + dateStr + " is today or a date before today.");
            return;
        }

        if (date.isWeekend()) {
            System.out.println("Appointment date: " + dateStr + " is Saturday or Sunday.");
            return;
        }

        if (!date.isWithinSixMonths()) {
            System.out.println("Appointment date: " + dateStr + " is not within six months.");
            return;
        }

        if(dob.isAfterOrEqualToToday()){
            System.out.println("Patient dob: "+ dobStr + " is today or a date after today.");
            return;
        }
        if(!dob.isValid()){
            System.out.println("Patient dob: " + dobStr + " is not a valid calendar date.");
            return;
        }

        Appointment newAppointment = new Appointment(date, newTimeslot, profile,  provider);

        if (appointmentList.contains(newAppointment)) {
            System.out.println(fname + " " + lname + " " + dobStr + " has an existing appointment at the new time slot.");
            return;
        }

        if (!isProviderAvailable(date, newTimeslot, provider)) {
            System.out.println("[" + provider + "] is not available at slot " + newSlotNum + ".");
            return;
        }

        appointmentList.remove(existingAppointment);
        appointmentList.add(newAppointment);
        System.out.println("Rescheduled to " + dateStr + " " + newTimeslot + " " + fname + " " + lname + " " + dobStr + " [" + provider + "]");
    }


    private void printAppointments() {
        appointmentList.printByAppointment();
    }

    private void printPatients() {
        appointmentList.printByPatient();
    }

    private void printLocations() {
        appointmentList.printByLocation();
    }

    private void printBillingStatements() {
        appointmentList.printBillingStatements();
    }

    private boolean isProviderAvailable(Date date, Timeslot timeslot, Provider provider) {
        for(int i = 0; i<appointmentList.getSize(); i++){
            //we need to get the appointment for each index and then we should then check of there is a appointment at that time slot with that provider on that date
            Appointment indexedAppointment = appointmentList.getAppointment(i);
            if (indexedAppointment.getDate().equals(date) && indexedAppointment.getTimeslot().equals(timeslot) && indexedAppointment.getProvider().equals(provider)){
                return false;
            }
        }
        return true;
    }

    private boolean hasExistingAppointment(Date date, Timeslot timeslot, Profile profile, Provider provider) {
        Appointment tempAppointment = new Appointment(date, timeslot, profile, provider);
        return appointmentList.contains(tempAppointment);
    }


    private Date parseDate(String dateStr) {
        String[] parts = dateStr.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return new Date(year, month, day);
    }

    private boolean isValidAppointmentDate(Date date) {
        return date.isValid();
    }

    private boolean isValidDateOfBirth(Date dob) {
        return dob.isValid();
    }
}