package ija.ija2017.controllers;

import ija.ija2017.drag.DraggableNode;
import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.BoardItemInterface;
import ija.ija2017.interfaces.ExecuteControllerInterface;
import ija.ija2017.items.board.BoardItem;
import ija.ija2017.items.connection.PortItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by davidillichmann on 02.05.18.
 *
 */
public class ExecuteController extends AnchorPane implements ExecuteControllerInterface {

    private ArrayList<DraggableNode> draggebleItems;
    private BoardItemInterface boardItem;
    private DraggableNode actualDraggableItem;
    private DraggableNode previousDraggableItem;

    @FXML
    private transient Button next_step;

    @FXML
    private transient Button compute;

    public ExecuteController() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../resources/controllers.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize() {
        this.boardItem = new BoardItem();
        this.draggebleItems = boardItem.getDraggableItems();

        buildButtonHandlers();
    }

    private void buildButtonHandlers() {
        next_step.setOnMouseClicked(event -> {
            this.nextStep();
        });

        compute.setOnMouseClicked(event -> {
            this.solveWholeBoard();
        });

    }

    @Override
    public void setBoardName(String name) {
        this.boardItem.setName(name);
    }

    @Override
    public void nextStep() {
        if(this.draggebleItems.isEmpty() || this.actualDraggableItem == null) {
            if(!boardItem.getDraggableItems().isEmpty()) {
                this.draggebleItems = new ArrayList<>(boardItem.getDraggableItems());
                this.actualDraggableItem = this.draggebleItems.get(0);
            } else {
                //prazdny board
                return;
            }
        }
        if(!this.updateInputValues(this.actualDraggableItem)) {
            this.draggebleItems.remove(this.actualDraggableItem); //odebere aktualni
            this.draggebleItems.add(this.actualDraggableItem); //prida jej nakonec seznamu
            this.actualDraggableItem = this.draggebleItems.get(0); //nastavime aktualni na prvni
            this.nextStep();
            return;
        }
        if (this.actualDraggableItem.getBlockItem().execute()) {
            this.setBoardBlockItemOutputValue(this.actualDraggableItem);
            this.actualDraggableItem.getTitle_bar().setText(Double.toString(this.actualDraggableItem.getBlockItem().getOutputPort().getValue()));
            this.draggebleItems.remove(this.actualDraggableItem);
        }

        int idx = this.draggebleItems.indexOf(actualDraggableItem);
        if(this.previousDraggableItem != null) {
            this.previousDraggableItem.setNormalStyle();
        }
        this.previousDraggableItem = actualDraggableItem;
        this.actualDraggableItem.setExecuting();
        try {
            this.actualDraggableItem = this.draggebleItems.get(++idx);
        } catch (IndexOutOfBoundsException e) {
            this.actualDraggableItem = null;
        }
    }

    public boolean updateInputValues(DraggableNode item) {
        for (PortItem portItem: item.getBlockItem().getInputPorts()) {
            if(portItem.getInputBlockId() != 0) {
                for(DraggableNode draggableNodeItem: this.boardItem.getDraggableItems()) {
                    if(portItem.getInputBlockId() == draggableNodeItem.getBlockItem().getBlockItemId()) {
                        if(item.getBlockItem().getType() == BlockItemInterface.type.SUB || item.getBlockItem().getType() == BlockItemInterface.type.SUM) {
                            if(draggableNodeItem.getBlockItem().getOutputPort().getValue() == 0) {
                                return false;
                            }
                        } if(item.getBlockItem().getType() == BlockItemInterface.type.MUL || item.getBlockItem().getType() == BlockItemInterface.type.DIV) {
                            if(draggableNodeItem.getBlockItem().getOutputPort().getValue() == 1) {
                                return false;
                            }
                        }
                        portItem.setValue(draggableNodeItem.getBlockItem().getOutputPort().getValue());
                    }
                }
            }
        }
        return true;
    }

    public void setBoardBlockItemOutputValue(DraggableNode item) {
        for(DraggableNode actualItem : this.boardItem.getDraggableItems()) {
            if(item.getBlockItem().getBlockItemId() == actualItem.getBlockItem().getBlockItemId()) {
                actualItem.getBlockItem().getOutputPort().setValue(item.getBlockItem().getOutputPort().getValue());
            }
        }
    }

    @Override
    public double solveWholeBoard() {
        try {
            do {
                this.nextStep();
    //            System.out.print(this.previousDraggableItem.getOutputPort().getValue());
            } while (this.actualDraggableItem != null);
            return this.previousDraggableItem.getBlockItem().getOutputPort().getValue();
        } catch (StackOverflowError e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Vytvoření cyklu není povoleno!");

            alert.showAndWait();
        }
        return 0.0;
    }

    public BoardItemInterface getBoardItem() {
        return boardItem;
    }
}
