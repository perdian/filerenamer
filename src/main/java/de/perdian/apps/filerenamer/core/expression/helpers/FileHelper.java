package de.perdian.apps.filerenamer.core.expression.helpers;

import java.io.File;
import java.time.Instant;

public class FileHelper {

    private File file = null;
    private int index = 0;

    public FileHelper(File file, int index) {
        this.setFile(file);
        this.setIndex(index);
    }

    public Instant lastModified() {
        return this.getFile().lastModified() <= 0 ? null : Instant.ofEpochMilli(this.getFile().lastModified());
    }

    public String name() {
        return this.getFile().getName();
    }

    public int index() {
        return this.getIndex() + 1;
    }

    private File getFile() {
        return this.file;
    }
    private void setFile(File file) {
        this.file = file;
    }

    private int getIndex() {
        return this.index;
    }
    private void setIndex(int index) {
        this.index = index;
    }

}
