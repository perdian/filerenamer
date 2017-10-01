package de.perdian.apps.filerenamer.fx.types;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileWrapper {

    private StringProperty sourceFileName = null;
    private StringProperty targetFileName = null;
    private File file = null;

    public FileWrapper(File file) {
        this.setFile(file);
        this.setSourceFileName(new SimpleStringProperty(file.getName()));
        this.setTargetFileName(new SimpleStringProperty());
    }

    public StringProperty getSourceFileName() {
        return this.sourceFileName;
    }
    private void setSourceFileName(StringProperty sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public StringProperty getTargetFileName() {
        return this.targetFileName;
    }
    private void setTargetFileName(StringProperty targetFileName) {
        this.targetFileName = targetFileName;
    }

    public File getFile() {
        return this.file;
    }
    private void setFile(File file) {
        this.file = file;
    }

}
