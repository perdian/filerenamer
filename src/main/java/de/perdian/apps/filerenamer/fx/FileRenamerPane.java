package de.perdian.apps.filerenamer.fx;

import de.perdian.apps.filerenamer.fx.actions.UpdateFileNamesEventHandler;
import de.perdian.apps.filerenamer.fx.panels.ExpressionsPane;
import de.perdian.apps.filerenamer.fx.panels.FileWrappersPane;
import de.perdian.apps.filerenamer.fx.panels.HelpPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

class FileRenamerPane extends BorderPane {

    FileRenamerPane() {

        FileRenamerModel fileRenamerModel = new FileRenamerModel();

        ExpressionsPane expressionsPane = new ExpressionsPane(fileRenamerModel.getSourceExpression(), fileRenamerModel.getTargetExpression());
        expressionsPane.setPadding(new Insets(10, 10, 10, 10));
        TitledPane expressionsTitledPane = new TitledPane("Expressions", expressionsPane);
        expressionsTitledPane.setExpanded(true);
        expressionsTitledPane.setCollapsible(false);

        FileWrappersPane fileWrappersPane = new FileWrappersPane(fileRenamerModel.getFiles(), fileRenamerModel.getFilesAvailable());
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
        executeButton.setOnAction(new UpdateFileNamesEventHandler(fileRenamerModel.getFiles(), fileRenamerModel::recomputeTargetFileNames));
        executeButton.setDisable(true);
        executeButton.disableProperty().bind(fileRenamerModel.getFilesAvailable().not());

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
