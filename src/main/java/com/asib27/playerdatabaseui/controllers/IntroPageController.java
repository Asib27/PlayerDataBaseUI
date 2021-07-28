/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.controllers;

import com.asib27.playerdatabaseui.CustomControls.AutoCompleteTextField;
import com.asib27.playerdatabaseui.ControllerHelper.IntroPageButtonPressListener;
import com.asib27.playerdatabaseui.Drivers.Driver;
import com.asib27.playerdatabaseui.util.DatabaseManager;
import com.asib27.playerdatabaseui.util.ObserverUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Consumer;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class
 *
 * @author USER
 */

public class IntroPageController extends ObserverUtil<IntroPageButtonPressListener> implements Initializable, Driver{
    @FXML
    private AnchorPane vboxContainer;

    @FXML
    private Label databaseLabel;
    
    private AutoCompleteTextField searchTextField;
    
    
    private String[] buttonTexts = {"Search", "Stat", "Sell", "Buy"};
    private ArrayList<Consumer<ActionEvent>> buttonAcions = new ArrayList<>();
    String pressedButton = null;
    final String driverName = "New Tab";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        buttonAcions.add(this::button1pressed);
        buttonAcions.add(this::button2pressed);
        buttonAcions.add(this::button3pressed);
        buttonAcions.add(this::button4pressed);
        
        initiateSearchBox();
        searchTextField.setOnAction((ActionEvent t) -> {
            String text = searchTextField.getText();
            for (int i = 0; i < buttonTexts.length; i++) {
                String buttonText = buttonTexts[i];
                
                if(buttonText.equals(text)){
                    Consumer<ActionEvent> get = buttonAcions.get(i);
                    get.accept(null);
                    break;
                }
            }
        });
        
    }    
    
    private void initiateSearchBox(){
        SortedSet<String> entries = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        entries.addAll(Arrays.asList(buttonTexts));
        
        searchTextField = new AutoCompleteTextField(entries);
        
        vboxContainer.getChildren().add(searchTextField);
        searchTextField.setLayoutX(208.0);
        searchTextField.setLayoutY(335.0);
        searchTextField.setPrefWidth(540);
        searchTextField.setPromptText("Search Tool");
        AnchorPane.setLeftAnchor(searchTextField, 208.0);
        AnchorPane.setRightAnchor(searchTextField, 208.0);
        searchTextField.requestFocus();
    }

    @Override
    public Pane getGuiPane() {
        return vboxContainer;
    }

    @Override
    public void clearListener() {
        ;
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public void update(DatabaseManager databaseManager) {
        
    }
    
    @FXML
    private void button1pressed(ActionEvent ae){
        pressedButton = buttonTexts[0];
        updateAll();
    }
    
    @FXML
    private void button2pressed(ActionEvent ae){
        pressedButton = buttonTexts[1];
        updateAll();
    }
    
    @FXML
    private void button3pressed(ActionEvent ae){
        pressedButton = buttonTexts[2];
        updateAll();
    }
    
    @FXML
    private void button4pressed(ActionEvent ae){
        pressedButton = buttonTexts[3];
        updateAll();
    }

    @Override
    protected void updator(IntroPageButtonPressListener t) {
        t.update(pressedButton);
    }
}
