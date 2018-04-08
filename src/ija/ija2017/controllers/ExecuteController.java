/**
 *
 */
package ija.ija2017.controllers;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.BoardItemInterface;
import ija.ija2017.interfaces.ExecuteControllerInterface;

import java.util.ArrayList;

/**
 * @author xillic00
 */
public class ExecuteController implements ExecuteControllerInterface {

    private ArrayList<BlockItemInterface> blockItems;
//    private BoardItemInterface boardItem;
    private BlockItemInterface actualBlockItem;
    private BlockItemInterface previousBlockItem;

    public ExecuteController(BoardItemInterface boardItem) {
//        this.boardItem = boardItem;
        this.blockItems = boardItem.getBlockItems();
        this.actualBlockItem = this.blockItems.get(0);
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
