package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerItem;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class ItemsModel {

    private static final Logger log = LoggerFactory.getLogger(ItemsModel.class);

    private final BooleanProperty itemsLoading;
    private final ObservableList<FilerenamerItem> allItems;
    private final ObjectProperty<FilerenamerItem> activeItem;

    public ItemsModel() {
        this.itemsLoading = new SimpleBooleanProperty(false);
        this.activeItem = new SimpleObjectProperty<>();
        this.allItems = FXCollections.observableArrayList();
        this.allItems.addListener((ListChangeListener<? super FilerenamerItem>) change -> {
            if (this.activeItem.getValue() != null && !change.getList().contains(this.activeItem.getValue())) {
                this.activeItem.setValue(null);
            }
        });
    }

    public void automaticallyUpdateFromFiles(ObservableList<File> files) {
        this.updateFromFiles(files);
        files.addListener((ListChangeListener<? super File>) change -> this.updateFromFiles(change.getList()));
    }

    public synchronized void updateFromFiles(List<? extends File> files) {
        log.info("Updating model with items for {} files", files.size());
        Platform.runLater(() -> this.setItemsLoading(true));
        try {
            List<FilerenamerItem> allItems = files.stream()
                .map(FilerenamerItem::new)
                .toList();
            Platform.runLater(() -> {
                this.setActiveItem(null);
                this.getAllItems().setAll(allItems);
            });
        } finally {
            Platform.runLater(() -> this.setItemsLoading(false));
        }
    }

    public boolean isItemsLoading() {
        return this.itemsLoading.get();
    }
    public ObservableBooleanValue itemsLoadingProperty() {
        return this.itemsLoading;
    }
    private void setItemsLoading(boolean itemsLoading) {
        this.itemsLoading.set(itemsLoading);
    }

    public ObservableList<FilerenamerItem> getAllItems() {
        return this.allItems;
    }

    public FilerenamerItem getActiveItem() {
        return this.activeItem.get();
    }
    public ObjectProperty<FilerenamerItem> activeItemProperty() {
        return this.activeItem;
    }
    public void setActiveItem(FilerenamerItem activeItem) {
        this.activeItem.set(activeItem);
    }

}
