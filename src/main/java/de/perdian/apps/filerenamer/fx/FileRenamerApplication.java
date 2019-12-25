package de.perdian.apps.filerenamer.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FileRenamerApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(FileRenamerApplication.class);

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileRenamerPane mainPane = new FileRenamerPane();

        log.info("Opening JavaFX stage");
        primaryStage.getIcons().add(new Image(this.getClass().getClassLoader().getResourceAsStream("icons/256/application.png")));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("File Renamer");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(Math.min(Screen.getPrimary().getBounds().getHeight() - 100, 900));
        primaryStage.show();

    }

}
