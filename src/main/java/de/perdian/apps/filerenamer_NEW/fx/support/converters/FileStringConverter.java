package de.perdian.apps.filerenamer_NEW.fx.support.converters;

import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileStringConverter extends StringConverter<File> {

    @Override
    public String toString(File object) {
        return object == null ? null : object.getAbsolutePath();
    }

    @Override
    public File fromString(String string) {
        return StringUtils.isEmpty(string) ? null : new File(string);
    }

}
