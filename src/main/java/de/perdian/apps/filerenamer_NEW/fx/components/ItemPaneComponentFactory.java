package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerItem;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.*;
import java.util.function.Function;

class ItemPaneComponentFactory {

    private final ReadOnlyObjectProperty<FilerenamerItem> currentItem;
    private final ItemsModel model;
    private final ObservableBooleanValue globalBusy;
    private final Map<FilerenamerItem, List<Node>> nodesByItem;

    ItemPaneComponentFactory(ReadOnlyObjectProperty<FilerenamerItem> currentItemProperty, ItemsModel model, ObservableBooleanValue globalBusy) {
        this.currentItem = currentItemProperty;
        this.model = model;
        this.globalBusy = globalBusy;
        this.nodesByItem = new HashMap<>();
    }

    TextField createReadOnlyTextField(Function<FilerenamerItem, ReadOnlyStringProperty> propertyFunction) {
        TextField textField = this.createTextField();
        textField.setFocusTraversable(false);
        textField.textProperty().bind(propertyFunction.apply(this.getCurrentItem()));
        return textField;
    }

    TextField createEditableTextField(Function<FilerenamerItem, StringProperty> propertyFunction) {
        TextField textField = this.createTextField();
        textField.textProperty().bindBidirectional(propertyFunction.apply(this.getCurrentItem()));
        return textField;
    }

    private TextField createTextField() {
        FilerenamerItem currentItem = this.getCurrentItem();
        TextField textField = new TextField();
        textField.focusedProperty().addListener((_, _, newValue) -> {
            if (newValue) {
                if (!Objects.equals(this.getModel().getActiveItem(), currentItem)) {
                    this.getModel().setActiveItem(currentItem);
                }
            }
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> this.handleKeyPressedEvent(currentItem, event));
        this.getNodesByItem().computeIfAbsent(currentItem, _ -> new ArrayList<>()).add(textField);
        return textField;
    }

    private void handleKeyPressedEvent(FilerenamerItem currentItem, KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.PAGE_UP) {
            this.handleItemDirectionChange(currentItem, -1, keyEvent.getSource());
        } else if (keyEvent.getCode() == KeyCode.HOME && keyEvent.isMetaDown()) {
            this.handleItemDirectionChange(currentItem, 0, keyEvent.getSource());
        } else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.PAGE_DOWN) {
            this.handleItemDirectionChange(currentItem, 1, keyEvent.getSource());
        } else if (keyEvent.getCode() == KeyCode.END && keyEvent.isMetaDown()) {
            this.handleItemDirectionChange(currentItem, Integer.MAX_VALUE, keyEvent.getSource());
        } else if (keyEvent.getCode() == KeyCode.LEFT && keyEvent.isMetaDown()) {
            this.handleNodeDirectionChange(currentItem, -1, keyEvent.getSource());
        } else if (keyEvent.getCode() == KeyCode.RIGHT && keyEvent.isMetaDown()) {
            this.handleNodeDirectionChange(currentItem, 1, keyEvent.getSource());
        }
    }

    private void handleItemDirectionChange(FilerenamerItem currentItem, int direction, Object source) {
        int currentItemIndex = this.getModel().getAllItems().indexOf(currentItem);
        if (currentItemIndex > -1) {
            int targetItemIndex = switch (direction) {
                case 0 -> 0;
                case Integer.MAX_VALUE -> this.getModel().getAllItems().size() - 1;
                default -> Math.clamp(0, currentItemIndex + direction, this.getModel().getAllItems().size() - 1);
            };
            if (targetItemIndex >= 0 && targetItemIndex < this.getModel().getAllItems().size()) {
                FilerenamerItem targetItem = this.getModel().getAllItems().get(targetItemIndex);
                List<Node> currentItemNodes = this.getNodesByItem().get(currentItem);
                List<Node> targetItemNodes = this.getNodesByItem().get(targetItem);
                if (currentItemNodes != null && targetItemNodes != null) {
                    int sourceNodeIndex = currentItemNodes.indexOf(source);
                    if (sourceNodeIndex > -1 && sourceNodeIndex < targetItemNodes.size()) {
                        Node targetNode = targetItemNodes.get(sourceNodeIndex);
                        if (targetNode.isFocusTraversable()) {
                            targetNode.requestFocus();
                        }
                    }
                }
            }
        }
    }

    private void handleNodeDirectionChange(FilerenamerItem currentItem, int direction, Object source) {
        List<Node> nodeList = this.getNodesByItem().get(currentItem);
        int currentNodeIndex = nodeList == null ? -1 : nodeList.indexOf(source);
        for (int newNodeIndex = currentNodeIndex + direction; newNodeIndex >= 0 && newNodeIndex < nodeList.size(); newNodeIndex += direction) {
            Node nodeAtIndex = nodeList.get(newNodeIndex);
            if (nodeAtIndex.isFocusTraversable()) {
                nodeAtIndex.requestFocus();
                break;
            }
        }
    }

    private FilerenamerItem getCurrentItem() {
        return currentItem.get();
    }
    private ReadOnlyObjectProperty<FilerenamerItem> currentItemProperty() {
        return currentItem;
    }

    private ItemsModel getModel() {
        return this.model;
    }

    private boolean isGlobalBusy() {
        return this.globalBusy.get();
    }
    private ObservableBooleanValue globalBusyProperty() {
        return this.globalBusy;
    }

    private Map<FilerenamerItem, List<Node>> getNodesByItem() {
        return this.nodesByItem;
    }

}
