package ie.atu.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import ie.atu.serialize.EmployeeSerializer;

public class SwingMenu extends JFrame {
    private static final int FRAME_WIDTH = 300;
    private static final int FRAME_HEIGHT = 340;

    private JPanel centerPanel;
    private JLabel jlblEmployee;
    private Font f;

    private JPanel buttonPanel;
    private JButton jbtAdd, jbtList, jbtView, jbtEdit, jbtDelete, jbtExit;

    private EmployeeSerializer employeeSerializer;

    public SwingMenu() {
        f = new Font("Gill Sans MT", Font.BOLD, 22);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        jlblEmployee = new JLabel("EMPLOYEE", new ImageIcon("image/employees.jpg"), SwingConstants.CENTER);
        jlblEmployee.setHorizontalTextPosition(JLabel.CENTER);
        jlblEmployee.setVerticalTextPosition(JLabel.TOP);
        jlblEmployee.setFont(f);
        centerPanel.add(jlblEmployee, BorderLayout.NORTH);
        jlblEmployee = new JLabel("MAINTENANCE", SwingConstants.CENTER);
        jlblEmployee.setFont(f);
        centerPanel.add(jlblEmployee, BorderLayout.CENTER);
        centerPanel.setBackground(Color.WHITE);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 5, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        buttonPanel.add(jbtAdd = new JButton("ADD"));
        buttonPanel.add(jbtList = new JButton("LIST"));
        buttonPanel.add(jbtView = new JButton("VIEW"));
        buttonPanel.add(jbtEdit = new JButton("EDIT"));
        buttonPanel.add(jbtDelete = new JButton("DELETE"));
        buttonPanel.add(jbtExit = new JButton("EXIT"));

        jbtAdd.setMnemonic('A');
        jbtList.setMnemonic('L');
        jbtView.setMnemonic('V');
        jbtEdit.setMnemonic('E');
        jbtDelete.setMnemonic('D');
        jbtExit.setMnemonic('X');

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);

        employeeSerializer = new EmployeeSerializer();
        employeeSerializer.deserializeEmployees();

        jbtAdd.addActionListener(e -> {
            employeeSerializer.add();
        });
        jbtList.addActionListener(e -> {
            employeeSerializer.list();
        });
        jbtView.addActionListener(e -> {
            employeeSerializer.view();
        });
        jbtEdit.addActionListener(e -> {
            employeeSerializer.edit();
        });
        jbtDelete.addActionListener(e -> {
            employeeSerializer.delete();
        });
        jbtExit.addActionListener(e -> {
            employeeSerializer.serializeEmployees();
            dispose();
        });
    }

    public static void main(String[] args) {
        SwingMenu frame = new SwingMenu();

        frame.setTitle("EMPLOYEE SECTION");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
