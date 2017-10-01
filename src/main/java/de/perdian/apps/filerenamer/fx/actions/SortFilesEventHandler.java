package de.perdian.apps.filerenamer.fx.actions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SortFilesEventHandler implements EventHandler<ActionEvent> {

    private List<FileWrapper> fileWrappers = null;
    private int direction = 0;

    public SortFilesEventHandler(List<FileWrapper> fileWrappers, int direction) {
        this.setFileWrappers(fileWrappers);
        this.setDirection(direction);
    }

    @Override
    public void handle(ActionEvent event) {
        Collections.sort(this.getFileWrappers(), (left, right) -> {
            String leftName = Optional.ofNullable(left.getFile().getName()).orElse("");
            String rightName = Optional.ofNullable(right.getFile().getName()).orElse("");
            return leftName.compareToIgnoreCase(rightName) * this.getDirection();
        });
    }

    private List<FileWrapper> getFileWrappers() {
        return this.fileWrappers;
    }
    private void setFileWrappers(List<FileWrapper> fileWrappers) {
        this.fileWrappers = fileWrappers;
    }

    private int getDirection() {
        return this.direction;
    }
    private void setDirection(int direction) {
        this.direction = direction;
    }

}
