package com.display;

import com.calendar.Day;
import com.calendar.Month;
import com.calendar.Year;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarTable extends JTable {
    private DefaultTableModel dtm;

    private Month month;
    private Day day;

    //Constructor
    public CalendarTable() {
        day = new Day();
        month = new Month();

        setLayout();
        createTableCalendar(new Year().getYear(), month.getCurrMonthNum());
        init();
    }

    //Set table attributes
    private void init() {
        setRowHeight(50);
        setFillsViewportHeight(true);
        setPreferredScrollableViewportSize(getPreferredSize());
        getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 20));

        //Single cell selection
        setColumnSelectionAllowed(true);
        setRowSelectionAllowed(true);

        //Prevent resizing/reordering
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getTableHeader().setResizingAllowed(false);
        getTableHeader().setReorderingAllowed(false);

        //Color Scheme
        setForeground(Color.WHITE);
        setBackground(Color.BLACK);
    }

    //Create default table model
    private void setLayout() {
        dtm = new DefaultTableModel() {
            //Prevent cell editing
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        //Set rows and column headers
        dtm.setRowCount(6);
        for(int i = 0; i < 7; i++)
            dtm.addColumn(day.getCalDays(i));
    }

    //Set table month numbers
    public void createTableCalendar(int y, int m) {
        month.setCurrMonth(m);
        clearTable();

        GregorianCalendar cal = new GregorianCalendar(y, m, 1);
        int offset = cal.get(GregorianCalendar.DAY_OF_WEEK) - 1;

        //Set number values in cells
        for(int i = 0; i < month.getDaysPerMonth(y, m + 1); ++i) {
            dtm.setValueAt(Integer.toString(i + 1), offset / 7, offset % 7);
            ++offset;
        }

        //Set JTable model
        setModel(dtm);

        //Set cell renderer
        setDefaultRenderer(Object.class, new CalendarTable.TableCellRenderer());
    }

    //Clear table and reset layout
    private void clearTable() {
        dtm.setRowCount(0);
        setLayout();
    }

    //Class with method to modify cells of table
    public class TableCellRenderer extends DefaultTableCellRenderer {
        public TableCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);

            //Cell attributes
            setBackground(Color.BLACK);
            setForeground(Color.WHITE);
            setFont(new Font("Times New Roman", Font.BOLD, 20));

            Calendar cal = new GregorianCalendar();
            int currDay = cal.get(Calendar.DAY_OF_MONTH);
            int currMonth = cal.get(Calendar.MONTH);
            int currYear = cal.get(Calendar.YEAR);

            //Highlight current date (today)
            if(obj != null) {
                if(new Year().getYear() == currYear && month.getCurrMonthNum() == currMonth && Integer.parseInt(obj.toString()) == currDay)
                    setBackground(Color.CYAN);
            }

            return this;
        }
    }
}
