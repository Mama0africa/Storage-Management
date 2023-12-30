package org.example.views;


import org.example.views.charts.ChartBuilder;
import org.example.views.view_components.BetterButton;
import org.example.views.view_components.BetterFrame;
import org.example.views.view_components.CustomDatePickerPanel;
import org.example.views.view_components.DisplaysDate;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;


public class ChartSelectionView extends JPanel implements DisplaysDate {

    private JPanel chartPanel, buttonPanel;
    private int currentChartIndex;
    private BetterButton datePickerButton;
    private JFreeChart[] charts; // Store instances of different charts

    private LocalDate salesFromDate, salesUpToDate;



    public ChartSelectionView() {

        // Initialize chart instances
        charts = new JFreeChart[]{
                // Initialize other chart instances here
                ChartBuilder.monthlySalesChart(salesFromDate, salesUpToDate),
                ChartBuilder.bestSellingProductsByUnitsSoldChart(),
                ChartBuilder.bestProfitableProductsPieChart(),
                ChartBuilder.averageDeliveryTimeHistogram()
        };

        // Add buttons to a panel
        buttonPanel = new JPanel();

        datePickerButton = new BetterButton("Date From");


        // Create a panel to hold the chart
        chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        displayChart(charts[currentChartIndex]);

        // Create buttons
        BetterButton leftButton = new BetterButton("<");
        leftButton.addActionListener(e -> showPreviousChart());

        BetterButton rightButton = new BetterButton(">");
        rightButton.addActionListener(e -> showNextChart());



        // Make layout to flow layout where buttons are added
        buttonPanel.setLayout(new FlowLayout());

        // Add buttons to this buttonPanel
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(datePickerButton);



        // Add components to the frame
        setLayout(new BorderLayout());
        add(chartPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);





    }


    private void displayChart(JFreeChart chart) {
        // Display the chart on the chart panel
        ChartPanel chartPanel = new ChartPanel(chart);

        if (currentChartIndex == 0) {
            if (buttonPanel.getComponents().length != 0) {
                buttonPanel.add(datePickerButton);
            }
            var datePicker = new CustomDatePickerPanel(this);
            datePickerButton.addActionListener((e) -> {
                // Show datePicker object on top of this panel relatively to this from button
                Point buttonLocation = datePickerButton.getLocation();
                Point panelLocation = chartPanel.getLocation();
                int x = buttonLocation.x - panelLocation.x;
                int y = buttonLocation.y - panelLocation.y + datePickerButton.getHeight();

                // Add datePicker to the chart panel
                chartPanel.add(datePicker);
                chartPanel.setComponentZOrder(datePicker, 0);
                datePicker.setLocation(x, y);
                callRevalidateRepaint(this);
            });
        } else {
            buttonPanel.remove(datePickerButton);
        }

        chartPanel.setPreferredSize(new Dimension(500, 300));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);

        callRevalidateRepaint(buttonPanel);

        this.chartPanel.removeAll();
        this.chartPanel.add(chartPanel, BorderLayout.CENTER);
        callRevalidateRepaint(this.chartPanel);
    }

    private void showNextChart() {
        currentChartIndex = (currentChartIndex + 1) % charts.length;
        displayChart(charts[currentChartIndex]);
    }

    private void showPreviousChart() {
        currentChartIndex = (currentChartIndex - 1 + charts.length) % charts.length;
        displayChart(charts[currentChartIndex]);
    }

    public void callRevalidateRepaint(JComponent component)
    {
        component.revalidate();
        component.revalidate();
    }

    @Override
    public void displayDateFrom(LocalDate date) {
        salesFromDate = date;
        datePickerButton.setText("Date To");
    }

    @Override
    public void displayDateTo(LocalDate date) {
        salesUpToDate = date;
        charts[0] = ChartBuilder.monthlySalesChart(salesFromDate, salesUpToDate);
        displayChart(charts[0]);
        datePickerButton.setText("Date From");
    }
}
