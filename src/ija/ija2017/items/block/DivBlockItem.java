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
public class DivBlockItem extends BlockItemAbstract {

    public DivBlockItem() {
        super.BlockItemAbstract();
        for (PortItem portItem: this.getInputPorts()) {
            portItem.setValue(1);
        }
        this.type = BlockItemInterface.type.DIV;
        this.blockItemId = currentBlockItemId++;
        this.outputPort = new PortItem(PortItemInterface.type.output);
    }

    @Override
    public boolean execute() {
        double result = 0.0;

//        TODO: if getInputPorts().size() != numberOfPorts ---> false

        for (int i = 0; i < getInputPorts().size(); i++){

            if (getInputPorts().get(i).getValue() == 0){
                System.out.printf("DIV deleni 0\n");
                return false;
//                TODO: exit
            }

            if (i == 0) {
                result = getInputPorts().get(i).getValue();
            }
            else {
                result /= getInputPorts().get(i).getValue();
            }

            System.out.printf("The DIV is %f\n" , result);

            if (result > Double.MAX_VALUE) {
                System.out.printf("MUL pretekl\n");
//                TODO    event
            } else if (result < -Double.MAX_VALUE) {
                System.out.printf("MUL podtekl\n");
//                TODO: EXIT
            }
        }


        setOutputPortValue(result);
        return true;

//        System.out.printf("%f", getOutputPort().getValue());
    }
}
