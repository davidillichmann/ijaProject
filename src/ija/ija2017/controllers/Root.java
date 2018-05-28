package ija.ija2017.controllers;

import ija.ija2017.drag.DragBlock;
import ija.ija2017.drag.DragContainer;
import ija.ija2017.drag.DraggableNode;
import ija.ija2017.drag.NodeLink;
import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.items.connection.PortItem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;

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
    @FXML
    MenuItem new_board;
    @FXML
    MenuItem load_board;
    @FXML
    MenuItem save_board;

    @FXML
    ExecuteController controllers;

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
            dragBlock.setBlockItem(BlockItemInterface.type.values()[i]);
            this.left_pane.getChildren().add(dragBlock);
        }
        buildBoardHandlers();
        buildDragHandlers();
    }

    private void buildBoardHandlers() {
        new_board.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                newBoard();
            }
        });
        save_board.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveBoard();
            }
        });
        load_board.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                loadBoard();
            }
        });
    }

    private void loadBoard() {
        right_pane.getChildren().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);

        try {
            FileInputStream fileIn = new FileInputStream(file.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);

            ExecuteController savedController = (ExecuteController) in.readObject();
            controllers = new ExecuteController();
            right_pane.getChildren().add(controllers);
            for (DraggableNode nodeItem : savedController.getBoardItem().getDraggableItems()) {
                DraggableNode newNodeItem = new DraggableNode();
                newNodeItem.setBlockItem(nodeItem.getBlockItem().getType());
                newNodeItem.setWholeBlockItem(nodeItem.getBlockItem());
                this.controllers.getBoardItem().addBlockItem(newNodeItem);
                right_pane.getChildren().add(newNodeItem);
                newNodeItem.relocateToPoint(new Point2D(nodeItem.getX() + 109, nodeItem.getY() + 26));
                newNodeItem.printInputValues();
            }
            for(DraggableNode target : controllers.getBoardItem().getDraggableItems()) {
                for(PortItem portItem : target.getBlockItem().getInputPorts()) {
                    if(portItem.getInputBlockId() != 0) {
                        for (DraggableNode source : controllers.getBoardItem().getDraggableItems()) {
                            if (source.getBlockItem().getBlockItemId() == portItem.getInputBlockId()) {
                                NodeLink link = new NodeLink();
                                right_pane.getChildren().add(0, link);
                                link.bindEndsLoad(source, target);
                                break;
                            }
                        }
                    }
                }
            }
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }

    private void saveBoard() {
        FileChooser fileChooser1 = new FileChooser();
        fileChooser1.setTitle("Save");
        File file = fileChooser1.showSaveDialog(null);

        try {
            FileOutputStream fileOut = new FileOutputStream(file.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(controllers);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in " + file.getPath());
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private void newBoard() {
        right_pane.getChildren().clear();
        controllers = new ExecuteController();
        right_pane.getChildren().add(controllers);
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

                    node.setBlockItem(BlockItemInterface.type.valueOf(container.getValue("type")));
                    this.controllers.getBoardItem().addBlockItem(node);
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

            dragOverBlock.setBlockItem(block.getBlockItem().getType());
            dragOverBlock.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", dragOverBlock.getBlockItem().getType().toString());
            content.put(DragContainer.AddNode, container);

            dragOverBlock.startDragAndDrop(TransferMode.ANY).setContent(content);

            dragOverBlock.setVisible(true);
            dragOverBlock.setMouseTransparent(true);
            event.consume();
        });
    }

}
