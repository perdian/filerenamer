package de.perdian.apps.filerenamer.fx.panels;

import de.perdian.apps.filerenamer.fx.FileRenamerListener;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ProgressPane extends GridPane implements FileRenamerListener {

    private ProgressBar progressBar = null;
    private Label progressLabel = null;

    public ProgressPane() {

        ProgressBar progressBar = new ProgressBar(0d);
        progressBar.setMaxWidth(Double.MAX_VALUE);
        this.setProgressBar(progressBar);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);

        Label progressLabel = new Label(" ");
        this.setProgressLabel(progressLabel);
        GridPane.setHgrow(progressLabel, Priority.ALWAYS);

        this.add(progressBar, 0, 0, 1, 1);
        this.add(progressLabel, 0, 1, 1, 1);

    }

    @Override
    public void progress(int currentItem, int totalItems, String message) {
        double progressValue = (1d / totalItems) * (currentItem + 1);
        Platform.runLater(() -> {
            this.getProgressBar().setProgress(progressValue);
            this.getProgressLabel().setText(message);
        });
    }

    private ProgressBar getProgressBar() {
        return this.progressBar;
    }
    private void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    private Label getProgressLabel() {
        return this.progressLabel;
    }
    private void setProgressLabel(Label progressLabel) {
        this.progressLabel = progressLabel;
    }

}
