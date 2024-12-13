package ie.atu.serializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ie.atu.hotel.Employee;

public class EmployeeSerializer {
    private ArrayList<Employee> employees;

    private final String FILENAME = "employees.bin";
    private File employeesFile;

    public EmployeeSerializer() {
        employees = new ArrayList<Employee>();
        employeesFile = new File(FILENAME);
        try {
            employeesFile.createNewFile();
        } catch (IOException e) {
            System.err.println("IOException with " + employeesFile.getName());
        }
    }

    public void add() {
        Employee employee = new Employee();
        if (employee.read()) {
            employees.add(employee);
        }
    }

    public void list() {
        if (employees.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Employees to list.", "EMPLOYEE LIST",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String employeesToList = "";

        for (Employee tmpEmployee : employees) {
            employeesToList += tmpEmployee;
            employeesToList += "\n";
        }

        JOptionPane.showMessageDialog(null, employeesToList, "EMPLOYEE LIST", JOptionPane.INFORMATION_MESSAGE);
    }

    public Employee view() {
        JTextField txtNumber = new JTextField();
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        int option = JOptionPane.showConfirmDialog(dialog, new Object[] { "Number", txtNumber },
                "ENTER EMPLOYEE NUMBER", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.CANCEL_OPTION) {
            return null;
        }

        int number = Integer.parseInt(txtNumber.getText());
        for (Employee employee : employees) {
            if (employee.getNumber() == number) {
                JOptionPane.showMessageDialog(null, employee.toString(), "EMPLOYEE", JOptionPane.INFORMATION_MESSAGE);
                return employee;
            }
        }

        JOptionPane.showMessageDialog(null, "NOT FOUND", "EMPLOYEE", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    public void delete() {
        JTextField txtNumber = new JTextField();
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        int option = JOptionPane.showConfirmDialog(dialog, new Object[] { "Number", txtNumber },
                "ENTER EMPLOYEE NUMBER", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.CANCEL_OPTION) {
            return;
        }

        int number = Integer.parseInt(txtNumber.getText());
        if (employees.removeIf(e -> (e.getNumber() == number))) {
            JOptionPane.showMessageDialog(null, "Delete", "EMPLOYEE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "NOT FOUND", "EMPLOYEE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void edit() {
        JTextField txtNumber = new JTextField();
        JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        int option = JOptionPane.showConfirmDialog(dialog, new Object[] { "Number", txtNumber },
                "ENTER EMPLOYEE NUMBER", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.CANCEL_OPTION) {
            return;
        }

        int number = Integer.parseInt(txtNumber.getText());
        for (Employee employee : employees) {
            if (employee.getNumber() == number) {
                if (employee.read()) {
                    JOptionPane.showMessageDialog(null, "Eddited", "EMPLOYEE", JOptionPane.INFORMATION_MESSAGE);
                }
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "NOT FOUND", "EMPLOYEE", JOptionPane.ERROR_MESSAGE);
    }

    public void serializeEmployees() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(employeesFile));
            os.writeObject(employees);
            os.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + employeesFile.getName() + " not found");
        } catch (Exception e) {
            System.err.println("Some exception with " + employeesFile.getName());
        }
    }

    public void deserializeEmployees() {
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(employeesFile));
            employees = (ArrayList<Employee>) is.readObject();
            Employee.setNextNumber(Collections.max(employees, (a, b) -> Integer.compare(a.getNumber(), b.getNumber())).getNumber() + 1);
            is.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + employeesFile.getName() + " not found");
        } catch (Exception e) {
            System.err.println("Cannot read from " + employeesFile.getName() + e.getMessage());
        }
    }
}
