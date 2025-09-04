package de.perdian.apps.filerenamer_NEW.fx.components;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ActiveItemPreviewPane extends BorderPane {

    public ActiveItemPreviewPane(ItemsModel model) {
        this.setMinWidth(300);

        Label activeItemLabel = new Label("");
        model.activeItemProperty().addListener((_, _, newValue) -> {
            activeItemLabel.setText(newValue == null ? "<>" : newValue.getExistingFileName());
        });
        this.setCenter(activeItemLabel);

    }

}
