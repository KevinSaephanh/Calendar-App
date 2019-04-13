package com.display;

import com.datastorage.EventJSON;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class EventListPanel extends JPanel {
    private JList<String> list;

    private JLabel listTitle;

    private JButton deleteButton;

    private JTextArea eventDetails;

    DefaultListModel<String> model;

    private Box box;

    EventJSON eventJSON;

    public EventListPanel() {
        init();
        setList();
        setDetailsArea();
        setDeleteButton();
        displayList();

        add(box);
    }

    private void init() {
        eventJSON = new EventJSON();

        listTitle = new JLabel("Today's Events");
        listTitle.setFont(new Font("Times New Roman", Font.BOLD, 26));
        listTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        box = Box.createVerticalBox();
        box.add(listTitle);
    }

    @SuppressWarnings("unchecked")
    private void setList() {
        model = new DefaultListModel<>();
        list = new JList(model);
        list.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        //Add mouse listener to enable delete button when text is highlighted
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                deleteButton.setEnabled(true);

                eventDetails.setText(eventJSON.getTodayEventDetails(list.getSelectedValue()));
            }
        });

        box.add(new JScrollPane(list));
        box.add(Box.createVerticalStrut(10));
    }

    private void setDetailsArea() {
        eventDetails = new JTextArea();
        eventDetails.setEditable(false);
        eventDetails.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        eventDetails.setPreferredSize(new Dimension(200, 200));

        box.add(eventDetails);
        box.add(Box.createVerticalStrut(10));
    }

    private void setDeleteButton() {
        deleteButton = new JButton("Delete Event");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setEnabled(false);

        //Add action listener
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Remove object from file
                eventJSON.deleteFromJSON(list.getSelectedValue());

                //Remove deleted event from the list
                model.remove(list.getSelectedIndex());
            }
        });

        box.add(deleteButton);
    }

    public void displayList() {
        //Add array list elements to model list
        for(int i = 0; i < eventJSON.getTodayEvents().size(); i++) {
            model.addElement(eventJSON.getTodayEvents().get(i));
        }
    }
}
