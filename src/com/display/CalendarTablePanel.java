package com.display;

import com.calendar.Month;
import com.calendar.Year;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
* This class creates a display for the current month
* with previous/next month transitioning buttons.
* */

public class CalendarTablePanel extends JPanel {
    private JButton prevMonth;
    private JButton nextMonth;

    private JLabel currMonth;

    private Month month;
    private CalendarTable table;

    //Constructor
    public CalendarTablePanel() {
        init();
    }

    private void init() {
        //Panel attributes
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(516, 353));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //Initialize month
        month = new Month();
        setMonth(month.getCurrMonth());
        setMonthButtons();

        //Create box
        Box box = Box.createHorizontalBox();
        box.add(prevMonth);
        box.add(currMonth);
        box.add(nextMonth);
        add(box);

        //Initialize table
        table = new CalendarTable();
        add(table);
        add(new JScrollPane(table));
    }

    //Create label for current month
    public void setMonth(String s) {
        currMonth = new JLabel(s);
        currMonth.setForeground(Color.WHITE);
        currMonth.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        currMonth.setFont(new Font("Times New Roman", Font.BOLD, 28));
    }

    //Set and add previous/next month buttons
    private void setMonthButtons() {
        prevMonth = new JButton("<<");
        prevMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(month.getCurrMonthNum() == 0)
                    prevMonth.setEnabled(false);
                else {
                    nextMonth.setEnabled(true);
                    month.setCurrMonth(month.getCurrMonthNum() - 1);
                    currMonth.setText(month.getCurrMonth());

                    //Refresh table
                    table.createTableCalendar(new Year().getYear(), month.getCurrMonthNum());
                }
            }
        });

        nextMonth = new JButton(">>");
        nextMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(month.getCurrMonthNum() == 11)
                    nextMonth.setEnabled(false);
                else {
                    prevMonth.setEnabled(true);
                    month.setCurrMonth(month.getCurrMonthNum() + 1);
                    currMonth.setText(month.getCurrMonth());

                    //Refresh table
                    table.createTableCalendar(new Year().getYear(), month.getCurrMonthNum());
                }
            }
        });
    }
}
