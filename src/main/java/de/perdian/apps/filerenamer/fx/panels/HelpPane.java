package de.perdian.apps.filerenamer.fx.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class HelpPane extends GridPane {

    public HelpPane() {

        Label titleLabel = new Label("Target expressions");

        StringBuilder content = new StringBuilder();
        content.append("file.index() : Number\n");
        content.append("file.lastModified() : Instant\n");
        content.append("file.name() : String\n");
        content.append("format.date(<Instant>, <String>) : String\n");
        content.append("format.number(<Number>) : String\n");
        content.append("format.toLowercase(<String>) : String\n");
        content.append("format.toUppercase(<String>) : String\n");
        content.append("regex.group(<Number>) : String\n");
        Label contentLabel = new Label(content.toString());
        contentLabel.setFont(Font.font("Monospaced", 12d));

        this.add(titleLabel, 0, 0, 1, 1);
        this.add(contentLabel, 0, 1, 1, 1);
        this.setVgap(5);

    }

}
