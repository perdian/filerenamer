package de.perdian.apps.filerenamer.core.expression;

import java.io.File;
import java.util.List;

import de.perdian.apps.filerenamer.core.expression.helpers.FileHelper;
import de.perdian.apps.filerenamer.core.expression.helpers.FormatHelper;
import de.perdian.apps.filerenamer.core.expression.helpers.RegexHelper;

public class ExpressionRoot {

    private RegexHelper regex = null;
    private FileHelper file = null;
    private FormatHelper format = null;
    private List<File> files = null;

    public RegexHelper getRegex() {
        return this.regex;
    }
    public void setRegex(RegexHelper regex) {
        this.regex = regex;
    }

    public FileHelper getFile() {
        return this.file;
    }
    public void setFile(FileHelper file) {
        this.file = file;
    }

    public FormatHelper getFormat() {
        return this.format;
    }
    public void setFormat(FormatHelper format) {
        this.format = format;
    }

    public List<File> getFiles() {
        return this.files;
    }
    public void setFiles(List<File> files) {
        this.files = files;
    }

}
