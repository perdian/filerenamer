package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.util.List;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;

public class FileNameComputerFactory {

    private FileNameComputerHelper helper = new FileNameComputerHelper();

    public FileNameComputer createRenamer(List<File> files, SourceExpression sourceExpression, TargetExpression targetExpression) {
        if (sourceExpression == null || !sourceExpression.isValid() || targetExpression == null || !targetExpression.isValid()) {
            return sourceFileName -> null;
        } else {
            FileNameComputerImpl fileNameComputerImpl = new FileNameComputerImpl(this.getHelper());
            fileNameComputerImpl.setFiles(files);
            fileNameComputerImpl.setSourceExpression(sourceExpression);
            fileNameComputerImpl.setTargetExpression(targetExpression);
            return fileNameComputerImpl;
        }
    }

    FileNameComputerHelper getHelper() {
        return this.helper;
    }
    void setHelper(FileNameComputerHelper helper) {
        this.helper = helper;
    }

}
