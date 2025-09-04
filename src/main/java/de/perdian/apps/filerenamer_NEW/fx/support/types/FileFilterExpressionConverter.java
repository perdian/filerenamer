package de.perdian.apps.filerenamer_NEW.fx.support.types;

import javafx.util.StringConverter;

public class FileFilterExpressionConverter extends StringConverter<FileFilterExpression> {

    @Override
    public String toString(FileFilterExpression object) {
        return object == null ? null : object.getExpressionString();
    }

    @Override
    public FileFilterExpression fromString(String string) {
        return new FileFilterExpression(string);
    }

}
