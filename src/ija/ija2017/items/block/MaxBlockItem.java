/**
 *
 */
package ija.ija2017.items.block;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.interfaces.PortItemInterface;
import ija.ija2017.items.connection.PortItem;

/**
 * @author xfryct00
 *
 */
public class MaxBlockItem extends BlockItemAbstract {

    public MaxBlockItem() {
        super.BlockItemAbstract();
        this.type = BlockItemInterface.type.MAX;
        this.blockItemId = currentBlockItemId++;
        this.outputPort = new PortItem(PortItemInterface.type.output);
    }

    @Override
    public boolean execute() {
        double result = 0.0;

        for (int i = 0; i < getInputPorts().size(); i++){
            if(result < getInputPorts().get(i).getValue()) {
                result = getInputPorts().get(i).getValue();
            }
        }

        setOutputPortValue(result);
        return true;
    }
}
