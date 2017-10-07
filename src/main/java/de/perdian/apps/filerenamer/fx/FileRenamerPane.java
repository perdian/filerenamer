package de.perdian.apps.filerenamer.fx;

import de.perdian.apps.filerenamer.fx.actions.UpdateFileNamesEventHandler;
import de.perdian.apps.filerenamer.fx.panels.ExpressionsPane;
import de.perdian.apps.filerenamer.fx.panels.FileWrappersPane;
import de.perdian.apps.filerenamer.fx.panels.HelpPane;
import de.perdian.apps.filerenamer.fx.panels.ProgressPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

class FileRenamerPane extends GridPane {

    FileRenamerPane() {

        FileRenamerModel fileRenamerModel = new FileRenamerModel();

        ExpressionsPane expressionsPane = new ExpressionsPane(fileRenamerModel.getSourceExpression(), fileRenamerModel.getTargetExpression());
        expressionsPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane expressionsTitledPane = new TitledPane("Expressions", expressionsPane);
        expressionsTitledPane.setExpanded(true);
        expressionsTitledPane.setCollapsible(false);
        GridPane.setHgrow(expressionsTitledPane, Priority.ALWAYS);

        FileWrappersPane fileWrappersPane = new FileWrappersPane(fileRenamerModel.getFiles(), fileRenamerModel.getFilesAvailable());
        TitledPane fileWrappersTitledPane = new TitledPane("Files", fileWrappersPane);
        fileWrappersTitledPane.setMaxHeight(Double.MAX_VALUE);
        fileWrappersTitledPane.setCollapsible(false);
        GridPane.setHgrow(fileWrappersTitledPane, Priority.ALWAYS);
        GridPane.setVgrow(fileWrappersTitledPane, Priority.ALWAYS);

        HelpPane helpPane = new HelpPane();
        helpPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane helpTitledPane = new TitledPane("Help", helpPane);
        helpTitledPane.setMaxHeight(Double.MAX_VALUE);
        helpTitledPane.setCollapsible(false);
        GridPane.setVgrow(helpTitledPane, Priority.ALWAYS);

        ProgressPane progressPane = new ProgressPane();
        progressPane.setPadding(new Insets(10, 10, 10, 10));
        fileRenamerModel.addListener(progressPane);
        TitledPane progressTitledPane = new TitledPane("Progress", progressPane);
        progressTitledPane.setCollapsible(false);
        GridPane.setHgrow(progressTitledPane, Priority.ALWAYS);

        Button executeButton = new Button("Execute rename");
        executeButton.setOnAction(new UpdateFileNamesEventHandler(fileRenamerModel.getFiles(), fileRenamerModel::recomputeTargetFileNames));
        executeButton.setDisable(true);
        executeButton.disableProperty().bind(fileRenamerModel.getFilesAvailable().not().or(fileRenamerModel.getBusy()));

        HBox buttonBox = new HBox(5);
        buttonBox.disableProperty().bind(fileRenamerModel.getBusy());
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(executeButton);
        GridPane.setHgrow(buttonBox, Priority.ALWAYS);

        this.setPadding(new Insets(5, 5, 10, 5));
        this.setHgap(10);
        this.setVgap(10);
        this.add(expressionsTitledPane, 0, 0, 2, 1);
        this.add(fileWrappersTitledPane, 0, 1, 1, 1);
        this.add(helpTitledPane, 1, 1, 1, 1);
        this.add(progressTitledPane, 0, 2, 2, 1);
        this.add(buttonBox, 0, 3, 2, 1);

    }

}
