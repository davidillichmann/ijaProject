package ija.ija2017.drag;

import ija.ija2017.interfaces.BlockItemInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Created by davidillichmann on 02.05.18.
 *
 *
 */
public class DragBlock extends AnchorPane implements BlockItemInterface {

    private type blockType = null;

    public DragBlock() {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../resources/DragBlock.fxml")
        );

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
    }

    public type getType() {
        return this.blockType;
    }

    public void setType(type type) {

        this.blockType = type;

        getStyleClass().clear();
        getStyleClass().add("dragblock");

        switch (this.blockType) {

            case SUM:
                getStyleClass().add("icon-blue");
                break;

            case SUB:
                getStyleClass().add("icon-red");
                break;

            case MUL:
                getStyleClass().add("icon-green");
                break;

            case DIV:
                getStyleClass().add("icon-yellow");
                break;
            case CMP:
                getStyleClass().add("icon-grey");
                break;
            default:
                break;
        }
    }

    public void relocateToPoint(Point2D p) {

        Point2D localCoords = getParent().sceneToLocal(p);
        relocate((int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)), (int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2)));
    }
}
