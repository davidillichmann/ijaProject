package ija.ija2017.interfaces;

import java.io.Serializable;

public interface PortItemInterface extends Serializable {

    enum type {
        input, output
    }

    int getPortItemId();
    type getType();
    String getName();
    int getPipeItemId();
    double getValue();

    void setValue(double value);
    void setPipeItemId(int id);
    public void setInputBlockId(int inputBlockId);
    public int getInputBlockId();

}
