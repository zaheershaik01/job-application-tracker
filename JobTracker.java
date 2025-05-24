import java.io.*;
import java.util.*;

class Job {
    String title, company, status, deadline;

    Job(String title, String company, String status, String deadline) {
        this.title = title;
        this.company = company;
        this.status = status;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Company: " + company + ", Status: " + status + ", Deadline: " + deadline;
    }
}

public class JobTracker {
    static ArrayList<Job> jobList = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadJobsFromFile();
        while (true) {
            System.out.println("\n--- Job Application Tracker ---");
            System.out.println("1. Add Job\n2. View Jobs\n3. Search by Company\n4. Update Status\n5. Delete Job\n6. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear newline

            switch (choice) {
                case 1 -> addJob();
                case 2 -> viewJobs();
                case 3 -> searchJob();
                case 4 -> updateStatus();
                case 5 -> deleteJob();
                case 6 -> {
                    saveJobsToFile();
                    System.out.println("Exiting... Jobs saved.");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }

    static void addJob() {
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter company: ");
        String company = sc.nextLine();
        System.out.print("Enter status (Applied/Interview/Rejected/Offer): ");
        String status = sc.nextLine();
        System.out.print("Enter deadline (yyyy-MM-dd): ");
        String deadline = sc.nextLine();

        jobList.add(new Job(title, company, status, deadline));
        System.out.println("Job added.");
    }

    static void viewJobs() {
        jobList.sort(Comparator.comparing(j -> j.deadline));
        System.out.println("--- All Jobs Sorted by Deadline ---");
        for (Job j : jobList)
            System.out.println(j);
    }

    static void searchJob() {
        System.out.print("Enter company name to search: ");
        String name = sc.nextLine();
        for (Job j : jobList) {
            if (j.company.equalsIgnoreCase(name)) {
                System.out.println(j);
            }
        }
    }

    static void updateStatus() {
        System.out.print("Enter company name to update: ");
        String name = sc.nextLine();
        for (Job j : jobList) {
            if (j.company.equalsIgnoreCase(name)) {
                System.out.print("Enter new status: ");
                j.status = sc.nextLine();
                System.out.println("Status updated.");
                return;
            }
        }
        System.out.println("Company not found.");
    }

    static void deleteJob() {
        System.out.print("Enter company name to delete: ");
        String name = sc.nextLine();
        Iterator<Job> it = jobList.iterator();
        while (it.hasNext()) {
            if (it.next().company.equalsIgnoreCase(name)) {
                it.remove();
                System.out.println("Job deleted.");
                return;
            }
        }
        System.out.println("Company not found.");
    }

    static void saveJobsToFile() {
        try (PrintWriter pw = new PrintWriter("jobs.txt")) {
            for (Job j : jobList) {
                pw.println(j.title + "," + j.company + "," + j.status + "," + j.deadline);
            }
        } catch (IOException e) {
            System.out.println("Error saving jobs: " + e.getMessage());
        }
    }

    static void loadJobsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("jobs.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 4) jobList.add(new Job(p[0], p[1], p[2], p[3]));
            }
        } catch (IOException e) {
            System.out.println("No saved jobs found.");
        }
    }
}
