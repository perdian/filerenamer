package de.perdian.apps.filerenamer.fx.panels;

import org.apache.commons.lang3.StringUtils;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;
import javafx.beans.property.Property;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ExpressionsPane extends GridPane {

    public ExpressionsPane(Property<SourceExpression> sourceExpressionProperty, Property<TargetExpression> targetExpressionProperty) {

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

        this.setHgap(2);
        this.setVgap(2);
        this.add(sourceExpressionLabel, 0, 0, 1, 1);
        this.add(sourceExpressionField, 0, 1, 1, 1);
        this.add(sourceExpressionInfoLabel, 0, 2, 2, 1);
        this.add(targetExpressionLabel, 0, 3, 1, 1);
        this.add(targetExpressionField, 0, 4, 1, 1);
        this.add(targetExpressionInfoLabel, 0, 5, 2, 1);

        sourceExpressionField.setText("(.*)");
        targetExpressionField.setText("${regex.group(1)}");

    }

}
