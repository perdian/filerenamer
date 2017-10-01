package de.perdian.apps.filerenamer.fx.actions;

import java.util.regex.Pattern;

import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.collections.ObservableList;

public class RecomputeFileNamesEvent {

    private ObservableList<FileWrapper> fileWrappers = null;
    private Pattern sourcePattern = null;
    private String targetExpression = null;

    public ObservableList<FileWrapper> getFileWrappers() {
        return this.fileWrappers;
    }
    public void setFileWrappers(ObservableList<FileWrapper> fileWrappers) {
        this.fileWrappers = fileWrappers;
    }

    public Pattern getSourcePattern() {
        return this.sourcePattern;
    }
    public void setSourcePattern(Pattern sourcePattern) {
        this.sourcePattern = sourcePattern;
    }

    public String getTargetExpression() {
        return this.targetExpression;
    }
    public void setTargetExpression(String targetExpression) {
        this.targetExpression = targetExpression;
    }

}
