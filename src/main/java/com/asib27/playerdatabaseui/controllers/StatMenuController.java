/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.ControllerHelper.SearchObserver;
import com.asib27.playerdatabaseui.ControllerHelper.DataProcessHelper;
import com.asib27.playerdatabaseui.util.ObserverUtil;
import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabasesystem.PlayerDataBase;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import com.asib27.playerdatabaseui.CustomControls.SearchBoxHelper;
import com.asib27.playerdatabaseui.CustomControls.SearchHelper;
import com.asib27.playerdatabaseui.ControllerHelper.StatData;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import javafx.animation.PauseTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class StatMenuController extends ObserverUtil<SearchObserver<StatData>> implements Initializable {
    @FXML
    private ComboBox<PlayerAttribute> rowNameBox;

    @FXML
    private ComboBox<PlayerAttribute> colNameBox;

    @FXML
    private ComboBox<String> statTypeBox;

    @FXML
    private ComboBox<PlayerAttribute> whichFieldBox;

    @FXML
    private ComboBox positionComboBox;

    @FXML
    private Button searchButton;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private TextField errorMessage;
    
    @FXML
    private Button clearButton;
      
    final private String[] allStatType = {"Count", "Total", "Average"};
    private DataProcessor dataProcessor = null;
    SearchHelper searchHelper = new SearchHelper();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for (var field: PlayerAttribute.values()) {
            rowNameBox.getItems().add(field);
            colNameBox.getItems().add(field);
        }
        rowNameBox.getItems().remove(0);
        colNameBox.getItems().remove(0);
        
        rowNameBox.setValue(PlayerAttribute.CLUB);
        statTypeBox.setValue(allStatType[0]);
        whichFieldBox.setValue(PlayerAttribute.AGE);
        
        statTypeBox.getItems().addAll(allStatType);
        whichFieldBox.getItems().addAll(PlayerAttribute.AGE, PlayerAttribute.SALARY, PlayerAttribute.HEIGHT ,PlayerAttribute.JURSEY);
        
        ObjectProperty valueProperty = statTypeBox.valueProperty();
        whichFieldBox.visibleProperty().bind(valueProperty.isEqualTo("Average").or(valueProperty.isEqualTo("Total")));
        
        
        clearButton.visibleProperty().bind(colNameBox.valueProperty().isNotNull());
        
        searchButton.setOnAction((t) -> {
            if(searchHelper.isInError()){
                errorMessage.setVisible(true);
                errorMessage.setText("Some field does not contain proper data");
                
                PauseTransition pt = new PauseTransition(Duration.millis(5000));
                pt.setOnFinished(eh -> errorMessage.setVisible(false));
                pt.play();
            }
            else{
                errorMessage.setVisible(false);
                dataProcessor = new DataProcessor();
                updateAll();
            }
        });
        
        anchorPane.getChildren().add(searchHelper);
        searchHelper.setLayoutX(20.0);
        searchHelper.setLayoutY(130);
        
    }    
    
    public void clearListener(){
        whichFieldBox.visibleProperty().unbind();
    }
    
    public Button getSearchButton() {
        return searchButton;
    }

    public DataProcessor getDataProcessor() {
        return dataProcessor;
    }

    @Override
    public void updator(SearchObserver t) {
        t.update(dataProcessor);
    }
    
    @FXML
    private void clearComboBox(){
        colNameBox.valueProperty().set(null);
    }
    
    public class DataProcessor implements DataProcessHelper<StatData>{
        private ArrayList<String> columns = new ArrayList<>();
        
        @Override
        public StatData[] getData(PlayerDataBaseInt database){
            PlayerDataBaseInt db = new PlayerDataBase(searchHelper.getData(database));
            
            columns.clear();
            ArrayList<StatData> data =  null;
                
            if(colNameBox.getValue() == null){
                data = createSingleColumnData(db);
            }
            else{
                data = createMultipleColumnData(db);
            }
                
            return data.toArray(new StatData[0]);
        }
        
        private ArrayList<StatData> createSingleColumnData(PlayerDataBaseInt db){
            ArrayList<StatData> data = new ArrayList<>();    
            
            if(statTypeBox.getValue().equals("Count")){
                Map<String, Integer> counAlltFields = db.counAlltFields(rowNameBox.getValue());
                for (String string : counAlltFields.keySet()) {
                    StatData sd = new StatData(string);
                    sd.addValue("Count", counAlltFields.get(string));
                    data.add(sd);
                }
                
            }else{
                Map<String, PlayerDataBaseInt> splitDatabase = db.splitDatabase(rowNameBox.getValue());

                splitDatabase.keySet().stream().map((t) -> {
                    StatData sd = new StatData(t);
                    
                    if(statTypeBox.getValue().equals("Total")){
                        sd.addValue(statTypeBox.getValue(), sum(splitDatabase.get(t), whichFieldBox.getValue()));
                    }
                    else{
                        sd.addValue(statTypeBox.getValue(), average(splitDatabase.get(t), whichFieldBox.getValue()));
                    }
                    
                    return sd;
                }).forEachOrdered((sd) -> {
                    data.add(sd);
                });
            }

            columns.add(statTypeBox.getValue());
            
            
            return data;
        }


        private ArrayList<StatData> createMultipleColumnData(PlayerDataBaseInt db) {
            ArrayList<StatData> data = new ArrayList<>();    
            Set<String> columnNames = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            
            if(statTypeBox.getValue().equals("Count")){
                Map<String, PlayerDataBaseInt> splitDatabase = db.splitDatabase(rowNameBox.getValue());
                
                splitDatabase.keySet().stream().map((t) -> {
                    StatData sd = new StatData(t);
                    sd.setSeriesValuesInt(splitDatabase.get(t).counAlltFields(colNameBox.getValue()));
                    return sd; //To change body of generated lambdas, choose Tools | Templates.
                }).forEachOrdered((StatData t) -> {
                    data.add(t);
                    columnNames.addAll(t.getSeriesValues().keySet());
                });
                
            }else{
                Map<String, PlayerDataBaseInt> splitDatabase = db.splitDatabase(rowNameBox.getValue());

                splitDatabase.keySet().stream().map((t) -> {
                    var split = splitDatabase.get(t).splitDatabase(colNameBox.getValue());
                    StatData sd = null;
                    
                    if(statTypeBox.getValue().equals("Total"))
                        sd = sumOfAll(split);
                    else
                        sd = avgOfAll(split);
                    
                    sd.setName(t);
                    return sd;
                }).forEachOrdered((sd) -> {
                    data.add(sd);
                    columnNames.addAll(sd.getSeriesValues().keySet());
                });
            }

            columns.addAll(columnNames);
            
            return data;
        }
        
        public ArrayList<String> getColumns() {
            return columns;
        }
        
        private StatData sumOfAll(Map<String, PlayerDataBaseInt> split){
            StatData sd = new StatData();
            
            for (String string : split.keySet()) {
                sd.addValue(string, sum(split.get(string), whichFieldBox.getValue()));
            }
            
            return sd;
        }
        
        private StatData avgOfAll(Map<String, PlayerDataBaseInt> split){
            StatData sd = new StatData();
            
            for (String string : split.keySet()) {
                sd.addValue(string, average(split.get(string), whichFieldBox.getValue()));
            }
            
            return sd;
        }
        
        private double sum(PlayerDataBaseInt db, PlayerAttribute field){
            Player[] players = db.getAllRecords();
            double sum = 0;
            
            for (Player player : players) {
                Object ob = player.get(field);
                
                if(ob instanceof Double)
                    sum += (double)player.get(field);
                else if(ob instanceof Integer)
                    sum += (Integer)player.get(field);
            }
            
            return sum;
        }
        
        private double average(PlayerDataBaseInt db, PlayerAttribute field){
            return sum(db, field)/db.getNoofRecord();
        }
    }
    
}
