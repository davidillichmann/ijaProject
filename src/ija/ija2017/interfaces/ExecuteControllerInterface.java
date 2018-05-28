package ija.ija2017.interfaces;

import java.io.Serializable;

public interface ExecuteControllerInterface extends Serializable {

    void setBoardName(String name);

    void nextStep();

    double solveWholeBoard();
}
