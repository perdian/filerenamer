package de.perdian.apps.filerenamer_NEW.fx;

import de.perdian.apps.filerenamer_NEW.fx.components.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilerenamerApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(FilerenamerApplicationLauncher.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane applicationPane = this.createApplicationPane();
        Scene applicationScene = new Scene(applicationPane);

        log.debug("Opening main application window");
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(700);
        primaryStage.setTitle("Filerenamer by perdian");
        primaryStage.setWidth(Math.min(1800, screenBounds.getWidth() - 250));
        primaryStage.setHeight(Math.min(1200, screenBounds.getHeight() - 250));
        primaryStage.setScene(applicationScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }

    private Pane createApplicationPane() {

        InputFilesSelectionModel inputFilesSelectionModel = new InputFilesSelectionModel();
        ItemsModel itemsModel = new ItemsModel();
        itemsModel.automaticallyUpdateFromFiles(inputFilesSelectionModel.getLoadedFiles());
        RenamingPatternModel renamingPatternModel = new RenamingPatternModel();

        BooleanProperty busyProperty = new SimpleBooleanProperty(false);

        InputFilesSelectionPane inputFilesSelectionPane = new InputFilesSelectionPane(inputFilesSelectionModel, busyProperty);
        TitledPane inputFilesSelectionTitledPane = new TitledPane("Input files selection", inputFilesSelectionPane);
        inputFilesSelectionTitledPane.setCollapsible(false);
        inputFilesSelectionTitledPane.setFocusTraversable(false);
        inputFilesSelectionTitledPane.setGraphic(new FontIcon(RemixiconAL.LIST_CHECK));

        ItemsPane itemsPane = new ItemsPane(itemsModel, busyProperty);
        TitledPane itemsTitledPane = new TitledPane("Items", itemsPane);
        itemsTitledPane.setCollapsible(false);
        itemsTitledPane.setFocusTraversable(false);
        itemsTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(itemsTitledPane, Priority.ALWAYS);
        GridPane.setVgrow(itemsTitledPane, Priority.ALWAYS);

        RenamingPatternPane renamingPatternPane = new RenamingPatternPane(renamingPatternModel);
        TitledPane renamingPatternTitledPane = new TitledPane("Renaming pattern", renamingPatternPane);
        renamingPatternTitledPane.setCollapsible(false);
        renamingPatternTitledPane.setFocusTraversable(false);
        renamingPatternTitledPane.setGraphic(new FontIcon(RemixiconAL.EDIT_BOX_FILL));

        ExecuteRenamingPane executeRenamingPane = new ExecuteRenamingPane();
        TitledPane executeRenaimingTitledPane = new TitledPane("Execute renaming", executeRenamingPane);
        executeRenaimingTitledPane.setCollapsible(false);
        executeRenaimingTitledPane.setFocusTraversable(false);
        executeRenaimingTitledPane.setGraphic(new FontIcon(RemixiconMZ.PLAY_CIRCLE_FILL));

        GridPane leftColumnPane = new GridPane();
        leftColumnPane.setVgap(10);
        leftColumnPane.add(inputFilesSelectionTitledPane, 0, 0, 1, 1);
        leftColumnPane.add(renamingPatternTitledPane, 0, 1, 1, 1);
        leftColumnPane.add(executeRenaimingTitledPane, 0, 2, 1, 1);
        GridPane.setVgrow(leftColumnPane, Priority.ALWAYS);

        GridPane middleColumnPane = new GridPane();
        middleColumnPane.add(itemsTitledPane, 0, 0, 1, 1);
        GridPane.setHgrow(middleColumnPane, Priority.ALWAYS);
        GridPane.setVgrow(middleColumnPane, Priority.ALWAYS);

        ActiveItemPreviewPane activeItemPreviewPane = new ActiveItemPreviewPane(itemsModel);
        TitledPane activeItemPreviewTitledPane = new TitledPane("Active item", activeItemPreviewPane);
        activeItemPreviewTitledPane.setCollapsible(false);
        activeItemPreviewTitledPane.setFocusTraversable(false);
        activeItemPreviewTitledPane.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(activeItemPreviewTitledPane, Priority.ALWAYS);

        GridPane rightColumnPane = new GridPane();
        rightColumnPane.setVgap(10);
        rightColumnPane.add(activeItemPreviewTitledPane, 0, 0, 1, 1);
        GridPane.setVgrow(rightColumnPane, Priority.ALWAYS);

        GridPane applicationPane = new GridPane();
        applicationPane.add(leftColumnPane, 0, 0, 1, 1);
        applicationPane.add(middleColumnPane, 1, 0, 1, 1);
        applicationPane.add(rightColumnPane, 2, 0, 1, 1);
        applicationPane.setHgap(10);
        applicationPane.setPadding(new Insets(15, 15, 15, 15));
        return applicationPane;

    }

}
