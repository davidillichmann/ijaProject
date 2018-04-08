package ija.ija2017.interfaces;

public interface PortItemInterface {

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

}
