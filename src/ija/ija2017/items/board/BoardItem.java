/**
 *
 */
package ija.ija2017.items.board;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.BoardItemInterface;

import java.util.ArrayList;

/**
 * @author xillic00
 */
public class BoardItem implements BoardItemInterface {

    private String name;

    private ArrayList<BlockItemInterface> blockItems;

    public BoardItem(String name) {
        this.blockItems = new ArrayList<>();
        this.name = name;
    }

    @Override
    public ArrayList<BlockItemInterface> getBlockItems() {
        return this.blockItems;
    }

    @Override
    public boolean deleteBlockItem(BlockItemInterface blockItem) {
        return this.blockItems.remove(blockItem);
    }

    @Override
    public boolean addBlockItem(BlockItemInterface blockItem) {
        return this.blockItems.add(blockItem);
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
