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

    static protected int currentPortItemId = 1;

    protected int portItemId;
    protected int pipeItemId;
    protected String name;
    protected int type;
    protected double value;

    public PortItem(int type) {
        this.type = type;
        this.portItemId = currentPortItemId++; // generate TODO
    }

    public String getName() {
        return this.name;
    }

    public double getValue() {
        return this.value;
    }

    public static int getCurrentPortItemId() {
        return currentPortItemId;
    }

    public int getPipeItemId() {
        return this.pipeItemId;
    }

    public int getPortItemId() {
        return this.portItemId;
    }

    public int getType() {
        return this.type;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
