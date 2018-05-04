package ija.ija2017.controllers;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.BoardItemInterface;
import ija.ija2017.interfaces.ExecuteControllerInterface;
import ija.ija2017.items.board.BoardItem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.ArrayList;

/**
 * Created by davidillichmann on 02.05.18.
 */
public class ExecuteController implements ExecuteControllerInterface {

    private ArrayList<BlockItemInterface> blockItems;
    private BoardItemInterface boardItem;
    private BlockItemInterface actualBlockItem;
    private BlockItemInterface previousBlockItem;

    //TODO
//    public ExecuteController(BoardItemInterface boardItem) {
//        this.boardItem = boardItem;
//        this.blockItems = boardItem.getBlockItems();
//        this.actualBlockItem = this.blockItems.get(0);
//    }

//    public ExecuteController() {
//        this.boardItem = new BoardItem();
//        this.blockItems = boardItem.getBlockItems();
//        this.actualBlockItem = this.blockItems.get(0);
//    }

    @Override
    public void init() {
        this.boardItem = new BoardItem();
        this.blockItems = boardItem.getBlockItems();
    }

    public void pressButton(ActionEvent event) {
        System.out.println("HELOOOOOO");
    }

    @Override
    public void setBoardName(String name) {
        this.boardItem.setName(name);
    }

    @Override
    public void nextStep() {
        if (this.actualBlockItem.execute()) {
            this.blockItems.remove(this.actualBlockItem);
        }
        int idx = this.blockItems.indexOf(actualBlockItem);
        this.previousBlockItem = actualBlockItem;
        try {
            this.actualBlockItem = this.blockItems.get(++idx);
        } catch (IndexOutOfBoundsException e) {
            this.actualBlockItem = null;
        }
    }

    @Override
    public double solveWholeBoard() {
        do {
            this.nextStep();
//            System.out.print(this.previousBlockItem.getOutputPort().getValue());
        } while (this.actualBlockItem != null);
        return this.previousBlockItem.getOutputPort().getValue();
    }
}
