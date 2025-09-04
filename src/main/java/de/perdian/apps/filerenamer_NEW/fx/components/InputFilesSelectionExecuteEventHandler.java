package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerItem;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;

public class InputFilesSelectionExecuteEventHandler implements EventHandler<ActionEvent> {

    private final ObservableObjectValue<File> inputDirectory;
    private final ObservableStringValue inputFilesFilter;
    private final ObservableList<FilerenamerItem> outputItems;

    public InputFilesSelectionExecuteEventHandler(ObservableObjectValue<File> inputDirectory, ObservableStringValue inputFilesFilter, ObservableList<FilerenamerItem> outputItems) {
        this.inputDirectory = inputDirectory;
        this.inputFilesFilter = inputFilesFilter;
        this.outputItems = outputItems;
    }

    @Override
    public void handle(ActionEvent event) {

    }

}
