import java.io.*;
import java.util.*;

// ===================
// Base Person class
// ===================
abstract class Person {
    protected int id;
    protected String name;
    protected int age;

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
}

// ===================
// Doctor class
// ===================
class Doctor extends Person {
    private String specialization;
    private double appointmentFee;

    public Doctor(int id, String name, int age, String specialization, double appointmentFee) {
        super(id, name, age);
        this.specialization = specialization;
        this.appointmentFee = appointmentFee;
    }

    public String getSpecialization() { return specialization; }
    public double getAppointmentFee() { return appointmentFee; }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-15s | Age: %-3d | Specialization: %-15s | Fee: $%-6.2f",
                id, name, age, specialization, appointmentFee);
    }
}

// ===================
// Patient class
// ===================
class Patient extends Person {
    private String disease;
    private boolean testRequired;
    private String prescription;

    public Patient(int id, String name, int age, String disease) {
        super(id, name, age);
        this.disease = disease;
        this.testRequired = false;
        this.prescription = "";
    }

    public String getDisease() { return disease; }
    public boolean isTestRequired() { return testRequired; }
    public void setTestRequired(boolean testRequired) { this.testRequired = testRequired; }
    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-15s | Age: %-3d | Disease: %-15s | Test: %-5s | Prescription: %-20s",
                id, name, age, disease, testRequired ? "Yes" : "No", prescription);
    }
}

// ===================
// Staff class
// ===================
class Staff extends Person {
    private String department;
    private String position;
    private double salary;

    public Staff(int id, String name, int age, String department, String position, double salary) {
        super(id, name, age);
        this.department = department;
        this.position = position;
        this.salary = salary;
    }

    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-15s | Age: %-3d | Department: %-15s | Position: %-15s | Salary: $%-8.2f",
                id, name, age, department, position, salary);
    }
}

// ===================
// Appointment class
// ===================
class Appointment {
    private Doctor doctor;
    private Patient patient;
    private String date;
    private boolean isPaid;

    public Appointment(Doctor doctor, Patient patient, String date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.isPaid = false;
    }

    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public String getDate() { return date; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean paid) { isPaid = paid; }
    public double getFee() { return doctor.getAppointmentFee(); }

    @Override
    public String toString() {
        return String.format("Date: %-10s | Dr. %-15s | Patient: %-15s | Fee: $%-6.2f | Paid: %-3s",
                date, doctor.getName(), patient.getName(),
                doctor.getAppointmentFee(), isPaid ? "Yes" : "No");
    }
}

// ===================
// User class (Login)
// ===================
class User {
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}


// Hospital System 

class Hospital {
    private List<Doctor> doctors = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();
    private List<Staff> staffMembers = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    int doctorCounter = 1, patientCounter = 1, staffCounter = 1;

    public Hospital() {
        // Default accounts
        users.add(new User("admin", "admin123", "ADMIN"));
        users.add(new User("user", "user123", "USER"));
        users.add(new User("doctor", "doctor123", "DOCTOR"));
        users.add(new User("staff", "staff123", "STAFF"));
    }

    // Login
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // Add methods
    public void addDoctor(String name, int age, String specialization, double appointmentFee) {
        doctors.add(new Doctor(doctorCounter++, name, age, specialization, appointmentFee));
        System.out.println("Doctor added successfully!");
    }

    public void addPatient(String name, int age, String disease) {
        patients.add(new Patient(patientCounter++, name, age, disease));
        System.out.println("Patient added successfully!");
    }

    public void addStaff(String name, int age, String department, String position, double salary) {
        staffMembers.add(new Staff(staffCounter++, name, age, department, position, salary));
        System.out.println("Staff member added successfully!");
    }

    public void scheduleAppointment(int doctorId, int patientId, String date) {
        Doctor doctor = null;
        Patient patient = null;
        for (Doctor d : doctors) if (d.getId() == doctorId) { doctor = d; break; }
        for (Patient p : patients) if (p.getId() == patientId) { patient = p; break; }

        if (doctor != null && patient != null) {
            appointments.add(new Appointment(doctor, patient, date));
            System.out.println("✅ Appointment scheduled successfully!");
            System.out.println("   Details: Dr. " + doctor.getName() + " with " + patient.getName() +
                    " on " + date + " | Fee: $" + doctor.getAppointmentFee());
        } else {
            System.out.println("❌ Invalid Doctor ID or Patient ID!");
        }
    }

