/**
 * 
 */
package ija.ija2017.items.connection;

import ija.ija2017.interfaces.PortItemInterface;

/**
 * @author davidillichmann
 *
 */
public class PortItem implements PortItemInterface {

    static private int currentPortItemId = 1;

    private int portItemId;
    private int pipeItemId;
    private String name;
    private type type;
    private double value;



    public PortItem(type type) {
        this.type = type;
        this.portItemId = currentPortItemId++; // generate TODO
    }

    public String getName() {
        return this.name;
    }

    public double getValue() {
        return this.value;
    }

    public int getPipeItemId() {
        return this.pipeItemId;
    }

    public int getPortItemId() {
        return this.portItemId;
    }

    public type getType() {
        return this.type;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setPipeItemId(int pipeItemId) {
        this.pipeItemId = pipeItemId;
    }
}
