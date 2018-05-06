package ija.ija2017.drag;

import ija.ija2017.interfaces.BlockItemInterface;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

/**
 * Created by davidillichmann on 02.05.18.
 *
 */
public class DraggableNode extends AnchorPane implements BlockItemInterface {

    private type blockType = null;

    @FXML
    AnchorPane left_link_handle;
    @FXML
    AnchorPane right_link_handle;

    private NodeLink dragLink = null;
    private AnchorPane right_pane = null;

    private EventHandler<MouseEvent> linkHandleDragDetected;
    private EventHandler<DragEvent> linkHandleDragDropped;
    private EventHandler<DragEvent> contextLinkDragOver;
    private EventHandler<DragEvent> contextLinkDragDropped;

    private EventHandler contextDragOver;
    private EventHandler contextDragDropped;

    private Point2D dragOffset = new Point2D(0.0, 0.0);

    @FXML
    private Label title_bar;
    @FXML
    private Label close_button;

    private final DraggableNode self;
    private final List linkIds = new ArrayList();

    public DraggableNode() {
        self = this;
        setId(UUID.randomUUID().toString());

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../resources/DraggableNode.fxml")
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
        buildNodeDragHandlers();
        buildLinkDragHandlers();


        left_link_handle.setOnDragDetected(linkHandleDragDetected);
        right_link_handle.setOnDragDetected(linkHandleDragDetected);

        left_link_handle.setOnDragDropped(linkHandleDragDropped);
        right_link_handle.setOnDragDropped(linkHandleDragDropped);

        dragLink = new NodeLink();
        dragLink.setVisible(false);

        parentProperty().addListener((observable, oldValue, newValue) -> right_pane = (AnchorPane) getParent());
    }

    public void buildNodeDragHandlers() {

        contextDragOver = (EventHandler<DragEvent>) event -> {

            event.acceptTransferModes(TransferMode.ANY);
            relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            event.consume();
        };

        contextDragDropped = (EventHandler<DragEvent>) event -> {
            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            event.setDropCompleted(true);

            event.consume();
        };

        close_button.setOnMouseClicked(event -> {
            AnchorPane parent = (AnchorPane) self.getParent();
            parent.getChildren().remove(self);
            for (ListIterator<String> iterId = linkIds.listIterator(); iterId.hasNext(); ) {
                String id = iterId.next();
                for (ListIterator<Node> iterNode = parent.getChildren().listIterator(); iterNode.hasNext(); ) {
                    Node node = iterNode.next();
                    if (node.getId() == null)
                        continue;
                    if (node.getId().equals(id))
                        iterNode.remove();
                }
                iterId.remove();
            }
        });

        title_bar.setOnDragDetected(event -> {

            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(contextDragOver);
            getParent().setOnDragDropped(contextDragDropped);

            dragOffset = new Point2D(event.getX(), event.getY());

            relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", blockType.toString());
            content.put(DragContainer.AddNode, container);

            startDragAndDrop(TransferMode.ANY).setContent(content);

            event.consume();
        });
    }

    private void buildLinkDragHandlers() {

        linkHandleDragDetected = event -> {

            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            getParent().setOnDragOver(contextLinkDragOver);
            getParent().setOnDragDropped(contextLinkDragDropped);

            right_pane.getChildren().add(0, dragLink);

            dragLink.setVisible(false);

            Point2D p = new Point2D(
                    getLayoutX() + (getWidth() / 2.0),
                    getLayoutY() + (getHeight() / 2.0)
            );

            dragLink.setStart(p);

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("source", getId());

            content.put(DragContainer.AddLink, container);

            startDragAndDrop(TransferMode.ANY).setContent(content);

            event.consume();
        };

        linkHandleDragDropped = event -> {

            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            DragContainer container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

            if (container == null)
                return;

            dragLink.setVisible(false);
            right_pane.getChildren().remove(0);

            AnchorPane link_handle = (AnchorPane) event.getSource();

            DraggableNode parent = (DraggableNode) link_handle.getParent().getParent().getParent();

            ClipboardContent content = new ClipboardContent();

            container.addData("target", getId());

            content.put(DragContainer.AddLink, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);
            event.consume();
        };

        contextLinkDragOver = event -> {
            event.acceptTransferModes(TransferMode.ANY);

            if (!dragLink.isVisible())
                dragLink.setVisible(true);

            dragLink.setEnd(new Point2D(event.getX(), event.getY()));

            event.consume();

        };

        //drop event for link creation
        contextLinkDragDropped = event -> {
            System.out.println("context link drag dropped");

            getParent().setOnDragOver(null);
            getParent().setOnDragDropped(null);

            dragLink.setVisible(false);
            right_pane.getChildren().remove(0);

            event.setDropCompleted(true);
            event.consume();
        };

    }


    public type getType() {
        return this.blockType;
    }

    public void setType(type type) {

        this.blockType = type;

        getStyleClass().clear();
        getStyleClass().add("dragblock");

        setTitleBar(this.blockType.toString());

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

    private void setTitleBar(String title) {
        Node vBox = self.getChildren().get(0);
        if (vBox instanceof VBox) {
            Node gridPane = ((VBox) vBox).getChildren().get(0);
            if (gridPane instanceof GridPane) {
                for (Node label : ((GridPane) gridPane).getChildren()) {
                    if (label instanceof Label && label.getId().equals("title_bar")) {
                        ((Label) label).setText(title);
                    }
                }
            }
        }
    }

    public void relocateToPoint(Point2D p) {

        Point2D localCoords = getParent().sceneToLocal(p);
        relocate((int) (localCoords.getX() - dragOffset.getX()), (int) (localCoords.getY() - dragOffset.getY()));
    }

    public void registerLink(String linkId) {
        linkIds.add(linkId);
    }
}
