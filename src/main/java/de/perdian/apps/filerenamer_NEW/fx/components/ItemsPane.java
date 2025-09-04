package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerItem;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;

public class ItemsPane extends BorderPane {

    public ItemsPane(ItemsModel model, ObservableBooleanValue globalBusy) {
        this.updateItemsPane(model, globalBusy);
        model.getAllItems().addListener((ListChangeListener<? super FilerenamerItem>) change -> this.updateItemsPane(model, globalBusy));
    }

    private void updateItemsPane(ItemsModel model, ObservableBooleanValue globalBusy) {
        Platform.runLater(() -> this.setCenter(this.createLoadingPane(model)));
        Thread.ofVirtual().start(() -> {
            Node itemsPane = this.createItemsPane(model, globalBusy);
            Platform.runLater(() -> this.setCenter(itemsPane));
        });
    }

    private Pane createLoadingPane(ItemsModel model) {
        Label loadingLabel = new Label("Loading " + model.getAllItems().size() + " items...");
        loadingLabel.setAlignment(Pos.CENTER);
        GridPane.setHalignment(loadingLabel, HPos.CENTER);
        ProgressBar progressBar = new ProgressBar(ProgressBar.INDETERMINATE_PROGRESS);
        progressBar.setPrefWidth(250);
        progressBar.setPrefHeight(25);
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(5);
        gridPane.add(loadingLabel, 0, 0);
        gridPane.add(progressBar, 0, 1);
        BorderPane loadingPane = new BorderPane();
        loadingPane.setCenter(gridPane);
        return loadingPane;
    }

    private Node createItemsPane(ItemsModel model, ObservableBooleanValue globalBusy) {
        if (model.getAllItems().isEmpty()) {
            return this.createEmptyItemsPane();
        } else {
            return this.createListItemsPane(model, globalBusy);
        }
    }

    private Node createEmptyItemsPane() {
        return new BorderPane();
    }

    private Node createListItemsPane(ItemsModel model, ObservableBooleanValue globalBusy) {

        VBox itemsBox = new VBox();
        itemsBox.setPadding(new Insets(10, 10, 10, 10));
        ObjectProperty<FilerenamerItem> createItemProperty = new SimpleObjectProperty<>();
        ItemPaneComponentFactory createItemPaneComponentFactory = new ItemPaneComponentFactory(createItemProperty, model, globalBusy);
        List<? extends FilerenamerItem> allItems = model.getAllItems();
        for (int i=0; i < allItems.size(); i++) {

            FilerenamerItem createItem = allItems.get(i);
            createItemProperty.set(createItem);

            Pane createItemPane = new ItemPane(createItem, createItemPaneComponentFactory);
            createItemPane.setPadding(new Insets(3, 3, 3, 3));
            createItemPane.setStyle("-fx-border-color: transparent; fx-border-width: 3;");

            model.activeItemProperty().addListener((_, _, newActiveItem) -> {
                if (Objects.equals(createItem, newActiveItem)) {
                    createItemPane.setStyle("-fx-border-color: #cccccc; -fx-background-color: #dddddd; fx-border-width: 3;");
                } else {
                    createItemPane.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; fx-border-width: 3;");
                }
            });
            itemsBox.getChildren().add(createItemPane);

        }

        ScrollPane itemsScrollPane = new ScrollPane(itemsBox);
        itemsScrollPane.setFitToWidth(true);
        itemsScrollPane.setStyle("-fx-background-color: transparent;");
        itemsScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        itemsScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        BorderPane itemsPane = new BorderPane(itemsScrollPane);
        return itemsPane;

    }


}
