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
        this.portItemId = this.currentPortItemId++; // generate TODO
    }
}
