/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.CustomControls;

import com.asib27.playerdatabaseui.CustomControls.SearchBoxHelper;
import com.asib27.playerdatabasesystem.Player;
import com.asib27.playerdatabasesystem.PlayerAttribute;
import com.asib27.playerdatabasesystem.PlayerDataBase;
import com.asib27.playerdatabasesystem.PlayerDataBaseInt;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author USER
 */
public class SearchHelper extends GridPane{
    ArrayList<SearchBoxHelper> searchBoxHelpers = new ArrayList<>();
    ArrayList<Label> searchLabels = new ArrayList<>();

    public SearchHelper() {
        initialize();
    }
    
    private void initialize(){
        int i = 0;
        for (var field : PlayerAttribute.values()) {
            SearchBoxHelper searchBoxHelper = new SearchBoxHelper(field);
            searchBoxHelpers.add(searchBoxHelper);
            
            Label label = new Label(field.toString());
            searchLabels.add(label);
            
            VBox vb = new VBox(label, searchBoxHelper);
            this.add(vb, 0, i);
            i++;
        }
        
        this.setVgap(20);
    }
    
    public Player[] getData(PlayerDataBaseInt pdb){
        Player[] players = null;
        PlayerDataBaseInt db = new PlayerDataBase(pdb.getAllRecords());
        
        for (var s : searchBoxHelpers) {
            players = s.getResult(db);
            db.setPlayers(players);
        }
        
        return players;
    }
    
    public boolean isInError(){
        boolean b = false;
        for (var box : searchBoxHelpers) {
            b = b || box.isInError();
        }
        
        return b;
    }
}
