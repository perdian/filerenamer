package de.perdian.apps.filerenamer_NEW.fx.support.properties;

import de.perdian.apps.filerenamer_NEW.fx.FilerenamerApplication;
import de.perdian.apps.filerenamer_NEW.fx.support.converters.FileStringConverter;
import de.perdian.apps.filerenamer_NEW.fx.support.types.FileFilterExpression;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.prefs.Preferences;

public class FxPropertyHelper {

    private static final Logger log = LoggerFactory.getLogger(FxPropertyHelper.class);

    private static final Map<String, StringProperty> STRING_PROPERTIES = new HashMap<>();

    public static StringProperty createPersistentStringProperty(String key, String defaultValue) {
        return STRING_PROPERTIES.computeIfAbsent(key, _ -> {
            Preferences preferences = Preferences.userNodeForPackage(FilerenamerApplication.class);
            String preferencesValues = preferences.get(key, defaultValue);
            StringProperty property = new SimpleStringProperty(preferencesValues);
            property.addListener((_, _, newValue) -> {
                try {
                    preferences.put(key, newValue);
                } catch (Exception e) {
                    log.warn("Cannot update preferences value", e);
                }
            });
            return property;
        });
    }

    public static <T> ObjectProperty<T> createPersistentObjectProperty(String key, Supplier<T> defaultValueSupplier, StringConverter<T> stringConverter) {
        StringProperty stringProperty = FxPropertyHelper.createPersistentStringProperty(key, null);
        String stringPropertyValue = stringProperty.getValue();
        ObjectProperty<T> objectProperty = new SimpleObjectProperty<>(StringUtils.isEmpty(stringPropertyValue) ? defaultValueSupplier.get() : stringConverter.fromString(stringPropertyValue));
        objectProperty.addListener((_, _, newValue) -> stringProperty.setValue(stringConverter.toString(newValue)));
        return objectProperty;
    }

    public static ObjectProperty<File> createPersistentFileProperty(String key, File defaultValue) {
        return FxPropertyHelper.createPersistentObjectProperty(key, () -> defaultValue, new FileStringConverter());
    }

    public static BooleanProperty createPersistentBooleanProperty(String key, boolean defaultValue) {
        StringProperty stringProperty = FxPropertyHelper.createPersistentStringProperty(key, String.valueOf(defaultValue));
        BooleanProperty booleanProperty = new SimpleBooleanProperty("true".equalsIgnoreCase(stringProperty.getValue()));
        booleanProperty.addListener((_, _, newValue) -> stringProperty.setValue(String.valueOf(newValue)));
        return booleanProperty;
    }

    public static <T> ObservableStringValue createStringBindingForObject(ObservableObjectValue<T> objectProperty, StringConverter<T> stringConverter) {
        StringProperty stringProperty = new SimpleStringProperty(stringConverter.toString(objectProperty.getValue()));
        objectProperty.addListener((_, _, newValue) -> {
            String newStringValue = stringConverter.toString(newValue);
            if (!Objects.equals(newStringValue, stringProperty.getValue())) {
                stringProperty.setValue(newStringValue);
            }
        });
        return stringProperty;
    }

    public static ObservableStringValue createStringBindingForFile(ObservableObjectValue<File> fileProperty) {
        return FxPropertyHelper.createStringBindingForObject(fileProperty, new FileStringConverter());
    }

    public static ObservableBooleanValue createBooleanBindingForExpressionValid(ObservableStringValue expressionValue) {
        return null;
    }

    public static ObservableValue<FileFilterExpression> createObjectBindingForFileFilterExpression(ObservableStringValue expressionValue) {
        ObjectProperty<FileFilterExpression> fileFilterObjectProperty = new SimpleObjectProperty<>(new FileFilterExpression(expressionValue.getValue()));
        expressionValue.addListener((_, _, newValue) -> fileFilterObjectProperty.setValue(new FileFilterExpression(newValue)));
        return fileFilterObjectProperty;
    }

}
