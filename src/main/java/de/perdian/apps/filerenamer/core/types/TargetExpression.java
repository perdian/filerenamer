package de.perdian.apps.filerenamer.core.types;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * Representation of the target expression that will be used to compute
 * the new names of the files.
 *
 * @author Christian Seifert
 */

public class TargetExpression {

    private String value = null;
    private boolean valid = false;
    private Expression expression = null;
    private ParseException parseException = null;

    public TargetExpression(String value) {
        if (StringUtils.isEmpty(value)) {
            this.setValue(null);
            this.setValid(false);
        } else {
            this.setValue(value);
            ExpressionParser expressionParser = new SpelExpressionParser();
            try {
                this.setExpression(expressionParser.parseExpression(value, new TemplateParserContext("${", "}")));
                this.setValid(true);
            } catch (ParseException e) {
                this.setValid(false);
                this.setParseException(e);
            }
        }
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof TargetExpression) {
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(this.getValue(), ((TargetExpression)that).getValue());
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

    public Expression getExpression() {
        return this.expression;
    }
    private void setExpression(Expression expression) {
        this.expression = expression;
    }

    public ParseException getParseException() {
        return this.parseException;
    }
    private void setParseException(ParseException parseException) {
        this.parseException = parseException;
    }

}
