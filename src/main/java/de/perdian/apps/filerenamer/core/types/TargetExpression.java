package de.perdian.apps.filerenamer.core.types;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Representation of the target expression that will be used to compute
 * the new names of the files.
 *
 * @author Christian Robert
 */

public class TargetExpression {

    private String value = null;
    private boolean valid = false;

    public TargetExpression(String value) {
        if (StringUtils.isEmpty(value)) {
            this.setValue(null);
            this.setValid(false);
        } else {
            this.setValue(value);
            this.setValid(true);
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

}
