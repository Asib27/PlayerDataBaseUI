module com.asib27.playerdatabaseui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires com.asib27.PlayerDataBaseSystem;
    

    opens com.asib27.playerdatabaseui to javafx.fxml;
    opens com.asib27.playerdatabaseui.controllers to javafx.fxml;
    exports com.asib27.playerdatabaseui;
}
