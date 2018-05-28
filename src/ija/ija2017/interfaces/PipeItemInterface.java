package ija.ija2017.interfaces;

import java.io.Serializable;

public interface PipeItemInterface extends Serializable {

    int getPipeItemId();

    int getInPortId();

    int getOutPortId();
}