    // Show methods
    public void showDoctors() {
        System.out.println("\n=== LIST OF DOCTORS ===");
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.println("ID   Name            Age  Specialization      Fee");
            System.out.println("-------------------------------------------------");
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
        System.out.println("=======================");
    }

    public void showPatients() {
        System.out.println("\n=== LIST OF PATIENTS ===");
        if (patients.isEmpty()) {
            System.out.println("No patients available.");
        } else {
            System.out.println("ID   Name            Age  Disease           Test  Prescription");
            System.out.println("-------------------------------------------------------------");
            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
        System.out.println("=======================");
    }

    public void showStaff() {
        System.out.println("\n=== LIST OF STAFF MEMBERS ===");
        if (staffMembers.isEmpty()) {
            System.out.println("No staff members available.");
        } else {
            System.out.println("ID   Name            Age  Department        Position         Salary");
            System.out.println("------------------------------------------------------------------");
            for (Staff staff : staffMembers) {
                System.out.println(staff);
            }
        }
        System.out.println("===========================");
    }

    public void showAppointments() {
        System.out.println("\n=== SCHEDULED APPOINTMENTS ===");
        if (appointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            System.out.println("Date       Doctor          Patient         Fee      Paid");
            System.out.println("--------------------------------------------------------");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
        System.out.println("==============================");
    }

    public void showAppointmentFees() {
        System.out.println("\n=== APPOINTMENT FEES ===");
        if (doctors.isEmpty()) {
            System.out.println("No doctors available.");
        } else {
            System.out.println("Doctor            Specialization      Fee");
            System.out.println("-----------------------------------------");
            for (Doctor doctor : doctors) {
                System.out.printf("%-15s   %-15s   $%-6.2f%n",
                        doctor.getName(), doctor.getSpecialization(), doctor.getAppointmentFee());
            }
        }
        System.out.println("=======================");
    }

    // Doctor functions
    public void showDoctorPatients(int doctorId) {
        Doctor doctor = null;
        for (Doctor d : doctors) if (d.getId() == doctorId) { doctor = d; break; }
        if (doctor == null) {
            System.out.println("❌ Doctor not found!");
            return;
        }

        System.out.println("\n=== PATIENTS FOR DR. " + doctor.getName() + " ===");
        boolean hasPatients = false;

        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getId() == doctorId) {
                Patient patient = appointment.getPatient();
                System.out.println(patient);
                hasPatients = true;
            }
        }

        if (!hasPatients) {
            System.out.println("No patients scheduled for this doctor.");
        }
        System.out.println("==================================");
    }

    public void setTestRequired(int patientId, boolean testRequired) {
        Patient patient = null;
        for (Patient p : patients) if (p.getId() == patientId) { patient = p; break; }
        if (patient != null) {
            patient.setTestRequired(testRequired);
            System.out.println("Test requirement updated for patient: " + patient.getName());
        } else {
            System.out.println("❌ Patient not found!");
        }
    }

    public void setPrescription(int patientId, String prescription) {
        Patient patient = null;
        for (Patient p : patients) if (p.getId() == patientId) { patient = p; break; }
        if (patient != null) {
            patient.setPrescription(prescription);
            System.out.println("Prescription updated for patient: " + patient.getName());
        } else {
            System.out.println("❌ Patient not found!");
        }
    }

    public void takeAppointmentFee(int appointmentIndex) {
        if (appointmentIndex >= 0 && appointmentIndex < appointments.size()) {
            Appointment appointment = appointments.get(appointmentIndex);
            appointment.setPaid(true);
            System.out.println("Fee collected for appointment with " + appointment.getPatient().getName() +
                    " on " + appointment.getDate() + " | Amount: $" + appointment.getFee());
        } else {
            System.out.println("❌ Invalid appointment index!");
        }
    }

    // Staff functions
    public void showAllStaff() {
        showStaff();
    }

