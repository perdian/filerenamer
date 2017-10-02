package de.perdian.apps.filerenamer.fx;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.perdian.apps.filerenamer.core.FileNameComputer;
import de.perdian.apps.filerenamer.core.FileNameComputerFactory;
import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

class FileRenamerModel {

    private ObservableList<FileWrapper> files = null;
    private ObjectProperty<SourceExpression> sourceExpression = null;
    private ObjectProperty<TargetExpression> targetExpression = null;
    private BooleanProperty filesAvailable = null;

    FileRenamerModel() {

        ObservableList<FileWrapper> files = FXCollections.observableArrayList();
        ObjectProperty<SourceExpression> sourceExpressionProperty = new SimpleObjectProperty<>();
        ObjectProperty<TargetExpression> targetExpressionProperty = new SimpleObjectProperty<>();
        BooleanProperty filesAvailableProperty = new SimpleBooleanProperty(!files.isEmpty());

        sourceExpressionProperty.addListener((o, oldValue, newValue) -> this.recomputeTargetFileNames());
        targetExpressionProperty.addListener((o, oldValue, newValue) -> this.recomputeTargetFileNames());
        files.addListener((ListChangeListener.Change<? extends Object> change) -> filesAvailableProperty.setValue(!files.isEmpty()));
        files.addListener((ListChangeListener.Change<? extends Object> change) -> this.recomputeTargetFileNames());

        this.setFiles(files);
        this.setSourceExpression(sourceExpressionProperty);
        this.setTargetExpression(targetExpressionProperty);
        this.setFilesAvailable(filesAvailableProperty);

    }

    void recomputeTargetFileNames() {
        List<File> allFiles = this.getFiles().stream().map(FileWrapper::getFile).collect(Collectors.toList());
        FileNameComputer fileNameComputer = FileNameComputerFactory.createRenamer(allFiles, this.getSourceExpression().getValue(), this.getTargetExpression().getValue());
        for (int i=0; i < this.getFiles().size(); i++) {
            File sourceFile = this.getFiles().get(i).getFile();
            String currentTargetFileName = this.getFiles().get(i).getTargetFileName().getValue();
            try {
                String newTargetFileName = fileNameComputer.computeTargetFileName(sourceFile);
                if (!StringUtils.equals(currentTargetFileName, newTargetFileName)) {
                    this.getFiles().get(i).getTargetFileName().setValue(newTargetFileName);
                }
                this.getFiles().get(i).getException().setValue(null);
            } catch (Exception e) {
                this.getFiles().get(i).getException().setValue(e);
            }
        }
    }

    public ObservableList<FileWrapper> getFiles() {
        return this.files;
    }
    private void setFiles(ObservableList<FileWrapper> files) {
        this.files = files;
    }

    public ObjectProperty<SourceExpression> getSourceExpression() {
        return this.sourceExpression;
    }
    private void setSourceExpression(ObjectProperty<SourceExpression> sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    public ObjectProperty<TargetExpression> getTargetExpression() {
        return this.targetExpression;
    }
    private void setTargetExpression(ObjectProperty<TargetExpression> targetExpression) {
        this.targetExpression = targetExpression;
    }

    public BooleanProperty getFilesAvailable() {
        return this.filesAvailable;
    }
    private void setFilesAvailable(BooleanProperty filesAvailable) {
        this.filesAvailable = filesAvailable;
    }

}
