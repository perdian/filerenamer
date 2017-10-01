package de.perdian.apps.filerenamer.fx;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;
import de.perdian.apps.filerenamer.fx.actions.RecomputeFileNamesChangeListener;
import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class FileRenamerPane extends BorderPane {

    FileRenamerPane() {

        ObservableList<FileWrapper> fileWrappers = FXCollections.observableArrayList();
fileWrappers.addAll(Arrays.stream(new File(System.getProperty("user.home"), "Downloads").listFiles()).map(FileWrapper::new).collect(Collectors.toList()));

        Property<SourceExpression> sourceExpressionProperty = new SimpleObjectProperty<>();
        Property<TargetExpression> targetExpressionProperty = new SimpleObjectProperty<>();

        ChangeListener<Object> recomputeFileNamesChangeListener = new RecomputeFileNamesChangeListener(fileWrappers, sourceExpressionProperty, targetExpressionProperty);
        sourceExpressionProperty.addListener(recomputeFileNamesChangeListener);
        targetExpressionProperty.addListener(recomputeFileNamesChangeListener);
        fileWrappers.addListener((ListChangeListener.Change<? extends Object> change) -> recomputeFileNamesChangeListener.changed(null, null, null));

        TextField sourceExpressionField = new TextField();
        sourceExpressionField.setFont(Font.font("Monospaced", 14d));
        sourceExpressionField.textProperty().addListener((o, oldValue, newValue) -> sourceExpressionProperty.setValue(new SourceExpression(newValue)));
        GridPane.setHgrow(sourceExpressionField, Priority.ALWAYS);
        Label sourceExpressionLabel = new Label("Source expression (Regex)");
        Label sourceExpressionInfoLabel = new Label("Source expression empty");
        sourceExpressionInfoLabel.setTextFill(Color.web("#cccccc"));
        sourceExpressionProperty.addListener((o, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue.getValue())) {
                sourceExpressionInfoLabel.setText("Expression empty");
                sourceExpressionInfoLabel.setTextFill(Color.web("#cccccc"));
            } else if (newValue.isValid()) {
                sourceExpressionInfoLabel.setText("Expression valid");
                sourceExpressionInfoLabel.setTextFill(Color.web("#008800"));
            } else {
                sourceExpressionInfoLabel.setText("Expression invalid!");
                sourceExpressionInfoLabel.setTextFill(Color.web("#dd0000"));
            }
        });

        TextField targetExpressionField = new TextField();
        targetExpressionField.setFont(Font.font("Monospaced", 14d));
        targetExpressionField.textProperty().addListener((o, oldValue, newValue) -> targetExpressionProperty.setValue(new TargetExpression(newValue)));
        GridPane.setHgrow(targetExpressionField, Priority.ALWAYS);
        Label targetExpressionLabel = new Label("Target expression (EL)");
        GridPane.setMargin(targetExpressionLabel, new Insets(10, 0, 0, 0));
        Label targetExpressionInfoLabel = new Label("Target expression empty");
        targetExpressionInfoLabel.setTextFill(Color.web("#cccccc"));
        targetExpressionProperty.addListener((o, oldValue, newValue) -> {
            if (StringUtils.isEmpty(newValue.getValue())) {
                targetExpressionInfoLabel.setText("Expression empty");
                targetExpressionInfoLabel.setTextFill(Color.web("#cccccc"));
            } else if (newValue.isValid()) {
                targetExpressionInfoLabel.setText("Expression valid");
                targetExpressionInfoLabel.setTextFill(Color.web("#008800"));
            } else {
                targetExpressionInfoLabel.setText("Expression invalid!");
                targetExpressionInfoLabel.setTextFill(Color.web("#dd0000"));
            }
        });

        GridPane topPane = new GridPane();
        topPane.setHgap(2);
        topPane.setVgap(2);
        topPane.add(sourceExpressionLabel, 0, 0, 1, 1);
        topPane.add(sourceExpressionField, 0, 1, 1, 1);
        topPane.add(sourceExpressionInfoLabel, 0, 2, 2, 1);
        topPane.add(targetExpressionLabel, 0, 3, 1, 1);
        topPane.add(targetExpressionField, 0, 4, 1, 1);
        topPane.add(targetExpressionInfoLabel, 0, 5, 2, 1);
        TitledPane topTitledPane = new TitledPane("Expressions", topPane);
        topTitledPane.setExpanded(true);
        topTitledPane.setCollapsible(false);

        TableColumn<FileWrapper, String> currentFileNameColumn = new TableColumn<>("Current file name");
        currentFileNameColumn.setSortable(false);
        currentFileNameColumn.setCellValueFactory(p -> p.getValue().getSourceFileName());
        TableColumn<FileWrapper, String> newFileNameColumn = new TableColumn<>("New file name");
        newFileNameColumn.setSortable(false);
        newFileNameColumn.setCellValueFactory(p -> p.getValue().getTargetFileName());
        TableView<FileWrapper> fileWrappersTable = new TableView<>(fileWrappers);
        fileWrappersTable.getColumns().addAll(Arrays.asList(currentFileNameColumn, newFileNameColumn));
        fileWrappersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TitledPane fileWrappersTitledPane = new TitledPane("Files", fileWrappersTable);
        fileWrappersTitledPane.setMaxHeight(Double.MAX_VALUE);
        fileWrappersTitledPane.setCollapsible(false);
        BorderPane.setMargin(fileWrappersTitledPane, new Insets(10, 0, 10, 0));

        Button executeButton = new Button("Execute rename");
        executeButton.setDisable(true);
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(executeButton);

        this.setPadding(new Insets(5, 5, 10, 5));
        this.setTop(topTitledPane);
        this.setBottom(buttonBox);
        this.setCenter(fileWrappersTitledPane);

    }

}
