package de.perdian.apps.filerenamer.fx.actions;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.perdian.apps.filerenamer.core.FileNameComputer;
import de.perdian.apps.filerenamer.core.FileNameComputerFactory;
import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;
import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class RecomputeFileNamesChangeListener implements ChangeListener<Object> {

    private List<FileWrapper> files = null;
    private Property<SourceExpression> sourceExpression = null;
    private Property<TargetExpression> targetExpression = null;

    public RecomputeFileNamesChangeListener(List<FileWrapper> files, Property<SourceExpression> sourceExpression, Property<TargetExpression> targetExpression) {
        this.setFiles(files);
        this.setSourceExpression(sourceExpression);
        this.setTargetExpression(targetExpression);
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        List<File> files = this.getFiles().stream().map(FileWrapper::getFile).collect(Collectors.toList());
        FileNameComputer fileNameComputer = FileNameComputerFactory.createRenamer(files, this.getSourceExpression().getValue(), this.getTargetExpression().getValue());
        for (int i=0; i < this.getFiles().size(); i++) {
            File sourceFileName = this.getFiles().get(i).getFile();
            String currentTargetFileName = this.getFiles().get(i).getTargetFileName().getValue();
            try {
                String newTargetFileName = fileNameComputer.computeTargetFileName(sourceFileName);
                if (!StringUtils.equals(currentTargetFileName, newTargetFileName)) {
                    this.getFiles().get(i).getTargetFileName().setValue(newTargetFileName);
                }
                this.getFiles().get(i).getException().setValue(null);
            } catch (Exception e) {
                this.getFiles().get(i).getException().setValue(e);
            }
        }
    }

    public List<FileWrapper> getFiles() {
        return this.files;
    }
    public void setFiles(List<FileWrapper> files) {
        this.files = files;
    }

    public Property<SourceExpression> getSourceExpression() {
        return this.sourceExpression;
    }
    public void setSourceExpression(Property<SourceExpression> sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    public Property<TargetExpression> getTargetExpression() {
        return this.targetExpression;
    }
    public void setTargetExpression(Property<TargetExpression> targetExpression) {
        this.targetExpression = targetExpression;
    }

}
