package de.perdian.apps.filerenamer.core.expression.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public class FormatHelper {

    private long maxNumber = 0;

    public FormatHelper(long maxNumber) {
        this.setMaxNumber(maxNumber);
    }

    public String date(TemporalAccessor value, String pattern) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern(pattern)
            .withZone(ZoneId.systemDefault());

        return dateTimeFormatter.format(value);

    }

    public String number(long value) {
        return this.number(value, this.getMaxNumber());
    }

    public String number(long value, long maxValue) {
        NumberFormat numberFormat = new DecimalFormat();
        numberFormat.setMinimumIntegerDigits(String.valueOf(maxValue).length());
        return numberFormat.format(value);
    }

    public String toLowercase(String value) {
        return Optional.ofNullable(value).map(String::toLowerCase).orElse("");
    }

    public String toUppercase(String value) {
        return Optional.ofNullable(value).map(String::toUpperCase).orElse("");
    }

    private long getMaxNumber() {
        return this.maxNumber;
    }
    private void setMaxNumber(long maxNumber) {
        this.maxNumber = maxNumber;
    }

}
