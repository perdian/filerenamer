package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerItem;
import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.List;

class ItemPane extends GridPane {

    ItemPane(FilerenamerItem item, ItemPaneComponentFactory componentFactory) {

        ColumnConstraints existingFileNameColumnConstraints = new ColumnConstraints();
        existingFileNameColumnConstraints.setPercentWidth(50);
        existingFileNameColumnConstraints.setHgrow(Priority.ALWAYS);
        TextField existingFileNameField = componentFactory.createReadOnlyTextField(FilerenamerItem::existingFileNameProperty);
        existingFileNameField.textProperty().bind(item.existingFileNameProperty());
        existingFileNameField.setEditable(false);
        GridPane.setHgrow(existingFileNameField, Priority.ALWAYS);

        ColumnConstraints newFileNameColumnConstraints = new ColumnConstraints();
        newFileNameColumnConstraints.setPercentWidth(50);
        newFileNameColumnConstraints.setHgrow(Priority.ALWAYS);
        TextField newFileNameField = componentFactory.createEditableTextField(FilerenamerItem::newFileNameProperty);
        newFileNameField.textProperty().bindBidirectional(item.newFileNameProperty());
        GridPane.setHgrow(newFileNameField, Priority.ALWAYS);

        this.installDifferentMaker(item.newFileNameDifferentProperty(), List.of(newFileNameField));

        this.getColumnConstraints().addAll(existingFileNameColumnConstraints, newFileNameColumnConstraints);
        this.setHgap(2);
        this.add(existingFileNameField, 0, 0);
        this.add(newFileNameField, 1, 0);

    }

    private void installDifferentMaker(ObservableBooleanValue differentProperty, List<Node> targetNodes) {
        this.updateDifferentMaker(differentProperty.get(), targetNodes);
        differentProperty.addListener((_, _, newValue) -> this.updateDifferentMaker(newValue, targetNodes));
    }

    private void updateDifferentMaker(boolean differentValue, List<Node> targetNodes) {
        for (Node targetNode : targetNodes) {
            if (differentValue) {
                targetNode.setStyle("-fx-control-inner-background: #f4f4ff");
            } else {
                targetNode.setStyle("-fx-control-inner-background: #ffffff");
            }
        }
    }

}
