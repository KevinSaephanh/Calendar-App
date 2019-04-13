package com.display;

import com.calendar.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

/*
* This class displays the time (ex: 1:00 PM)
* and the date (ex: Wednesday Apr 3 2019)
* Panel also includes a dynamic clock
* and a button to open the event frame
* */

public class CalendarDatePanel extends JPanel {
    private Calendar calendar;

    private Timer timer;

    private JLabel timeLabel;
    private JLabel dateLabel;
    private JLabel eventLabel;

    private int currSec;

    //Constructor
    public CalendarDatePanel() {
        init();
        start();
    }

    private void init() {
        Font font = new Font("Times New Roman", Font.BOLD, 24);

        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(500, 70));

        //Set date label
        dateLabel = new JLabel(new Date().getDate());
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(font);

        //Set time label
        timeLabel = new JLabel(new Date().getTime());
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(font);

        //Set add event button
        eventLabel = new JLabel("+");
        eventLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        eventLabel.setForeground(Color.WHITE);
        eventLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                //Open event frame when clicked
                new EventFrame();
            }
        });

        //Create box for date and time
        Box box = Box.createVerticalBox();
        box.add(timeLabel);
        box.add(dateLabel);

        add(Box.createHorizontalStrut(10));
        add(box);
        add(Box.createHorizontalGlue());
        add(eventLabel);
        add(Box.createHorizontalStrut(20));
    }

    //Start dynamic clock using timer, updating every minute
    private void start() {
        calendar = Calendar.getInstance();
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currSec == 60) {
                    resetSec();
                }
                timeLabel.setText(new Date().getTime());
                currSec++;
            }
        });
        timer.start();
    }

    private void resetSec() {
        currSec = calendar.get(Calendar.SECOND);
    }
}
