/**
 * 
 */
package ija.ija2017.items.block;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.items.connection.PortItem;

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

    public void addInputPort(PortItem portItem) {
        if (inputPorts == null) {
            inputPorts = new ArrayList<PortItem>();
        }

        this.inputPorts.add(portItem);
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
