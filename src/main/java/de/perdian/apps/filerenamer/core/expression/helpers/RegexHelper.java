package de.perdian.apps.filerenamer.core.expression.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    private Matcher matcher = null;

    public RegexHelper(Pattern regexPattern, String value) {
        this.setMatcher(regexPattern.matcher(value));
    }

    public String group(int groupIndex) {
        if (this.getMatcher().matches()) {
            return this.getMatcher().group(groupIndex);
        } else {
            return null;
        }
    }

    private Matcher getMatcher() {
        return this.matcher;
    }
    private void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

}
