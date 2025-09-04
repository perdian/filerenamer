package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.support.properties.FxPropertyHelper;
import de.perdian.apps.filerenamer_NEW.fx.support.types.FileFilterExpression;
import de.perdian.apps.filerenamer_NEW.fx.support.types.FileFilterExpressionConverter;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

public class InputFilesSelectionModel {

    private static final Logger log = LoggerFactory.getLogger(InputFilesSelectionModel.class);

    private final BooleanProperty busy;
    private final ObjectProperty<File> directory;
    private final ObservableBooleanValue directoryValid;
    private final ObjectProperty<FileFilterExpression> filterExpression;
    private final ObservableBooleanValue filterExpressionValid;
    private final ObservableBooleanValue valid;
    private final ObservableList<File> loadedFiles;

    public InputFilesSelectionModel() {
        this.busy = new SimpleBooleanProperty(false);
        this.directory = FxPropertyHelper.createPersistentFileProperty(InputFilesSelectionModel.class.getSimpleName() + ".inputDirectory", null);
        this.directoryValid = Bindings.createBooleanBinding(() -> this.directory.getValue() != null && this.directory.getValue().exists(), this.directory);
        this.filterExpression = FxPropertyHelper.createPersistentObjectProperty(InputFilesSelectionModel.class.getSimpleName() + ".inputFilesFilterExpression", FileFilterExpression::new, new FileFilterExpressionConverter());
        this.filterExpressionValid = Bindings.createBooleanBinding(() -> this.filterExpression.getValue() != null && this.filterExpression.getValue().isValid(), this.filterExpression);
        this.valid = Bindings.and(this.directoryValid, this.filterExpressionValid);
        this.loadedFiles = FXCollections.observableArrayList();
        this.reloadFiles();
    }

    public boolean reloadFiles() {
        if (!this.isValid()) {
            return false;
        } else {
            Platform.runLater(() -> this.setBusy(true));
            Thread.ofVirtual().start(() -> {
                try {
                    this.reloadFilesInternal();
                } finally {
                    Platform.runLater(() -> this.setBusy(false));
                }
            });
            return true;
        }
    }

    private void reloadFilesInternal() {
        File directory = this.getDirectory();
        log.info("Loading files from directory: {}", directory.getAbsolutePath());
        FileFilter directoryFileFilter = this.getFilterExpression() == null ? _ -> true : this.getFilterExpression().asFileFilter();
        FileFilter enhancedFileFilter = file ->
            file.isFile()
            && !file.isHidden()
            && !file.getName().startsWith(".")
            && directoryFileFilter.accept(file);
        File[] filesInDirectory = directory == null ? null : directory.listFiles(enhancedFileFilter);
        List<File> filesInDirectoryList = Arrays.stream(filesInDirectory)
            .sorted((f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()))
            .toList();
        this.getLoadedFiles().setAll(filesInDirectoryList);
    }

    public boolean isBusy() {
        return this.busy.get();
    }
    public ObservableBooleanValue busyProperty() {
        return this.busy;
    }
    private void setBusy(boolean busy) {
        this.busy.set(busy);
    }

    public File getDirectory() {
        return this.directory.get();
    }
    public ObjectProperty<File> directoryProperty() {
        return this.directory;
    }
    public void setDirectory(File directory) {
        this.directory.set(directory);
    }

    public boolean isDirectoryValid() {
        return this.directoryValid.get();
    }
    public ObservableBooleanValue directoryValidProperty() {
        return this.directoryValid;
    }

    public FileFilterExpression getFilterExpression() {
        return this.filterExpression.get();
    }
    public ObjectProperty<FileFilterExpression> filterExpressionProperty() {
        return this.filterExpression;
    }
    public void setFilterExpression(FileFilterExpression filterExpression) {
        this.filterExpression.set(filterExpression);
    }

    public boolean isFilterExpressionValid() {
        return this.filterExpressionValid.get();
    }
    public ObservableBooleanValue filterExpressionValidProperty() {
        return this.filterExpressionValid;
    }

    public boolean isValid() {
        return this.valid.get();
    }
    public ObservableBooleanValue validProperty() {
        return this.valid;
    }

    public ObservableList<File> getLoadedFiles() {
        return this.loadedFiles;
    }

}
