/**
 * 
 */
package ija.ija2017.items.block;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.connection.PortItem;
import javafx.event.Event;

import java.util.ArrayList;

/**
 * @author xfryct00
 *
 */
public abstract class BlockItemAbstract implements BlockItemInterface {

    static protected int currentBlockItemId = 1;
    protected int blockItemId;
    protected type type;
    protected ArrayList<PortItem> inputPorts;
    protected PortItem outputPort;

    public void BlockItemAbstract() {
        this.inputPorts = new ArrayList<PortItem>();
        for(int i = 0; i < 4; i++) {
            this.inputPorts.add(i, new PortItem(PortItemInterface.type.input));
        }
    }

//    @Override
    public ArrayList<PortItem> getInputPorts() {
        return inputPorts;
    }

//    @Override
    public int getBlockItemId() {
        return blockItemId;
    }

//    @Override
    public PortItem getOutputPort() {
        return outputPort;
    }

    @Override
    public BlockItemInterface.type getType() {
        return type;
    }

    public void setInputPortValue(int index, double value) {
        this.inputPorts.get(index).setValue(value);
//        if (inputPorts == null) {
//            inputPorts = new ArrayList<PortItem>();
//        }
//        this.inputPorts.add(portItem);
    }

    public void setLink(int inputBlockId) {
        for (int i = 0; i < 4; i++) {
            if(this.getInputPorts().get(i).getValue() == 0 || this.getInputPorts().get(i).getValue() == 1) {
                if(this.getInputPorts().get(i).getInputBlockId() == 0) {
                    //nasetovani input block id do input portu
                    this.getInputPorts().get(i).setInputBlockId(inputBlockId);
                    return;
                }
            } else {
                //vyhodit upozorneni ze mam vsechny porty plne
            }
        }
    }

    public void setOutputPortValue(double value) {
        this.outputPort.setValue(value);
    }

//    @Override
    public void setOutputPort(PortItem outputPort) {
        this.outputPort = outputPort;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
