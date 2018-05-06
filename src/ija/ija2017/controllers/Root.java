package ija.ija2017.controllers;

import ija.ija2017.drag.DragBlock;
import ija.ija2017.drag.DragContainer;
import ija.ija2017.drag.DraggableNode;
import ija.ija2017.drag.NodeLink;
import ija.ija2017.interfaces.BlockItemInterface;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by davidillichmann on 04.05.18.
 *
 *
 */
public class Root extends AnchorPane {

    @FXML
    SplitPane base_pane;
    @FXML
    AnchorPane right_pane;
    @FXML
    VBox left_pane;

    private EventHandler blockDragOverRoot = null;
    private EventHandler blockDragOverRightPane = null;
    private EventHandler blockDragDropped = null;

    private DragBlock dragOverBlock = null;

    public Root() {

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("../resources/Root.fxml")
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
        this.dragOverBlock = new DragBlock();
        this.dragOverBlock.setVisible(false);
        this.dragOverBlock.setOpacity(0.65);
        this.getChildren().add(this.dragOverBlock);

        //inicializace vsech bloku
        for (int i = 0; i < 5; i++) {
            DragBlock dragBlock = new DragBlock();
            Node borderPane = dragBlock.getChildren().get(0);
            if(borderPane instanceof BorderPane) {
                Node label = ((BorderPane) borderPane).getChildren().get(0);
                if(label instanceof Label) {
                    ((Label) label).setText(BlockItemInterface.type.values()[i].toString());
                }
            }

            addDragDetection(dragBlock);
            dragBlock.setType(BlockItemInterface.type.values()[i]);
            this.left_pane.getChildren().add(dragBlock);
        }
        buildDragHandlers();
    }

    private void buildDragHandlers() {
        blockDragOverRoot = (EventHandler<DragEvent>) event -> {

            Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

            if (!right_pane.boundsInLocalProperty().get().contains(p)) {

                event.acceptTransferModes(TransferMode.ANY);
                dragOverBlock.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                return;
            }

            event.consume();
        };

        blockDragOverRightPane = (EventHandler<DragEvent>) event -> {

            event.acceptTransferModes(TransferMode.ANY);

            dragOverBlock.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
            event.consume();
        };

        blockDragDropped = (EventHandler<DragEvent>) event -> {

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            container.addData("scene_coords",new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            content.put(DragContainer.AddNode, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);

        };

        this.setOnDragDone(event -> {

            right_pane.removeEventHandler(DragEvent.DRAG_OVER, blockDragOverRightPane);
            right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, blockDragDropped);
            base_pane.removeEventHandler(DragEvent.DRAG_OVER, blockDragOverRoot);

            dragOverBlock.setVisible(false);

            DragContainer container = (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            if (container != null) {
                if (container.getValue("scene_coords") != null) {

                    DraggableNode node = new DraggableNode();

                    node.setType(DragBlock.type.valueOf(container.getValue("type")));
                    right_pane.getChildren().add(node);

                    Point2D cursorPoint = container.getValue("scene_coords");

                    node.relocateToPoint(new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32));
                }
            }

            container = (DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

            if (container != null) {
                System.out.println ("working"); //TODO
                if (container.getValue("type") != null)
                    System.out.println ("Moved node " + container.getValue("type"));
            }

            container = (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

            if (container != null) {
                String sourceId = container.getValue("source");
                String targetId = container.getValue("target");

                if (sourceId != null && targetId != null) {

                    //	System.out.println(container.getData());
                    NodeLink link = new NodeLink();

                    right_pane.getChildren().add(0,link);

                    DraggableNode source = null;
                    DraggableNode target = null;

                    for (Node n: right_pane.getChildren()) {

                        if (n.getId() == null)
                            continue;

                        if (n.getId().equals(sourceId))
                            source = (DraggableNode) n;

                        if (n.getId().equals(targetId))
                            target = (DraggableNode) n;

                    }

                    if (source != null && target != null)
                        link.bindEnds(source, target);
                }
            }

            event.consume();
        });
    }

    private void addDragDetection(DragBlock dragBlock) {

        dragBlock.setOnDragDetected(event -> {

            base_pane.setOnDragOver(blockDragOverRoot);
            right_pane.setOnDragOver(blockDragOverRightPane);
            right_pane.setOnDragDropped(blockDragDropped);

            DragBlock block = (DragBlock) event.getSource();

            dragOverBlock.setType(block.getType());
            dragOverBlock.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", dragOverBlock.getType().toString());
            content.put(DragContainer.AddNode, container);

            dragOverBlock.startDragAndDrop(TransferMode.ANY).setContent(content);

            dragOverBlock.setVisible(true);
            dragOverBlock.setMouseTransparent(true);
            event.consume();
        });
    }

}