    public void searchStaffByDepartment(String department) {
        System.out.println("\n=== STAFF IN DEPARTMENT: " + department.toUpperCase() + " ===");
        boolean found = false;
        for (Staff staff : staffMembers) {
            if (staff.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(staff);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No staff members found in " + department + " department.");
        }
        System.out.println("==================================");
    }

    // ---------------- CSV helpers ----------------
    private static String escapeCsv(String field) {
        if (field == null) return "";
        boolean needQuotes = field.contains(",") || field.contains("\"") || field.contains("\n") || field.contains("\r");
        String f = field.replace("\"", "\"\"");
        if (needQuotes) {
            return "\"" + f + "\"";
        } else {
            return f;
        }
    }

    private static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null) return result;
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escaped quote
                    cur.append('"');
                    i++; // skip next
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        result.add(cur.toString());
        return result;
    }

    // ---------------- Save data as CSV ----------------
    public void saveData() {
        try {
            // Doctors
            try (PrintWriter pw = new PrintWriter(new FileWriter("doctors.csv"))) {
                pw.println("ID,Name,Age,Specialization,Fee");
                for (Doctor d : doctors) {
                    pw.printf("%d,%s,%d,%s,%.2f%n",
                            d.getId(),
                            escapeCsv(d.getName()),
                            d.getAge(),
                            escapeCsv(d.getSpecialization()),
                            d.getAppointmentFee());
                }
            }

            // Patients
            try (PrintWriter pw = new PrintWriter(new FileWriter("patients.csv"))) {
                pw.println("ID,Name,Age,Disease,TestRequired,Prescription");
                for (Patient p : patients) {
                    pw.printf("%d,%s,%d,%s,%s,%s%n",
                            p.getId(),
                            escapeCsv(p.getName()),
                            p.getAge(),
                            escapeCsv(p.getDisease()),
                            p.isTestRequired(),
                            escapeCsv(p.getPrescription()));
                }
            }

            // Staff
            try (PrintWriter pw = new PrintWriter(new FileWriter("staff.csv"))) {
                pw.println("ID,Name,Age,Department,Position,Salary");
                for (Staff s : staffMembers) {
                    pw.printf("%d,%s,%d,%s,%s,%.2f%n",
                            s.getId(),
                            escapeCsv(s.getName()),
                            s.getAge(),
                            escapeCsv(s.getDepartment()),
                            escapeCsv(s.getPosition()),
                            s.getSalary());
                }
            }

            // Appointments
            try (PrintWriter pw = new PrintWriter(new FileWriter("appointments.csv"))) {
                pw.println("DoctorID,PatientID,Date,Paid");
                for (Appointment a : appointments) {
                    pw.printf("%d,%d,%s,%s%n",
                            a.getDoctor().getId(),
                            a.getPatient().getId(),
                            escapeCsv(a.getDate()),
                            a.isPaid());
                }
            }

            System.out.println("✅ Data saved to CSV!");
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // ---------------- Load data from CSV ----------------
    public static Hospital loadData() {
        Hospital h = new Hospital();

        // Doctors
        try (Scanner sc = new Scanner(new File("doctors.csv"))) {
            if (sc.hasNextLine()) sc.nextLine(); // skip header
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                List<String> parts = parseCsvLine(line);
                if (parts.size() < 5) continue;
                try {
                    int id = Integer.parseInt(parts.get(0));
                    String name = parts.get(1);
                    int age = Integer.parseInt(parts.get(2));
                    String spec = parts.get(3);
                    double fee = Double.parseDouble(parts.get(4));
                    h.doctors.add(new Doctor(id, name, age, spec, fee));
                } catch (NumberFormatException ignored) {}
            }
        } catch (FileNotFoundException ignored) {}

        // Patients
        try (Scanner sc = new Scanner(new File("patients.csv"))) {
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                List<String> parts = parseCsvLine(line);
                if (parts.size() < 6) continue;
                try {
                    int id = Integer.parseInt(parts.get(0));
                    String name = parts.get(1);
                    int age = Integer.parseInt(parts.get(2));
                    String disease = parts.get(3);
                    boolean testReq = Boolean.parseBoolean(parts.get(4));
                    String prescription = parts.get(5);
                    Patient p = new Patient(id, name, age, disease);
                    p.setTestRequired(testReq);
                    p.setPrescription(prescription);
                    h.patients.add(p);
                } catch (NumberFormatException ignored) {}
            }
        } catch (FileNotFoundException ignored) {}

        // Staff
        try (Scanner sc = new Scanner(new File("staff.csv"))) {
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                List<String> parts = parseCsvLine(line);
                if (parts.size() < 6) continue;
                try {
                    int id = Integer.parseInt(parts.get(0));
                    String name = parts.get(1);
                    int age = Integer.parseInt(parts.get(2));
                    String dept = parts.get(3);
                    String pos = parts.get(4);
                    double sal = Double.parseDouble(parts.get(5));
                    h.staffMembers.add(new Staff(id, name, age, dept, pos, sal));
                } catch (NumberFormatException ignored) {}
            }
        } catch (FileNotFoundException ignored) {}

        // Appointments
        try (Scanner sc = new Scanner(new File("appointments.csv"))) {
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty()) continue;
                List<String> parts = parseCsvLine(line);
                if (parts.size() < 4) continue;
                try {
                    int docId = Integer.parseInt(parts.get(0));
                    int patId = Integer.parseInt(parts.get(1));
                    String date = parts.get(2);
                    boolean paid = Boolean.parseBoolean(parts.get(3));
                    
                    Doctor d = null;
                    Patient p = null;
                    for (Doctor dd : h.doctors) if (dd.getId() == docId) { d = dd; break; }
                    for (Patient pp : h.patients) if (pp.getId() == patId) { p = pp; break; }
                    if (d != null && p != null) {
                        Appointment ap = new Appointment(d, p, date);
                        ap.setPaid(paid);
                        h.appointments.add(ap);
                    }
                } catch (NumberFormatException ignored) {}
            }
        } catch (FileNotFoundException ignored) {}

        // If no staff were loaded, add sample staff (keeps behaviour similar to original)
        if (h.staffMembers.isEmpty()) {
            h.staffMembers.add(new Staff(h.staffCounter++, "John Smith", 35, "Reception", "Receptionist", 35000));
            h.staffMembers.add(new Staff(h.staffCounter++, "Sarah Wilson", 42, "Nursing", "Head Nurse", 55000));
            h.staffMembers.add(new Staff(h.staffCounter++, "Mike Johnson", 28, "Maintenance", "Technician", 40000));
        }

        // Reset counters based on loaded data to avoid ID duplication
        h.doctorCounter = h.doctors.isEmpty() ? h.doctorCounter : h.doctors.stream().mapToInt(Doctor::getId).max().getAsInt() + 1;
        h.patientCounter = h.patients.isEmpty() ? h.patientCounter : h.patients.stream().mapToInt(Patient::getId).max().getAsInt() + 1;
        h.staffCounter = h.staffMembers.isEmpty() ? h.staffCounter : h.staffMembers.stream().mapToInt(Staff::getId).max().getAsInt() + 1;

        return h;
    }

    // Getters
    public List<Doctor> getDoctors() { return doctors; }
    public List<Patient> getPatients() { return patients; }
    public List<Staff> getStaffMembers() { return staffMembers; }
    public List<Appointment> getAppointments() { return appointments; }
}

