package com.display;

import javax.swing.*;
import java.awt.*;

public class EventFrame extends JFrame {
    private Box box;

    private EventAddPanel eventAddPanel;
    private EventListPanel eventListPanel;

    //Constructor
    public EventFrame() {
        super("EVENT");
        init();
    }

    private void init() {
        eventAddPanel = new EventAddPanel();

        eventListPanel = new EventListPanel();

        box = Box.createHorizontalBox();
        box.add(eventAddPanel);
        box.add(eventListPanel);

        add(box, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
