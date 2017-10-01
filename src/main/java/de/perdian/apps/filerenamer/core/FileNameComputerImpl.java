package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.util.List;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;

public class FileNameComputerImpl implements FileNameComputer {

    private List<File> files = null;
    private SourceExpression sourceExpression = null;
    private TargetExpression targetExpression = null;

    @Override
    public String computeTargetFileName(String sourceFileName, int sourceFileIndex) {
        return "NOT_SUPPORTED_YET"; // TODO: Implement!
    }

    List<File> getFiles() {
        return this.files;
    }
    void setFiles(List<File> files) {
        this.files = files;
    }

    SourceExpression getSourceExpression() {
        return this.sourceExpression;
    }
    void setSourceExpression(SourceExpression sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    TargetExpression getTargetExpression() {
        return this.targetExpression;
    }
    void setTargetExpression(TargetExpression targetExpression) {
        this.targetExpression = targetExpression;
    }

}
