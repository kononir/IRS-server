package com.bsuir.inforetrsys.view;

import javafx.scene.control.Alert;

public class MainAlert {
    public void show(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);

        alert.show();
    }
}
