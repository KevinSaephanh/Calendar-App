package com.display;

import javax.swing.*;
import java.awt.*;

public class CalendarFrame extends JFrame {
    private CalendarDatePanel calendarDatePanel;
    private CalendarTablePanel calendarTablePanel;

    //Constructor
    public CalendarFrame() {
        super("CALENDAR");
        init();
    }

    private void init() {
        calendarDatePanel = new CalendarDatePanel();
        add(calendarDatePanel, BorderLayout.NORTH);

        calendarTablePanel = new CalendarTablePanel();
        add(calendarTablePanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
