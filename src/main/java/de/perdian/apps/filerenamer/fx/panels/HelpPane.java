package de.perdian.apps.filerenamer.fx.panels;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class HelpPane extends GridPane {

    public HelpPane() {

        Label titleLabel = new Label("Target expressions");

        StringBuilder content = new StringBuilder();
        content.append("index : Number\n");
        content.append("index(Number minDigits) : Number\n");
        content.append("lastModified : String\n");
        content.append("lastModified(String pattern) : String\n");
        content.append("regex(Number groupIndex) : String\n");
        Label contentLabel = new Label(content.toString());
        contentLabel.setFont(Font.font("Monospaced", 12d));

        this.add(titleLabel, 0, 0, 1, 1);
        this.add(contentLabel, 0, 1, 1, 1);
        this.setVgap(5);

    }

}
