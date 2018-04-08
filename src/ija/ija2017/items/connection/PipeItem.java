/**
 *
 */
package ija.ija2017.items.connection;

import ija.ija2017.interfaces.PipeItemInterface;

/**
 * @author xillic00
 */
public class PipeItem implements PipeItemInterface {

    static private int currentPipeItemId = 1;

    private int pipeItemId;
    private int inPortId;
    private int outPortId;

    public PipeItem(int inPortId, int outPortId) {
        this.pipeItemId = ++currentPipeItemId;
        this.inPortId = inPortId;
        this.outPortId = outPortId;
    }

    @Override
    public int getPipeItemId() {
        return pipeItemId;
    }

    @Override
    public int getInPortId() {
        return inPortId;
    }

    @Override
    public int getOutPortId() {
        return outPortId;
    }
}
