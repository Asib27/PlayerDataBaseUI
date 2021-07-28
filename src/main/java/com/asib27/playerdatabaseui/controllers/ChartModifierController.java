/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import java.awt.GraphicsEnvironment;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ChartModifierController implements Initializable {
    Chart chart;

    @FXML
    private ComboBox chartType;
    
    @FXML
    private ColorPicker bgColorChart;
    
    @FXML
    private ColorPicker borderColorChart;
    
    @FXML
    private ComboBox<Side> legendPosition;
    
    /*legend */
    @FXML
    private ComboBox<String> legendFont;
    
    @FXML
    private ComboBox<Integer> legendFontSize;
    
    @FXML
    private CheckBox boldLegend, italicLegend;
    
    @FXML
    private ColorPicker colourPickedLegend;
    
    /* haxis*/
    @FXML
    private ComboBox<String> haxisFont;
    
    @FXML
    private ComboBox<Integer> haxisFontSize;
    
    @FXML
    private CheckBox haxisBold, haxisItalic;
    
    @FXML
    private ColorPicker haxisFontColor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        legendPosition.getItems().addAll(Arrays.asList(Side.values()));
        legendPosition.setValue(Side.BOTTOM);
        
        List<String> families = Font.getFamilies();
        Integer[] fontSizes = {10, 12, 14, 16, 18, 20};
        
        legendFont.getItems().addAll(families);
        legendFont.setValue("System");
        
        legendFontSize.setEditable(true);
        legendFontSize.getItems().addAll(fontSizes);
        legendFontSize.setValue(10);
        
        colourPickedLegend.setValue(Color.BLACK);
    }    

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
        
        chart.legendSideProperty().bind(legendPosition.valueProperty());
        
    }
    
    @FXML
    private void changeBgColor(){
        String color = getColorString(bgColorChart.getValue());
        String styleText = "-fx-background-color : "+ color +";";
        chart.lookup(".chart-plot-background").setStyle(styleText);
    }
    
    
    @FXML
    private void changeBorderColor(){
        String color = getColorString(borderColorChart.getValue());
        String styleText = "-fx-border-color : "+ color +";";
        chart.lookup(".chart-plot-background").setStyle(styleText);
    }
    
    @FXML
    private void changeLegendFont(){
        fontChangeHelper(".chart-legend-item", legendFont, legendFontSize, boldLegend, italicLegend, colourPickedLegend);
    }
    
    @FXML 
    private void changeHaxisFont(){
        XYChart chart2 = (XYChart) chart;
        
        chart.lookup(".axis-tick-label").setStyle("-fx-text-fill : "+ "red" +";");
        fontChangeHelper(".axis-tick-label", haxisFont, haxisFontSize, haxisBold, haxisItalic, haxisFontColor);
    }
    
    private void fontChangeHelper(String id, ComboBox<String> fontName, ComboBox<Integer> fontSize,
            CheckBox bold, CheckBox italic, ColorPicker fillColor)
    {
        String styleText = "";
        
        styleText += "-fx-font-family : \"" + fontName.getValue() + "\";";
        styleText+= "-fx-font-size : " + fontSize.getValue()  + ";";
        
        if(bold.isSelected())
            styleText += "-fx-font-weight : " + "bolder"  + ";";
        if(italic.isSelected())
            styleText += "-fx-font-style : " + "italic"  + ";";
        
        styleText += "-fx-text-fill : "+ getColorString(fillColor.getValue()) +";";
        chart.lookup(id).setStyle(styleText);
    }
    
    private String getColorString(Color color){
        return String.format("#%02X%02X%02X", (int)(color.getRed()*255), (int)(color.getGreen()*255), (int)(color.getBlue()*255));
    }
}
