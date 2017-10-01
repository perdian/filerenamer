package de.perdian.apps.filerenamer.fx;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;
import de.perdian.apps.filerenamer.fx.actions.RecomputeFileNamesChangeListener;
import de.perdian.apps.filerenamer.fx.actions.UpdateFileNamesEventHandler;
import de.perdian.apps.filerenamer.fx.panels.ExpressionsPane;
import de.perdian.apps.filerenamer.fx.panels.FileWrappersPane;
import de.perdian.apps.filerenamer.fx.panels.HelpPane;
import de.perdian.apps.filerenamer.fx.types.FileWrapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class FileRenamerPane extends BorderPane {

    FileRenamerPane() {

        ObservableList<FileWrapper> fileWrappers = FXCollections.observableArrayList();
        Property<SourceExpression> sourceExpressionProperty = new SimpleObjectProperty<>();
        Property<TargetExpression> targetExpressionProperty = new SimpleObjectProperty<>();
        BooleanProperty filesAvailableProperty = new SimpleBooleanProperty(!fileWrappers.isEmpty());
        fileWrappers.addListener((ListChangeListener.Change<? extends Object> change) -> filesAvailableProperty.setValue(!fileWrappers.isEmpty()));

        ChangeListener<Object> recomputeFileNamesChangeListener = new RecomputeFileNamesChangeListener(fileWrappers, sourceExpressionProperty, targetExpressionProperty);
        sourceExpressionProperty.addListener(recomputeFileNamesChangeListener);
        targetExpressionProperty.addListener(recomputeFileNamesChangeListener);
        fileWrappers.addListener((ListChangeListener.Change<? extends Object> change) -> recomputeFileNamesChangeListener.changed(null, null, null));

        ExpressionsPane expressionsPane = new ExpressionsPane(sourceExpressionProperty, targetExpressionProperty);
        expressionsPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane expressionsTitledPane = new TitledPane("Expressions", expressionsPane);
        expressionsTitledPane.setExpanded(true);
        expressionsTitledPane.setCollapsible(false);

        FileWrappersPane fileWrappersPane = new FileWrappersPane(fileWrappers, filesAvailableProperty);
        TitledPane fileWrappersTitledPane = new TitledPane("Files", fileWrappersPane);
        fileWrappersTitledPane.setMaxHeight(Double.MAX_VALUE);
        fileWrappersTitledPane.setCollapsible(false);
        BorderPane.setMargin(fileWrappersTitledPane, new Insets(10, 0, 10, 0));

        HelpPane helpPane = new HelpPane();
        helpPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane helpTitledPane = new TitledPane("Help", helpPane);
        helpTitledPane.setMaxHeight(Double.MAX_VALUE);
        helpTitledPane.setCollapsible(false);
        BorderPane.setMargin(helpTitledPane, new Insets(10, 0, 10, 10));

        Button executeButton = new Button("Execute rename");
        executeButton.setOnAction(new UpdateFileNamesEventHandler(fileWrappers));
        executeButton.setDisable(true);
        executeButton.disableProperty().bind(filesAvailableProperty.not());

        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(executeButton);

        this.setPadding(new Insets(5, 5, 10, 5));
        this.setTop(expressionsTitledPane);
        this.setCenter(fileWrappersTitledPane);
        this.setBottom(buttonBox);
        this.setRight(helpTitledPane);

    }

}
