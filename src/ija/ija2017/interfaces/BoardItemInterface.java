package ija.ija2017.interfaces;

import ija.ija2017.drag.DraggableNode;

import java.io.Serializable;
import java.util.ArrayList;

public interface BoardItemInterface extends Serializable {

    public ArrayList<DraggableNode> getDraggableItems();

//    boolean deleteBlockItem(BlockItemInterface blockItem);

    public boolean addBlockItem(DraggableNode draggableItem);

    boolean saveItem();

    boolean loadItem();

    String getName();

    boolean setName(String name);
}
