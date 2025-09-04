package de.perdian.apps.filerenamer_NEW.fx.support.components;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;
import java.util.function.Predicate;

public class ValidationComponentBuilder<T> {

    private ObservableValue<T> value = null;
    private Predicate<T> validationPredicate = _ -> true;
    private Function<T, String> validLabelFunction = _ -> "VALID";
    private String validColor = "#EEFFEE";
    private Function<T, String> invalidLabelFunction = _ -> "INVALID";
    private String invalidColor = "#FFEEEE";
    private Function<T, String> errorMessageFunction = _ -> null;

    public ValidationComponentBuilder(ObservableValue<T> value) {
        this.setValue(value);
    }

    public Node buildTextField() {
        TextField validationTextField = new TextField();
        validationTextField.setEditable(false);
        validationTextField.setFocusTraversable(false);
        validationTextField.setPrefWidth(0);
        validationTextField.setAlignment(Pos.CENTER);
        this.updateTextField(validationTextField, this.getValue().getValue());
        this.getValue().addListener((_, _, newValue) -> this.updateTextField(validationTextField, newValue));
        return validationTextField;
    }

    private void updateTextField(TextField textField, T value) {
        if (this.getValidationPredicate().test(value)) {
            textField.setText(this.getValidLabelFunction().apply(value));
            textField.setStyle("-fx-control-inner-background: " + this.getValidColor());
            textField.setTooltip(new Tooltip("Valid expression"));
        } else {
            String errorMessage = this.getErrorMessageFunction().apply(value);
            StringBuilder errorMessageBuilder = new StringBuilder();
            errorMessageBuilder.append(this.getErrorMessageFunction().apply(value));
            if (StringUtils.isNotEmpty(errorMessage)) {
                errorMessageBuilder.append(": ").append(errorMessage);
            }
            textField.setText(this.getInvalidLabelFunction().apply(value));
            textField.setStyle("-fx-control-inner-background: " + this.getInvalidColor());
            textField.setTooltip(new Tooltip(errorMessageBuilder.toString()));
        }

    }

    public ObservableValue<T> getValue() {
        return this.value;
    }
    private void setValue(ObservableValue<T> value) {
        this.value = value;
    }

    public ValidationComponentBuilder<T> withValidationPredicate(Predicate<T> validationPredicate) {
        this.setValidationPredicate(validationPredicate);
        return this;
    }
    public Predicate<T> getValidationPredicate() {
        return this.validationPredicate;
    }
    public void setValidationPredicate(Predicate<T> validationPredicate) {
        this.validationPredicate = validationPredicate;
    }

    public ValidationComponentBuilder<T> withValidLabelFunction(Function<T, String> validLabelFunction) {
        this.setValidLabelFunction(validLabelFunction);
        return this;
    }
    public Function<T, String> getValidLabelFunction() {
        return this.validLabelFunction;
    }
    public void setValidLabelFunction(Function<T, String> validLabelFunction) {
        this.validLabelFunction = validLabelFunction;
    }

    public ValidationComponentBuilder<T> withValidColor(String validColor) {
        this.setValidColor(validColor);
        return this;
    }
    public String getValidColor() {
        return this.validColor;
    }
    public void setValidColor(String validColor) {
        this.validColor = validColor;
    }

    public ValidationComponentBuilder<T> withInvalidLabelFunction(Function<T, String> invalidLabelFunction) {
        this.setInvalidLabelFunction(invalidLabelFunction);
        return this;
    }
    public Function<T, String> getInvalidLabelFunction() {
        return this.invalidLabelFunction;
    }
    public void setInvalidLabelFunction(Function<T, String> invalidLabelFunction) {
        this.invalidLabelFunction = invalidLabelFunction;
    }

    public ValidationComponentBuilder<T> withInvalidColor(String invalidColor) {
        this.setInvalidColor(invalidColor);
        return this;
    }
    public String getInvalidColor() {
        return this.invalidColor;
    }
    public void setInvalidColor(String invalidColor) {
        this.invalidColor = invalidColor;
    }

    public ValidationComponentBuilder<T> withErrorMessageFunction(Function<T, String> errorMessageFunction) {
        this.setErrorMessageFunction(errorMessageFunction);
        return this;
    }
    public Function<T, String> getErrorMessageFunction() {
        return this.errorMessageFunction;
    }
    public void setErrorMessageFunction(Function<T, String> errorMessageFunction) {
        this.errorMessageFunction = errorMessageFunction;
    }

}
