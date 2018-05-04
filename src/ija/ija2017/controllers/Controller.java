package ija.ija2017.controllers;

import ija.ija2017.interfaces.ExecuteControllerInterface;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.DragEvent;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * Created by davidillichmann on 04.05.18.
 */
public class Controller {

    protected ExecuteControllerInterface game;

    public void startNewGame() {
        if(this.game == null) { //pokud zadna hra neprobiha, zacneme novou
            this.game = new ExecuteController();
            this.game.init();
            String name = this.getBoardName();
            this.game.setBoardName(name);
        }
    }

    private String getBoardName() {
        TextInputDialog dialog = new TextInputDialog("Nové schéma");
        dialog.setHeaderText("");
        dialog.setTitle("Nové schéma");
        dialog.setContentText("Název schéma:");

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
//        if(event.getDragboard().hasContent()) {
//
//        }
    }

}