// ===================
// Main Program
// ===================
public class HospitalSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hospital hospital = Hospital.loadData();

        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM (CSV MODE) ===");
        System.out.println("Login Options:");
        System.out.println("00 - Admin Login");
        System.out.println("01 - User Login");
        System.out.println("02 - Doctor Login");
        System.out.println("03 - Staff Login");
        System.out.print("Enter login option: ");

        String loginOption = sc.nextLine();

        User loggedIn = null;

        if (loginOption.equals("00")) {
            System.out.print("Enter admin username: ");
            String adminUser = sc.nextLine();
            System.out.print("Enter admin password: ");
            String adminPass = sc.nextLine();
            loggedIn = hospital.login(adminUser, adminPass);
        } else if (loginOption.equals("01")) {
            System.out.print("Enter user username: ");
            String userUser = sc.nextLine();
            System.out.print("Enter user password: ");
            String userPass = sc.nextLine();
            loggedIn = hospital.login(userUser, userPass);
        } else if (loginOption.equals("02")) {
            System.out.print("Enter doctor username: ");
            String doctorUser = sc.nextLine();
            System.out.print("Enter doctor password: ");
            String doctorPass = sc.nextLine();
            loggedIn = hospital.login(doctorUser, doctorPass);
        } else if (loginOption.equals("03")) {
            System.out.print("Enter staff username: ");
            String staffUser = sc.nextLine();
            System.out.print("Enter staff password: ");
            String staffPass = sc.nextLine();
            loggedIn = hospital.login(staffUser, staffPass);
        } else {
            System.out.println("Invalid login option!");
            sc.close();
            return;
        }

        if (loggedIn == null) {
            System.out.println("Invalid login credentials!");
            sc.close();
            return;
        }

        System.out.println("Login successful! Role: " + loggedIn.getRole());

        switch (loggedIn.getRole()) {
            case "ADMIN":
                adminMenu(hospital, sc);
                break;
            case "USER":
                userMenu(hospital, sc);
                break;
            case "DOCTOR":
                doctorMenu(hospital, sc);
                break;
            case "STAFF":
                staffMenu(hospital, sc);
                break;
            default:
                System.out.println("Unknown role!");
        }

        sc.close();
    }

    private static void adminMenu(Hospital hospital, Scanner sc) {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Doctor");
            System.out.println("3. Add Staff");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. Show Patients");
            System.out.println("6. Show Doctors");
            System.out.println("7. Show Staff");
            System.out.println("8. Show Appointments");
            System.out.println("9. Save and Exit");
            System.out.print("Enter choice: ");

            String input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Please enter a valid choice!");
                continue;
            }

            int choice;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException e) { System.out.println("Invalid choice!"); continue; }

            switch (choice) {
                case 1: {
                    System.out.print("Enter patient name: ");
                    String pname = sc.nextLine();
                    System.out.print("Enter age: ");
                    int page = readInt(sc);
                    System.out.print("Enter disease: ");
                    String dis = sc.nextLine();
                    hospital.addPatient(pname, page, dis);
                    break;
                }
                case 2: {
                    System.out.print("Enter doctor name: ");
                    String dname = sc.nextLine();
                    System.out.print("Enter age: ");
                    int dage = readInt(sc);
                    System.out.print("Enter specialization: ");
                    String spec = sc.nextLine();
                    System.out.print("Enter appointment fee: ");
                    double fee = readDouble(sc);
                    hospital.addDoctor(dname, dage, spec, fee);
                    break;
                }
                case 3: {
                    System.out.print("Enter staff name: ");
                    String sname = sc.nextLine();
                    System.out.print("Enter age: ");
                    int sage = readInt(sc);
                    System.out.print("Enter department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter position: ");
                    String pos = sc.nextLine();
                    System.out.print("Enter salary: ");
                    double sal = readDouble(sc);
                    hospital.addStaff(sname, sage, dept, pos, sal);
                    break;
                }
                case 4: {
                    hospital.showDoctors();
                    System.out.print("Enter Doctor ID: ");
                    int dId = readInt(sc);
                    hospital.showPatients();
                    System.out.print("Enter Patient ID: ");
                    int pId = readInt(sc);
                    sc.nextLine(); // consume newline if needed
                    System.out.print("Enter appointment date (dd/mm/yyyy): ");
                    String date = sc.nextLine();
                    hospital.scheduleAppointment(dId, pId, date);
                    break;
                }
                case 5:
                    hospital.showPatients();
                    break;
                case 6:
                    hospital.showDoctors();
                    break;
                case 7:
                    hospital.showStaff();
                    break;
                case 8:
                    hospital.showAppointments();
                    break;
                case 9:
                    hospital.saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void userMenu(Hospital hospital, Scanner sc) {
        while (true) {
            System.out.println("\n=== USER MENU ===");
            System.out.println("1. Show Appointments");
            System.out.println("2. Show All Doctors");
            System.out.println("3. Show Appointment Fees");
            System.out.println("4. Show Staff");
            System.out.println("5. Save and Exit");
            System.out.print("Enter choice: ");

            String input = sc.nextLine();
            if (input.isEmpty()) { System.out.println("Please enter a valid choice!"); continue; }
            int choice;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException e) { System.out.println("Invalid choice!"); continue; }

            switch (choice) {
                case 1:
                    hospital.showAppointments();
                    break;
                case 2:
                    hospital.showDoctors();
                    break;
                case 3:
                    hospital.showAppointmentFees();
                    break;
                case 4:
                    hospital.showStaff();
                    break;
                case 5:
                    hospital.saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void doctorMenu(Hospital hospital, Scanner sc) {
        hospital.showDoctors();
        System.out.print("Enter your Doctor ID: ");
        int doctorId = readInt(sc);
        sc.nextLine();
        Doctor currentDoctor = null;
        for (Doctor d : hospital.getDoctors()) if (d.getId() == doctorId) { currentDoctor = d; break; }

        if (currentDoctor == null) {
            System.out.println("Invalid Doctor ID!");
            return;
        }

        System.out.println("Logged in as Dr. " + currentDoctor.getName());

        while (true) {
            System.out.println("\n=== DOCTOR MENU ===");
            System.out.println("1. Show My Patients");
            System.out.println("2. Select Patient");
            System.out.println("3. Mark Test Required");
            System.out.println("4. Write Prescription");
            System.out.println("5. Take Appointment Fee");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            String input = sc.nextLine();
            if (input.isEmpty()) { System.out.println("Please enter a valid choice!"); continue; }
            int choice;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException e) { System.out.println("Invalid choice!"); continue; }

            switch (choice) {
                case 1:
                    hospital.showDoctorPatients(doctorId);
                    break;
                case 2: {
                    hospital.showDoctorPatients(doctorId);
                    System.out.print("Enter Patient ID to select: ");
                    int patientId = readInt(sc);
                    sc.nextLine();
                    Patient selectedPatient = null;
                    for (Patient p : hospital.getPatients()) if (p.getId() == patientId) { selectedPatient = p; break; }
                    if (selectedPatient != null) {
                        System.out.println("Selected patient: " + selectedPatient.getName());
                    } else {
                        System.out.println("Invalid Patient ID!");
                    }
                    break;
                }
                case 3: {
                    hospital.showDoctorPatients(doctorId);
                    System.out.print("Enter Patient ID: ");
                    int patientId = readInt(sc);
                    sc.nextLine();
                    System.out.print("Test required? (true/false): ");
                    boolean testRequired = Boolean.parseBoolean(sc.nextLine().trim());
                    hospital.setTestRequired(patientId, testRequired);
                    break;
                }
                case 4: {
                    hospital.showDoctorPatients(doctorId);
                    System.out.print("Enter Patient ID: ");
                    int patientId = readInt(sc);
                    sc.nextLine();
                    System.out.print("Enter prescription: ");
                    String prescription = sc.nextLine();
                    hospital.setPrescription(patientId, prescription);
                    break;
                }
                case 5: {
                    List<Appointment> appointments = hospital.getAppointments();
                    System.out.println("\n=== APPOINTMENTS FOR FEE COLLECTION ===");
                    for (int i = 0; i < appointments.size(); i++) {
                        Appointment app = appointments.get(i);
                        if (app.getDoctor().getId() == doctorId && !app.isPaid()) {
                            System.out.println(i + ". " + app.getPatient().getName() +
                                    " | Date: " + app.getDate() +
                                    " | Fee: $" + app.getFee());
                        }
                    }
                    System.out.print("Enter appointment index to collect fee: ");
                    int appIndex = readInt(sc);
                    sc.nextLine();
                    hospital.takeAppointmentFee(appIndex);
                    break;
                }
                case 6:
                    hospital.saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void staffMenu(Hospital hospital, Scanner sc) {
        while (true) {
            System.out.println("\n=== STAFF MENU ===");
            System.out.println("1. Show All Staff");
            System.out.println("2. Search Staff by Department");
            System.out.println("3. Show Doctors");
            System.out.println("4. Show Patients");
            System.out.println("5. Show Appointments");
            System.out.println("6. Save and Exit");
            System.out.print("Enter choice: ");

            String input = sc.nextLine();
            if (input.isEmpty()) { System.out.println("Please enter a valid choice!"); continue; }
            int choice;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException e) { System.out.println("Invalid choice!"); continue; }

            switch (choice) {
                case 1:
                    hospital.showAllStaff();
                    break;
                case 2:
                    System.out.print("Enter department to search: ");
                    String department = sc.nextLine();
                    hospital.searchStaffByDepartment(department);
                    break;
                case 3:
                    hospital.showDoctors();
                    break;
                case 4:
                    hospital.showPatients();
                    break;
                case 5:
                    hospital.showAppointments();
                    break;
                case 6:
                    hospital.saveData();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter again: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter again: ");
            }
        }
    }
}
