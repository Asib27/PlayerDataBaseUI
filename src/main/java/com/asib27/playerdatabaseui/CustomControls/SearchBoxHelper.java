/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.CustomControls;

import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import static com.asib27.playerdatabasesystem.PlayerAttribute.AGE;
import static com.asib27.playerdatabasesystem.PlayerAttribute.COUNTRY;
import static com.asib27.playerdatabasesystem.PlayerAttribute.NAME;
import static com.asib27.playerdatabasesystem.PlayerAttribute.POSITION;
import static com.asib27.playerdatabasesystem.PlayerAttribute.SALARY;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javax.print.CancelablePrintJob;

/**
 *
 * @author USER
 */
public class SearchBoxHelper extends VBox{
    private PlayerAttribute field ;    
    private boolean isRange = false;
    
    private final String rangeString = "Range";
    private final String normalString = "Normal";

    ArrayList<SingleBox> boxes = new ArrayList<>();
    
    
    private SearchBoxHelper() {
        SingleBox singleBox = new SingleBox();
        boxes.add(singleBox);
        super.getChildren().addAll(boxes);
        super.setFillWidth(true);
    }

    public SearchBoxHelper(PlayerAttribute field) {
        this();
        this.field = field;
    }
    
    public boolean isInError(){
        boolean b = false;
        for (var box : boxes) {
            b = b || box.isInError();
        }
        
        return b;
    }
    
    public Player[] getResult(PlayerDataBaseInt dataBase){
        ArrayList<Player> arrayList = new ArrayList<>();
        
        boxes.forEach((t) -> {
            arrayList.addAll(Arrays.asList(t.getPlayers(dataBase)));
        });
        
        return arrayList.toArray(new Player[0]);
    }

    public PlayerAttribute getField() {
        return field;
    }

    public void setField(PlayerAttribute field) {
        this.field = field;
    }
    
    
    
    private class SingleBox extends HBox{
        TextField left =  new TextField(); 
        TextField right =  new TextField();
        Button rangeButton = new Button();
        Button cancelButton = new Button("Clear");
        
        boolean leftError = false;
        boolean rightError = false;

        public SingleBox() {
            super.getChildren().addAll(left, rangeButton, cancelButton);
            super.setAlignment(Pos.CENTER_LEFT);
            super.setSpacing(10);
            
            left.setPromptText("Enter value ");
            right.setPromptText("Upper Range");
            addTextFieldListeners(left);
            addTextFieldListeners(right);
            
            setButtonText(rangeButton, isRange);
            rangeButton.setOnAction((t) -> {
                if(isRange){
                    super.getChildren().remove(right);
                    isRange = false;
                    left.setPromptText("Enter " + field);
                }
                else{
                    super.getChildren().add(1, right);
                    isRange = true;
                    left.setPromptText("Lower Range");
                }
                setButtonText(rangeButton, isRange);
            });
            
            cancelButton.setOnAction((t) -> {
                left.setText("");
                right.setText("");
            });
        }

        private void addTextFieldListeners(TextField button){
            button.focusedProperty().addListener((ov, t, t1) -> {
                if(t1 == false && button.getText().length() != 0 &&!checkValidity(button.getText())){
                    button.setStyle("-fx-border-color : red;");
                    
                    if(button == left) leftError = true;
                    else rightError = true;
                }
                else{
                    button.setStyle("-fx-border-color : white;");
                    
                    if(button == left) leftError = false;
                    else rightError = false;
                }
            });
            
            
        }
        
        private void setButtonText(Button button, Boolean isRange){
            if(!isRange){
                button.setText(rangeString);
            }
            else{
                button.setText(normalString);
            }
        }
        
        private Player[] getPlayers(PlayerDataBaseInt database){
            String leftText = left.getText();
            String rightText = right.getText();
            
            if(leftText.length() == 0 && isRange && rightText.length()==0)
                return database.getAllRecords();
            else if(leftText.length() == 0 && !isRange)
                return database.getAllRecords();
            
            Player[] result = null;
            
            if(isRange){
                switch(field){
                    case CLUB, COUNTRY, NAME, POSITION->{
                        leftText = (leftText.length()==0 ? "0" : leftText);
                        rightText = (rightText.length()==0 ? "~" : rightText);

                        result = database.queryRange(field, leftText, rightText);
                    }
                    case JURSEY, AGE-> {
                        int min = (leftText.length()==0 ? 0 : Integer.parseInt(leftText));
                        int max = (rightText.length()==0 ? Integer.MAX_VALUE : Integer.parseInt(rightText));

                        result = database.queryRange(field, min, max);
                    }
                    case HEIGHT, SALARY-> {
                        double min = (leftText.length()==0 ? 0 : Double.parseDouble(leftText));
                        double max = (rightText.length()==0 ? Double.MAX_VALUE : Double.parseDouble(rightText));

                        result = database.queryRange(field, min, max);
                    }
                }
                
            }else{
                switch(field){
                    case CLUB, COUNTRY, NAME, POSITION->{
                        result = database.query(field, leftText);
                    }
                    case JURSEY, AGE-> {
                        result = database.query(field, Integer.parseInt(leftText));
                    }
                    
                    case HEIGHT, SALARY-> {
                        result = database.query(field, Double.parseDouble(leftText));
                    }
                }
            }
            
            return result;
        }
        
        private Player[] searchInRange(PlayerDataBaseInt db, PlayerAttribute field, Comparable min, Comparable max){
            Player[] queryRange = db.queryRange(field, min, max);
            return queryRange;
        }
        
        private boolean checkValidity(String str){
            return switch(field){
                case CLUB, COUNTRY, NAME, POSITION-> true;
                case JURSEY, AGE-> str.chars().allMatch((value) -> {
                        return Character.isDigit(value);
                    });
                case HEIGHT, SALARY-> checkDouble(str);
            };
        }
        
        private boolean checkDouble(String str){
            try{
                Double.parseDouble(str);
            }catch(NumberFormatException ex){
                return false;
            }
            return true;
        }
        
        public boolean isInError(){
            return leftError || (isRange && rightError);
        }
    }
}
