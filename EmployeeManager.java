//File Name EmployeeManager.java

import java.io.*;
import java.util.*;

public class EmployeeManager {

    public static void main(String[] args) {
        // Fix: Add a check at the beginning to ensure at least one argument.
        if (args.length == 0) {
            System.out.println("Error: No arguments provided.");
            System.out.println("Usage: java EmployeeManager <command>");
            System.out.println("Commands: l (list), s (select random), +<name> (add), ?<name> (search), c (count), u<name> (update to 'Updated'), d<name> (delete)");
            return; // Terminate the application gracefully
        }
        
        // Check arguments
        if (args[0].equals("l")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                if (l != null) { // Added check for null line
                    String e[] = l.split(",");
                    for (String emp : e) {
                        System.out.println(emp.trim()); // Added trim for clean output
                    }
                }
            } catch (Exception e) {
                // Consider more specific exception handling/logging
                System.err.println("An error occurred during list operation: " + e.getMessage());
            }
            System.out.println("Data Loaded.");
        } else if (args[0].equals("s")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                System.out.println(l);
                if (l != null && !l.trim().isEmpty()) { // Check for null or empty line
                    String e[] = l.split(",");
                    Random rand = new Random();
                    int idx = rand.nextInt(e.length);
                    System.out.println(e[idx].trim()); // Added trim
                } else {
                    System.out.println("Employee list is empty.");
                }
            } catch (Exception e) {
                System.err.println("An error occurred during select operation: " + e.getMessage());
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("+")) {
            if (args[0].length() <= 1) { // Check if a name is actually provided after '+'
                 System.err.println("Error: Employee name not provided for addition (e.g., +John)");
                 return;
            }
            System.out.println("Loading data ...");
            try {
                BufferedWriter w = new BufferedWriter(
                        new FileWriter("employees.txt", true));
                String n = args[0].substring(1).trim(); // Trim leading/trailing whitespace
                // Check if file is empty to determine whether to prepend a comma
                // This would ideally be handled with a file read, but for simplicity, we append.
                // NOTE: The current implementation will always append ", <name>"
                w.write(", " + n); 
                w.close();
            } catch (Exception e) {
                System.err.println("An error occurred during add operation: " + e.getMessage());
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("?")) {
            if (args[0].length() <= 1) {
                 System.err.println("Error: Employee name not provided for search (e.g., ?John)");
                 return;
            }
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                if (l != null) {
                    String e[] = l.split(",");
                    boolean found = false;
                    String s = args[0].substring(1).trim();
                    for (int i = 0; i < e.length && !found; i++) {
                        if (e[i].trim().equals(s)) { // Compare trimmed strings
                            System.out.println("Employee found!");
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("Employee not found.");
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred during search operation: " + e.getMessage());
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("c")) {
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                int count = 0;
                int totalChars = 0;
                if (l != null) {
                    totalChars = l.length();
                    // Simple count: split by comma, filter out empty/whitespace entries
                    String[] employees = l.split(",");
                    for(String emp : employees) {
                        if (!emp.trim().isEmpty()) {
                            count++;
                        }
                    }
                }
                // The original code's word counting logic was flawed for comma-separated data.
                // This is a simpler count of employees (comma-separated tokens).
                System.out.println(count + " employee(s) found. Total characters read: " + totalChars);
            } catch (Exception e) {
                System.err.println("An error occurred during count operation: " + e.getMessage());
            }
            System.out.println("Data Loaded.");
        } else if (args[0].contains("u")) {
            if (args[0].length() <= 1) {
                 System.err.println("Error: Employee name not provided for update (e.g., uJohn)");
                 return;
            }
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                if (l != null) {
                    String e[] = l.split(",");
                    String n = args[0].substring(1).trim();
                    boolean updated = false;
                    for (int i = 0; i < e.length; i++) {
                        if (e[i].trim().equals(n)) {
                            e[i] = "Updated"; // Replace the original name with "Updated"
                            updated = true;
                        }
                    }
                    
                    if (updated) {
                         BufferedWriter w = new BufferedWriter(
                                new FileWriter("employees.txt"));
                        w.write(String.join(",", e));
                        w.close();
                        System.out.println("Data Updated.");
                    } else {
                         System.out.println("Employee not found, no update performed.");
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred during update operation: " + e.getMessage());
            }
            System.out.println("Update process finished.");
        } else if (args[0].contains("d")) {
            if (args[0].length() <= 1) {
                 System.err.println("Error: Employee name not provided for delete (e.g., dJohn)");
                 return;
            }
            System.out.println("Loading data ...");
            try {
                BufferedReader r = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream("employees.txt")));
                String l = r.readLine();
                if (l != null) {
                    String e[] = l.split(",");
                    String n = args[0].substring(1).trim();
                    List<String> list = new ArrayList<>();
                    boolean removed = false;
                    for (String emp : e) {
                        if (emp.trim().equals(n)) {
                            removed = true; // Flag that the employee was found and removed
                        } else {
                            list.add(emp.trim()); // Add all others (trimmed)
                        }
                    }
                    
                    if (removed) {
                        BufferedWriter w = new BufferedWriter(
                                new FileWriter("employees.txt"));
                        w.write(String.join(",", list)); // Join the list back with commas
                        w.close();
                        System.out.println("Data Deleted.");
                    } else {
                        System.out.println("Employee not found, no deletion performed.");
                    }
                }
            } catch (Exception e) {
                System.err.println("An error occurred during delete operation: " + e.getMessage());
            }
            System.out.println("Delete process finished.");
        } else {
            System.out.println("Error: Unknown command or invalid argument format.");
            System.out.println("Usage: java EmployeeManager <command>");
        }
    }
}