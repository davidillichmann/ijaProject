package ija.ija2017.drag;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by davidillichmann on 02.05.18.
 *
 *
 */
public class NodeLink extends AnchorPane implements Serializable {

    private transient final DoubleProperty controlOffsetX = new SimpleDoubleProperty();
    private transient final DoubleProperty controlOffsetY = new SimpleDoubleProperty();
    private transient final DoubleProperty controlDirectionX1 = new SimpleDoubleProperty();
    private transient final DoubleProperty controlDirectionY1 = new SimpleDoubleProperty();
    private transient final DoubleProperty controlDirectionX2 = new SimpleDoubleProperty();
    private transient final DoubleProperty controlDirectionY2 = new SimpleDoubleProperty();

    @FXML
    transient CubicCurve node_link;

    public NodeLink() {
        setId(UUID.randomUUID().toString());

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../resources/NodeLink.fxml")
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

        controlOffsetX.set(100.0);
        controlOffsetY.set(50.0);

        controlDirectionX1.bind(new When(
                node_link.startXProperty().greaterThan(node_link.endXProperty()))
                .then(-1.0).otherwise(1.0));

        controlDirectionX2.bind(new When(
                node_link.startXProperty().greaterThan(node_link.endXProperty()))
                .then(1.0).otherwise(-1.0));


        node_link.controlX1Property().bind(
                Bindings.add(node_link.startXProperty(), controlOffsetX.multiply(controlDirectionX1))
        );

        node_link.controlX2Property().bind(
                Bindings.add(node_link.endXProperty(), controlOffsetX.multiply(controlDirectionX2))
        );

        node_link.controlY1Property().bind(
                Bindings.add(node_link.startYProperty(), controlOffsetY.multiply(controlDirectionY1))
        );

        node_link.controlY2Property().bind(
                Bindings.add(node_link.endYProperty(), controlOffsetY.multiply(controlDirectionY2))
        );
    }


    public void setStart(Point2D startPoint) {

        node_link.setStartX(startPoint.getX());
        node_link.setStartY(startPoint.getY());
    }

    public void setEnd(Point2D endPoint) {

        node_link.setEndX(endPoint.getX());
        node_link.setEndY(endPoint.getY());
    }

    public void bindEnds(DraggableNode source, DraggableNode target) {
        node_link.startXProperty().bind(
                Bindings.add(source.layoutXProperty(), (source.getWidth() / 2.0)));

        node_link.startYProperty().bind(
                Bindings.add(source.layoutYProperty(), (source.getWidth() / 2.0)));

        node_link.endXProperty().bind(
                Bindings.add(target.layoutXProperty(), (target.getWidth() / 2.0)));

        node_link.endYProperty().bind(
                Bindings.add(target.layoutYProperty(), (target.getWidth() / 2.0)));

        target.getBlockItem().setLink(source.getBlockItem().getBlockItemId());

        source.registerLink(getId());
        target.registerLink(getId());
    }

    public void bindEndsLoad(DraggableNode source, DraggableNode target) {
        node_link.startXProperty().bind(
                Bindings.add(source.layoutXProperty().add(54), (source.getWidth() / 2.0)));

        node_link.startYProperty().bind(
                Bindings.add(source.layoutYProperty().add(13), (source.getWidth() / 2.0)));

        node_link.endXProperty().bind(
                Bindings.add(target.layoutXProperty().add(54), (target.getWidth() / 2.0)));

        node_link.endYProperty().bind(
                Bindings.add(target.layoutYProperty().add(13), (target.getWidth() / 2.0)));

        source.registerLink(getId());
        target.registerLink(getId());
    }
}
