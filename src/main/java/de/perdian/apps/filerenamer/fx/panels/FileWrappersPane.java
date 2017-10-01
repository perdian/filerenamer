package de.perdian.apps.filerenamer.fx.panels;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdian.apps.filerenamer.fx.actions.SortFilesEventHandler;
import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class FileWrappersPane extends BorderPane {

    private static final Logger log = LoggerFactory.getLogger(FileWrappersPane.class);

    public FileWrappersPane(ObservableList<FileWrapper> fileWrappers, BooleanProperty filesAvailableProperty) {

        TableColumn<FileWrapper, String> currentFileNameColumn = new TableColumn<>("Current file name");
        currentFileNameColumn.setSortable(false);
        currentFileNameColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFile().getName()));

        TableColumn<FileWrapper, String> newFileNameColumn = new TableColumn<>("New file name");
        newFileNameColumn.setSortable(false);
        newFileNameColumn.setCellValueFactory(p -> p.getValue().getTargetFileName());

        TableView<FileWrapper> fileWrappersTable = new TableView<>(fileWrappers);
        fileWrappersTable.getColumns().addAll(Arrays.asList(currentFileNameColumn, newFileNameColumn));
        fileWrappersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button sortAscendingButton = new Button(null, new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/16/sort-descending.png"))));
        sortAscendingButton.setOnAction(new SortFilesEventHandler(fileWrappers, 1));
        sortAscendingButton.setTooltip(new Tooltip("Sort files ascending (A-Z)"));
        sortAscendingButton.setDisable(fileWrappers.isEmpty());
        sortAscendingButton.disableProperty().bind(filesAvailableProperty.not());

        Button sortDescendingButton = new Button(null, new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/16/sort-ascending.png"))));
        sortDescendingButton.setOnAction(new SortFilesEventHandler(fileWrappers, -1));
        sortDescendingButton.setTooltip(new Tooltip("Sort files descending (Z-A)"));
        sortDescendingButton.setDisable(fileWrappers.isEmpty());
        sortDescendingButton.disableProperty().bind(filesAvailableProperty.not());

        Button clearButton = new Button(null, new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/16/clear.png"))));
        clearButton.setOnAction(event -> fileWrappers.clear());
        clearButton.setTooltip(new Tooltip("Clear list"));
        clearButton.setDisable(fileWrappers.isEmpty());
        clearButton.disableProperty().bind(filesAvailableProperty.not());

        VBox buttonBox = new VBox(5);
        buttonBox.getChildren().add(sortAscendingButton);
        buttonBox.getChildren().add(sortDescendingButton);
        buttonBox.getChildren().add(clearButton);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));

        this.setOnDragOver(event -> {
            if (event.getGestureSource() != event.getSource() && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        this.setOnDragDropped(event -> {
            if (event.getDragboard().hasFiles()) {
                List<File> files = event.getDragboard().getFiles();
                if (files != null && !files.isEmpty()) {
                    for (File file : files) {
                        FileWrapper fileWrapper = new FileWrapper(file);
                        if (!fileWrappers.contains(fileWrapper)) {
                            log.info("Adding new file to list: {}", file.getAbsolutePath());
                            fileWrappers.add(fileWrapper);
                        }
                    }
                }
                event.setDropCompleted(true);
            } else {
                event.setDropCompleted(false);
            }
            event.consume();
        });
        this.setCenter(fileWrappersTable);
        this.setRight(buttonBox);

    }

}
