package org.example.views.charts;

import org.example.util.DatabaseUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class ChartBuilder
{


    public static JFreeChart currentStockQuantityVSMinimalStockQuantity() {
        // Fetch data from the database
        List<List<String>> data = DatabaseUtil.getInstance().getProductStockInfo();

        // Create a dataset to hold the data for the chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Assuming the data contains columns for current stock quantity and minimal stock quantity
        for (List<String> row : data) {
            String productId = row.get(0); // Assuming the product name is in the first column
            int currentStock = Integer.parseInt(row.get(1)); // Assuming current stock is in the second column
            int minimalStock = Integer.parseInt(row.get(2)); // Assuming minimal stock is in the third column

            // Add data to the dataset
            dataset.addValue(currentStock, "Current Stock", productId);
            dataset.addValue(minimalStock, "Minimal Stock", productId);
        }

        // Create the area chart using ChartFactory
        JFreeChart chart = ChartFactory.createAreaChart(
                "Current Stock Quantity vs Minimal Stock Quantity", // Chart title
                "Product ID Number", // X-axis label
                "Stock Quantity", // Y-axis label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));

        return chart;
    }


    public static JFreeChart averageDeliveryTimeHistogram()
    {
        List<AbstractMap.SimpleEntry<String, Double>> deliveryTimeData = DatabaseUtil.getInstance().averageDeliveryTime();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (AbstractMap.SimpleEntry<String, Double> entry : deliveryTimeData) {
            dataset.addValue(entry.getValue(), "Delivery Time", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Average Delivery Time per Supplier",
                "Supplier",
                "Average Delivery Time (Days)",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                false);



        return barChart;
    }

    public static JFreeChart bestProfitableProductsPieChart()
    {
        List<AbstractMap.SimpleEntry<String, Double>> productData = DatabaseUtil.getInstance().bestProfitableProducts();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (AbstractMap.SimpleEntry<String, Double> entry : productData) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Most Profitable Products",
                dataset,
                false, // Set legend to false to remove color indicators
                true,
                false);

        PiePlot plot = (PiePlot) chart.getPlot();
        Font georgianFont = new Font("Sylfaen", Font.PLAIN, 12); // Replace with your Georgian font
        plot.setLabelFont(georgianFont);

        return chart;
    }

    // Method to generate a JFreeChart pie chart
    public static JFreeChart bestSellingProductsByUnitsSoldChart() {
        // Retrieve data from the bestSellingProductsByUnitsSold() method
        List<AbstractMap.SimpleEntry<String, Double>> productData = DatabaseUtil.getInstance().bestSellingProductsByUnitsSold();

        // Create a dataset for the pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (AbstractMap.SimpleEntry<String, Double> entry : productData) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        // Generate a JFreeChart pie chart using the dataset
        JFreeChart chart = ChartFactory.createPieChart(
                "Best Selling Products by Units Sold",  // Chart title
                dataset,  // Dataset
                false,     // Include legend
                true,
                false);

        // Get the plot (PiePlot) of the chart and set the label font
        PiePlot plot = (PiePlot) chart.getPlot();
        Font georgianFont = new Font("Sylfaen", Font.PLAIN, 12); // Replace "Sylfaen" with a Georgian font name
        plot.setLabelFont(georgianFont);

        return chart;
    }


    public static JFreeChart monthlySalesChart(LocalDate startDate, LocalDate endDate) {
        List<AbstractMap.SimpleEntry<String, Double>> salesData = DatabaseUtil.getInstance().totalSalesPerMonth(startDate+"", endDate+"");

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (AbstractMap.SimpleEntry<String, Double> entry : salesData) {

            dataset.addValue(entry.getValue(), "Sales", entry.getKey());
        }


        // Create the chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Total Sales Over Time",
                "Time Period",
                "Total Sales",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // get a reference to the plot for further customisation...
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 2.0));

        return barChart;
    }

}
