package ija.ija2017.interfaces;

import java.util.ArrayList;

public interface BoardItemInterface {

    ArrayList<BlockItemInterface> getBlockItems();

    boolean deleteBlockItem(BlockItemInterface blockItem);

    boolean addBlockItem(BlockItemInterface blockItem);

    boolean saveItem();

    boolean loadItem();

    String getName();

    boolean setName(String name);
}
