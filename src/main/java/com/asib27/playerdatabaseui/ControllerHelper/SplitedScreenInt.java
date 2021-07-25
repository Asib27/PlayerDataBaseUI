/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asib27.playerdatabaseui.ControllerHelper;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public interface SplitedScreenInt {
    public void setLeftPane(Pane pane);
    public void setRightPane(Pane pane);
    public void setFloatingPane(Pane pane);
    public SplitPane getSplitPane();
    public void setFloatingPointCollapsed(boolean isUp);
}
