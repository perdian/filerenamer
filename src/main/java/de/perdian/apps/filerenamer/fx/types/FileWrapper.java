package de.perdian.apps.filerenamer.fx.types;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileWrapper {

    private StringProperty targetFileName = null;
    private File file = null;

    public FileWrapper(File file) {
        this.setFile(file);
        this.setTargetFileName(new SimpleStringProperty());
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else {
            return (that instanceof FileWrapper) && this.getFile() != null && this.getFile().equals(((FileWrapper)that).getFile());
        }
    }

    @Override
    public int hashCode() {
        return this.getFile().hashCode();
    }

    public StringProperty getTargetFileName() {
        return this.targetFileName;
    }
    private void setTargetFileName(StringProperty targetFileName) {
        this.targetFileName = targetFileName;
    }

    public void updateFile(File newFile) {
        this.setFile(newFile);
    }
    public File getFile() {
        return this.file;
    }
    private void setFile(File file) {
        this.file = file;
    }

}
