package de.perdian.apps.filerenamer.fx;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FileWrapper {

    private StringProperty sourceFileName = null;
    private StringProperty targetFileName = null;
    private File file = null;
    private ObjectProperty<Throwable> exception = null;

    public FileWrapper(File file) {
        this.setFile(file);
        this.setSourceFileName(new SimpleStringProperty(file.getName()));
        this.setTargetFileName(new SimpleStringProperty());
        this.setException(new SimpleObjectProperty<>());
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

    public void updateFile(File file) {
        this.setFile(file);
        this.getSourceFileName().setValue(file.getName());
    }
    public File getFile() {
        return this.file;
    }
    private void setFile(File file) {
        this.file = file;
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

    public ObjectProperty<Throwable> getException() {
        return this.exception;
    }
    private void setException(ObjectProperty<Throwable> exception) {
        this.exception = exception;
    }

}
