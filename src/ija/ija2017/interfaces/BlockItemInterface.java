package ija.ija2017.interfaces;

import java.io.Serializable;
import java.util.ArrayList;
import ija.ija2017.items.connection.PortItem;

public interface BlockItemInterface extends Serializable {

    enum type {
        SUM, SUB, MUL, DIV, MAX
    }

    boolean execute();
//
    PortItem getOutputPort();
//
//    ArrayList<PortItem> getInputPorts();
//
//    int getBlockItemId();
//
    type getType();
//
//    void setOutputPortValue(double value);
//
//    void setOutputPort(PortItem item);
//
//    void addInputPort(PortItem item);
}
