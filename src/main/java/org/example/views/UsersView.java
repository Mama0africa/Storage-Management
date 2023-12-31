package org.example.views;

import org.example.views.view_components.BetterFrame;
import org.example.views.view_components.BetterScrollPane;
import org.example.views.view_components.BetterTable;
import org.example.controllers.NavBarController;
import org.example.models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsersView extends JPanel {


    private BetterTable usersTable;
    private BetterScrollPane tableScrollPane;

    // Define column headers at the class level
    private final String[] columnHeaders = { "Username", "Role", "Password", "User id" };

    // Create table for customers display
    private String[][] rowData = new String[][] {  };





    public UsersView()
    {
        initializeUI();
    }

    private void initializeUI() {




        // Initialize main panel and layout
        setLayout(new BorderLayout());

        usersTable = new BetterTable(rowData, columnHeaders);
        tableScrollPane = new BetterScrollPane(usersTable);

        // Add components to the main panel
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        add(buttonPanel, BorderLayout.SOUTH);


    }


    public void displayUsers(List<User> users) {
        // Convert list of items to table data format
        String[][] rowData = new String[users.size()][0];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            rowData[i] = new String[]{
                    user.getUsername(),
                    user.getRole(),
                    user.getPassword(),
                    user.getUserId()
            };
        }
        usersTable.setModel(new DefaultTableModel(rowData, columnHeaders));
    }

    public int getSelectedUserIndex() {
        return usersTable.getSelectedRow();
    }





}
