package ija.ija2017.drag;

import ija.ija2017.interfaces.BlockItemInterface;
import ija.ija2017.items.block.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by davidillichmann on 02.05.18.
 */
public class DraggableNode extends AnchorPane implements Serializable {

    private BlockItemAbstract blockItem;

    @FXML
    transient AnchorPane left_link_handle;
    @FXML
    transient AnchorPane right_link_handle;

    private transient NodeLink dragLink = null;
    private transient AnchorPane right_pane = null;

    private transient EventHandler<MouseEvent> linkHandleDragDetected;
    private transient EventHandler<DragEvent> linkHandleDragDropped;
    private transient EventHandler<DragEvent> contextLinkDragOver;
    private transient EventHandler<DragEvent> contextLinkDragDropped;

    private transient EventHandler contextDragOver;
    private transient EventHandler contextDragDropped;

    private transient Point2D dragOffset = new Point2D(0.0, 0.0);
    private double x;
    private double y;

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public Point2D getDragOffset() {
        return dragOffset;
    }

    public double getY() {

        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @FXML
    private transient Label title_bar;
    @FXML
    private transient Label close_button;
    @FXML
    private transient AnchorPane port1;
    @FXML
    private transient AnchorPane port2;
    @FXML
    private transient AnchorPane port3;
    @FXML
    private transient AnchorPane port4;

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
        valuesHandler();


        left_link_handle.setOnDragDetected(linkHandleDragDetected);
        right_link_handle.setOnDragDetected(linkHandleDragDetected);

        left_link_handle.setOnDragDropped(linkHandleDragDropped);
        right_link_handle.setOnDragDropped(linkHandleDragDropped);

        dragLink = new NodeLink();
        dragLink.setVisible(false);

        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                right_pane = (AnchorPane) DraggableNode.this.getParent();
            }
        });
    }

    public void buildNodeDragHandlers() {

        contextDragOver = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                event.acceptTransferModes(TransferMode.ANY);
                DraggableNode.this.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                event.consume();
            }
        };

        contextDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                DraggableNode.this.getParent().setOnDragOver(null);
                DraggableNode.this.getParent().setOnDragDropped(null);

                event.setDropCompleted(true);

                event.consume();
            }
        };

        close_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
            }
        });

        title_bar.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                DraggableNode.this.getParent().setOnDragOver(null);
                DraggableNode.this.getParent().setOnDragDropped(null);

                DraggableNode.this.getParent().setOnDragOver(contextDragOver);
                DraggableNode.this.getParent().setOnDragDropped(contextDragDropped);

                dragOffset = new Point2D(event.getX(), event.getY());

                DraggableNode.this.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData("type", blockItem.getType().toString());
                content.put(DragContainer.AddNode, container);

                DraggableNode.this.startDragAndDrop(TransferMode.ANY).setContent(content);

                event.consume();
            }
        });
    }

    private void buildLinkDragHandlers() {

        linkHandleDragDetected = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                DraggableNode.this.getParent().setOnDragOver(null);
                DraggableNode.this.getParent().setOnDragDropped(null);

                DraggableNode.this.getParent().setOnDragOver(contextLinkDragOver);
                DraggableNode.this.getParent().setOnDragDropped(contextLinkDragDropped);

                right_pane.getChildren().add(0, dragLink);

                dragLink.setVisible(false);

                Point2D p = new Point2D(
                        DraggableNode.this.getLayoutX() + (DraggableNode.this.getWidth() / 2.0),
                        DraggableNode.this.getLayoutY() + (DraggableNode.this.getHeight() / 2.0)
                );

                dragLink.setStart(p);

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData("source", DraggableNode.this.getId());

                content.put(DragContainer.AddLink, container);

                DraggableNode.this.startDragAndDrop(TransferMode.ANY).setContent(content);

                event.consume();
            }
        };

        linkHandleDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                DraggableNode.this.getParent().setOnDragOver(null);
                DraggableNode.this.getParent().setOnDragDropped(null);

                DragContainer container =
                        (DragContainer) event.getDragboard().getContent(DragContainer.AddLink);

                if (container == null)
                    return;

                dragLink.setVisible(false);
                right_pane.getChildren().remove(0);

                AnchorPane link_handle = (AnchorPane) event.getSource();

                DraggableNode parent = (DraggableNode) link_handle.getParent().getParent().getParent();

                ClipboardContent content = new ClipboardContent();

                container.addData("target", DraggableNode.this.getId());

                content.put(DragContainer.AddLink, container);

                event.getDragboard().setContent(content);
                event.setDropCompleted(true);
                event.consume();
            }
        };

        contextLinkDragOver = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);

                if (!dragLink.isVisible())
                    dragLink.setVisible(true);

                dragLink.setEnd(new Point2D(event.getX(), event.getY()));

                event.consume();

            }
        };

        //drop event for link creation
        contextLinkDragDropped = new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println("context link drag dropped");

                DraggableNode.this.getParent().setOnDragOver(null);
                DraggableNode.this.getParent().setOnDragDropped(null);

                dragLink.setVisible(false);
                right_pane.getChildren().remove(0);

                event.setDropCompleted(true);
                event.consume();
            }
        };

    }


    public BlockItemAbstract getBlockItem() {
        return this.blockItem;
    }

    public void setWholeBlockItem(BlockItemAbstract item) {
        this.blockItem = item;
    }

    public void setBlockItem(BlockItemInterface.type type) {
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

    public void valuesHandler() {
        port1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DraggableNode.this.showDialogGetInputPortValues(0);
                DraggableNode.this.printInputValues();
            }
        });
        port2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DraggableNode.this.showDialogGetInputPortValues(1);
                DraggableNode.this.printInputValues();
            }
        });
        port3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DraggableNode.this.showDialogGetInputPortValues(2);
                DraggableNode.this.printInputValues();
            }
        });
        port4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DraggableNode.this.showDialogGetInputPortValues(3);
                DraggableNode.this.printInputValues();
            }
        });
    }

    private void showDialogGetInputPortValues(int portIndex) {
        TextInputDialog dialog = new TextInputDialog(Double.toString(blockItem.getInputPorts().get(portIndex).getValue()));
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setTitle("Input port " + (portIndex + 1));
        dialog.setContentText("Enter value of input port " + (portIndex + 1) + ":");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                blockItem.setInputPortValue(portIndex, Double.parseDouble(result.get()));
                System.out.println("Your name: " + blockItem.getInputPorts().get(portIndex).getValue());
            } catch (NumberFormatException e) {
                showErrorDialog();
            }
        }
    }

    public void printInputValues() {
        Node text = port1.getChildren().get(0);
        if (text instanceof Text) {
            ((Text) text).setText(String.valueOf(blockItem.getInputPorts().get(0).getValue()));
        }
        text = port2.getChildren().get(0);
        if (text instanceof Text) {
            ((Text) text).setText(String.valueOf(blockItem.getInputPorts().get(1).getValue()));
        }
        text = port3.getChildren().get(0);
        if (text instanceof Text) {
            ((Text) text).setText(String.valueOf(blockItem.getInputPorts().get(2).getValue()));
        }
        text = port4.getChildren().get(0);
        if (text instanceof Text) {
            ((Text) text).setText(String.valueOf(blockItem.getInputPorts().get(3).getValue()));
        }
    }

    private void showErrorDialog() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText("Ooops, there was an error!");

        alert.showAndWait();
    }

    public void relocateToPoint(Point2D p) {

        Point2D localCoords = getParent().sceneToLocal(p);
        this.setX((int) localCoords.getX() - dragOffset.getX());
        this.setY((int) localCoords.getY() - dragOffset.getY());
        relocate((int) (localCoords.getX() - dragOffset.getX()), (int) (localCoords.getY() - dragOffset.getY()));
    }

    public void registerLink(String linkId) {
        linkIds.add(linkId);
    }

    public Label getTitle_bar() {
        return title_bar;
    }

    public void setExecuting() {
        getStyleClass().clear();
        getStyleClass().add("dragblock");
        getStyleClass().add("icon-execute");
    }

    public void setNormalStyle() {
        getStyleClass().clear();
        getStyleClass().add("dragblock");

        switch (this.getBlockItem().getType()) {

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
            case MAX:
                getStyleClass().add("icon-grey");
                break;
            default:
                break;
        }
    }

}
