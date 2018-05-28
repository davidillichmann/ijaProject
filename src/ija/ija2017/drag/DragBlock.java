package ija.ija2017.drag;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.items.block.*;
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
public class DragBlock extends AnchorPane {

    private BlockItemAbstract blockItem;

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

    public BlockItemAbstract getBlockItem() {
        return this.blockItem;
    }

    public void setBlockItem(BlockItemInterface.type type) {

//        this.blockType = type;

        getStyleClass().clear();
        getStyleClass().add("dragblock");

        switch (type) {

            case SUM:
                getStyleClass().add("icon-blue");
                this.blockItem = new SumBlockItem();
                break;

            case SUB:
                getStyleClass().add("icon-red");
                this.blockItem = new SubBlockItem();
                break;

            case MUL:
                getStyleClass().add("icon-green");
                this.blockItem = new MulBlockItem();
                break;

            case DIV:
                getStyleClass().add("icon-yellow");
                this.blockItem = new DivBlockItem();
                break;
            case MAX:
                getStyleClass().add("icon-grey");
                this.blockItem = new MaxBlockItem();
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
