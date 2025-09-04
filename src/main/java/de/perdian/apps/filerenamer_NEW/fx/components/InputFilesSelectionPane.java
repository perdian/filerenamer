package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.support.components.ValidationComponentBuilder;
import de.perdian.apps.filerenamer_NEW.fx.support.properties.FxPropertyHelper;
import de.perdian.apps.filerenamer_NEW.fx.support.types.FileFilterExpressionConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;

import java.io.File;

public class InputFilesSelectionPane extends GridPane {

    public InputFilesSelectionPane(InputFilesSelectionModel model, ObservableBooleanValue globalBusy) {

        TextField directoryField = new TextField();
        directoryField.textProperty().bind(FxPropertyHelper.createStringBindingForFile(model.directoryProperty()));
        directoryField.setFocusTraversable(false);
        directoryField.setEditable(false);
        directoryField.disableProperty().bind(directoryField.textProperty().isEmpty().or(Bindings.or(globalBusy, model.busyProperty())));
        directoryField.setMinWidth(350);
        GridPane.setHgrow(directoryField, Priority.ALWAYS);

        Button directorySelectButton = new Button("Select", new FontIcon(RemixiconAL.FOLDER_OPEN_FILL));
        directorySelectButton.setOnAction(_ -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            if (model.getDirectory() != null && model.getDirectory().exists()) {
                directoryChooser.setInitialDirectory(model.getDirectory().getParentFile());
            }
            directoryChooser.setTitle("Select input directory");
            File selectedDirectory = directoryChooser.showDialog(this.getScene().getWindow());
            if (selectedDirectory != null && selectedDirectory.exists()) {
                model.setDirectory(selectedDirectory);
            }
        });
        directorySelectButton.disableProperty().bind(Bindings.or(globalBusy, model.busyProperty()));

        Label directoryLabel = new Label("Directory");
        directoryLabel.setLabelFor(directoryField);

        TextField inputFilesFilterField = new TextField();
        Bindings.bindBidirectional(inputFilesFilterField.textProperty(), model.filterExpressionProperty(), new FileFilterExpressionConverter());
        inputFilesFilterField.disableProperty().bind(Bindings.or(globalBusy, model.busyProperty()));
        GridPane.setHgrow(inputFilesFilterField, Priority.ALWAYS);

        Label inputFilesFilterLabel = new Label("Filter rules");
        inputFilesFilterLabel.setLabelFor(inputFilesFilterField);
        GridPane.setMargin(inputFilesFilterLabel, new Insets(10, 0, 0, 0));

        Node inputFilesFilterValidNode = new ValidationComponentBuilder<>(model.filterExpressionProperty())
            .withValidationPredicate(ffe -> ffe.isValid())
            .withErrorMessageFunction(ffe -> ffe.getParserException() == null ? null : ffe.getParserException().getMessage())
            .buildTextField();
        inputFilesFilterValidNode.disableProperty().bind(Bindings.or(globalBusy, model.busyProperty()));

        Button loadFilteredFilesButton = new Button("Load filtered files", new FontIcon(RemixiconAL.FILTER_2_FILL));
        loadFilteredFilesButton.setMaxWidth(Double.MAX_VALUE);
        loadFilteredFilesButton.disableProperty().bind(Bindings.or(Bindings.not(model.validProperty()), Bindings.or(globalBusy, model.busyProperty())));
        loadFilteredFilesButton.setOnAction(_ -> model.reloadFiles());
        GridPane.setMargin(loadFilteredFilesButton, new Insets(10, 0, 0, 0));
        GridPane.setHgrow(loadFilteredFilesButton, Priority.ALWAYS);

        ProgressBar loadFilteredFilesProgresBar = new ProgressBar(0);
        loadFilteredFilesProgresBar.disableProperty().bind(Bindings.or(globalBusy, Bindings.not(model.busyProperty())));
        loadFilteredFilesProgresBar.setPrefHeight(20);
        loadFilteredFilesProgresBar.setMaxWidth(Double.MAX_VALUE);
        loadFilteredFilesProgresBar.progressProperty().bind(Bindings.createDoubleBinding(() -> model.busyProperty().getValue() ? ProgressBar.INDETERMINATE_PROGRESS : 0, model.busyProperty()));
        GridPane.setMargin(loadFilteredFilesProgresBar, new Insets(5, 0, 0, 0));
        GridPane.setHgrow(loadFilteredFilesProgresBar, Priority.ALWAYS);

        this.add(directoryLabel, 0, 0, 2, 1);
        this.add(directoryField, 0, 1, 1, 1);
        this.add(directorySelectButton, 1, 1, 1, 1);
        this.add(inputFilesFilterLabel, 0, 2, 2, 1);
        this.add(inputFilesFilterField, 0, 3, 1, 1);
        this.add(inputFilesFilterValidNode, 1, 3, 1, 1);
        this.add(loadFilteredFilesButton, 0, 4, 2, 1);
        this.add(loadFilteredFilesProgresBar, 0, 5, 2, 1);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setHgap(5);
        this.setVgap(2);

    }

}
