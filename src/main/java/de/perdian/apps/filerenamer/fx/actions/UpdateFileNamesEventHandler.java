package de.perdian.apps.filerenamer.fx.actions;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UpdateFileNamesEventHandler implements EventHandler<ActionEvent> {

    private static final Logger log = LoggerFactory.getLogger(UpdateFileNamesEventHandler.class);

    private List<FileWrapper> fileWrappers = null;

    public UpdateFileNamesEventHandler(List<FileWrapper> fileWrappers) {
        this.setFileWrappers(fileWrappers);
    }

    @Override
    public void handle(ActionEvent event) {
        for (FileWrapper fileWrapper : this.getFileWrappers()) {
            String targetFileName = fileWrapper.getTargetFileName().getValue().trim();
            if (!StringUtils.isEmpty(targetFileName)) {
                File sourceFile = fileWrapper.getFile();
                File targetFile = new File(sourceFile.getParentFile(), targetFileName);
                if (!sourceFile.equals(targetFile)) {
                    log.info("Updating file '{}' to new file name '{}'", sourceFile.getName(), targetFileName);
                    sourceFile.renameTo(targetFile);
                    fileWrapper.updateFile(targetFile);
                }
            }
        }
    }

    private List<FileWrapper> getFileWrappers() {
        return this.fileWrappers;
    }
    private void setFileWrappers(List<FileWrapper> fileWrappers) {
        this.fileWrappers = fileWrappers;
    }

}
