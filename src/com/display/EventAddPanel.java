package com.display;

import com.calendar.Event;
import com.calendar.Year;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventAddPanel extends JPanel {
    private JTextField nameField;
    private JFormattedTextField dateField;
    private JFormattedTextField timeStartField;
    private JFormattedTextField timeEndField;
    private JTextArea descriptionArea;

    private Box box;

    private Font font;

    //Constructor
    public EventAddPanel() {
        init();

        setNameField();
        setDateField();
        setTimeStartField();
        setTimeEndField();
        setDescriptionArea();
        setAddButton();

        add(box);
    }

    private void init() {
        font = new Font("Times New Roman", Font.PLAIN, 16);

        JLabel eventLabel = new JLabel("Add Event");
        eventLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        eventLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        box = Box.createVerticalBox();
        box.add(eventLabel);
    }

    private void setNameField() {
        nameField = new JTextField();
        nameField.setFont(font);
        nameField.setText("Event name");

        //Set character limit
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(nameField.getText().length() > 15)
                    e.consume();
            }
        });

        //Add focus listener
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nameField.getText().equals("Event name"))
                    nameField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(nameField.getText().isEmpty())
                    nameField.setText("Event name");
            }
        });

        box.add(nameField);
        box.add(Box.createVerticalStrut(10));
    }

    private void setDateField() {
        dateField = new JFormattedTextField();
        dateField.setFont(font);
        dateField.setText("Ex: 12-1-2019");

        //Add focus listener
        dateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(dateField.getText().equals("Ex: 12-1-2019"))
                    dateField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(dateField.getText().isEmpty())
                    dateField.setText("Ex: 12-1-2019");
            }
        });

        //Set character limit
        dateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(dateField.getText().length() > 10)
                    e.consume();
            }
        });

        box.add(dateField);
        box.add(Box.createVerticalStrut(10));
    }

    private void setTimeStartField() {
        timeStartField = new JFormattedTextField();
        timeStartField.setFont(font);
        timeStartField.setText("Ex: 12:30 PM");

        //Add focus listener
        timeStartField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(dateField.getText().equals("Ex: 12-1-2019"))
                    dateField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(dateField.getText().isEmpty())
                    dateField.setText("Ex: 12-1-19");
            }
        });

        //Set character limit
        timeStartField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(timeStartField.getText().length() > 10)
                    e.consume();
            }
        });

        box.add(timeStartField);
        box.add(Box.createVerticalStrut(10));
    }

    private void setTimeEndField() {
        timeEndField = new JFormattedTextField();
        timeEndField.setFont(font);
        timeEndField.setText("Ex: 3:50 PM");

        //Add focus listener
        timeEndField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(timeEndField.getText().equals("Ex: 3:50 PM"))
                    timeEndField.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(timeEndField.getText().isEmpty())
                    timeEndField.setText("Ex: 3:50 PM");
            }
        });

        //Set character limit
        timeEndField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(timeEndField.getText().length() > 10)
                    e.consume();
            }
        });

        box.add(timeEndField);
        box.add(Box.createVerticalStrut(10));
    }

    private void setDescriptionArea() {
        descriptionArea = new JTextArea(10, 30);
        descriptionArea.setFont(font);
        descriptionArea.setText("Description");

        //Set character limit
        descriptionArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(descriptionArea.getText().length() > 300)
                    e.consume();
            }
        });

        //Add focus listener
        dateField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(descriptionArea.getText().equals("Description"))
                    descriptionArea.setText(null);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(descriptionArea.getText().isEmpty())
                    descriptionArea.setText("Description");
            }
        });

        box.add(descriptionArea);
        box.add(Box.createVerticalStrut(10));
    }

    private void setAddButton() {
        JButton addButton = new JButton("Add Event");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add action listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Convert date field text to formatted local date
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M-d-uuuu");
                    LocalDate date = LocalDate.parse(dateField.getText(), dtf);

                    //Throw invalid year input - cannot be before current year
                    if(date.isBefore(LocalDate.ofYearDay(new Year().getYear(), 1)))
                        throw new DateTimeException("INVALID YEAR INPUT");

                    //Convert time fields texts to formatted local date times
                    dtf = DateTimeFormatter.ofPattern("h:mm a");
                    LocalTime start = LocalTime.parse(timeStartField.getText(), dtf);
                    LocalTime end = LocalTime.parse(timeEndField.getText(), dtf);

                    //Throw invalid date time input -  start time is less than or equal to end time
                    if(start.compareTo(end) >= 0)
                        throw new DateTimeException("INVALID TIME SCHEDULING");

                    //Create and set event
                    Event event = new Event(nameField.getText(), date, start, end, descriptionArea.getText());
                    event.setEvent();

                    //Clear text after setting event
                    clearAll();
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        box.add(addButton);
    }

    //Clear all text
    private void clearAll() {
        nameField.setText("");
        dateField.setText("");
        timeStartField.setText("");
        timeEndField.setText("");
        descriptionArea.setText("");
    }
}
