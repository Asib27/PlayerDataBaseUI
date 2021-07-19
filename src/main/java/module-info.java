module com.asib27.playerdatabaseui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.swing;
    requires com.asib27.PlayerDataBaseSystem;
    requires java.desktop;
    

    opens com.asib27.playerdatabaseui to javafx.fxml;
    opens com.asib27.playerdatabaseui.controllers to javafx.fxml;
    opens com.asib27.playerdatabaseui.util to javafx.fxml;
    exports com.asib27.playerdatabaseui;
}
