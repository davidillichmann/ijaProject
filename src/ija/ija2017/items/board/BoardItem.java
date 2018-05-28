/**
 *
 */
package ija.ija2017.items.board;

import ija.ija2017.drag.DraggableNode;
import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.BoardItemInterface;

import java.util.ArrayList;

/**
 * @author xillic00
 */
public class BoardItem implements BoardItemInterface {

    private String name;

    private ArrayList<DraggableNode> draggableItems;

    public BoardItem() {
        this.draggableItems = new ArrayList<>();
    }

//    @Override
    public ArrayList<DraggableNode> getDraggableItems() {
        return this.draggableItems;
    }

//    @Override
    public boolean deleteBlockItem(DraggableNode draggableItem) {
        return this.draggableItems.remove(draggableItem);
    }

//    @Override
    public boolean addBlockItem(DraggableNode draggableItem) {
        return this.draggableItems.add(draggableItem);
    }


    @Override
    public boolean saveItem() {
        //TODO
        return false;
    }

    @Override
    public boolean loadItem() {
        //TODO
        return false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
            return true;
        }
        return false;
    }


}
