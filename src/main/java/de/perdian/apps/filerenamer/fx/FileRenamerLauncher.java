package de.perdian.apps.filerenamer.fx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;

public class FileRenamerLauncher {

    private static final Logger log = LoggerFactory.getLogger(FileRenamerLauncher.class);

    public static void main(String[] args) {

        log.info("Launching application");
        Application.launch(FileRenamerApplication.class);

    }

}
