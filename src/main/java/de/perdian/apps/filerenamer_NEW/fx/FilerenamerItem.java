package de.perdian.apps.filerenamer_NEW.fx;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;

import java.io.File;

public class FilerenamerItem {

    private File nativeFile;
    private StringProperty existingFileName;
    private StringProperty newFileName;
    private ObservableBooleanValue newFileNameDifferent;

    public FilerenamerItem(File nativeFile) {
        this.nativeFile = nativeFile;
        this.existingFileName = new SimpleStringProperty(nativeFile.getName());
        this.newFileName = new SimpleStringProperty(nativeFile.getName());
        this.newFileNameDifferent = this.existingFileName.isEqualTo(this.newFileName).not();

this.newFileName.addListener((observableValue, oldValue, newValue) -> {
    System.err.println("NEW_NAME _ " + newValue + " << " + nativeFile.getName());
});
        this.newFileNameDifferent.addListener((observableValue, oldValue, newValue) -> {
  System.err.println("DIFF _ " + newValue + " << " + nativeFile.getName());
});
    }

    public File getNativeFile() {
        return this.nativeFile;
    }

    public String getExistingFileName() {
        return this.existingFileName.get();
    }
    public ReadOnlyStringProperty existingFileNameProperty() {
        return this.existingFileName;
    }

    public String getNewFileName() {
        return this.newFileName.get();
    }
    public StringProperty newFileNameProperty() {
        return this.newFileName;
    }
    public void setNewFileName(String newFileName) {
        this.newFileName.set(newFileName);
    }

    public boolean isNewFileNameDifferent() {
        return this.newFileNameDifferent.get();
    }
    public ObservableBooleanValue newFileNameDifferentProperty() {
        return this.newFileNameDifferent;
    }

}
