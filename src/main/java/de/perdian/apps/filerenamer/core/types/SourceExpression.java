package de.perdian.apps.filerenamer.core.types;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Representation of a source regex expression that is used to determine
 * how to interpret the original file names
 *
 * @author Christian Seifert
 */

public class SourceExpression {

    private String value = null;
    private boolean valid = false;
    private Pattern regexPattern = null;
    private PatternSyntaxException regexException = null;

    public SourceExpression(String value) {
        if (StringUtils.isEmpty(value)) {
            this.setValue(null);
            this.setValid(false);
            this.setRegexPattern(null);
            this.setRegexException(null);
        } else {
            this.setValue(value);
            try {
                this.setRegexPattern(Pattern.compile(value));
                this.setValid(true);
                this.setRegexException(null);
            } catch (PatternSyntaxException e) {
                this.setRegexPattern(null);
                this.setValid(false);
                this.setRegexException(e);
            }
        }
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof SourceExpression) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getValue(), ((SourceExpression)that).getValue());
            return equalsBuilder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(this.getValue());
        return hashCodeBuilder.toHashCode();
    }

    public String getValue() {
        return this.value;
    }
    private void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {
        return this.valid;
    }
    private void setValid(boolean valid) {
        this.valid = valid;
    }

    public Pattern getRegexPattern() {
        return this.regexPattern;
    }
    private void setRegexPattern(Pattern regexPattern) {
        this.regexPattern = regexPattern;
    }

    public PatternSyntaxException getRegexException() {
        return this.regexException;
    }
    private void setRegexException(PatternSyntaxException regexException) {
        this.regexException = regexException;
    }

}
