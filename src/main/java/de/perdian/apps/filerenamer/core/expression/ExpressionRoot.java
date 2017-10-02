package de.perdian.apps.filerenamer.core.expression;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.regex.Matcher;

import de.perdian.apps.filerenamer.core.types.SourceExpression;

public class ExpressionRoot {

    private File sourceFile = null;
    private List<File> allFiles = null;
    private Matcher regexMatcher = null;

    public ExpressionRoot(File sourceFile, List<File> allFiles, SourceExpression sourceExpression) {
        this.setSourceFile(sourceFile);
        this.setAllFiles(allFiles);
        this.setRegexMatcher(sourceExpression.getRegexPattern().matcher(sourceFile.getName()));
    }

    public String regex(int groupIndex) {
        if (this.getRegexMatcher().matches()) {
            return this.getRegexMatcher().group(groupIndex);
        } else {
            return null;
        }
    }

    public String getIndex() {
        return this.index((int)Math.ceil(Math.log10(this.getAllFiles().size())));
    }

    public String index(int minDigits) {
        int fileIndex = this.getAllFiles().indexOf(this.getSourceFile());
        if (fileIndex < 0) {
            return "";
        } else {
            return this.formatNumber(fileIndex + 1, minDigits);
        }
    }

    public String getLastModified() {
        return this.lastModified(null);
    }

    public String lastModified(String pattern) {
        return this.getSourceFile().lastModified() <= 0 ? "" : this.formatDate(Instant.ofEpochMilli(this.getSourceFile().lastModified()), pattern);
    }

    private String formatDate(TemporalAccessor date, String pattern) {
        if (date == null) {
            return "";
        } else {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern == null ? "yyyyMMdd" : pattern).withZone(ZoneId.systemDefault());
            return dateTimeFormatter.format(date);
        }
    }

    private String formatNumber(Number number, int minDigits) {
        if (number == null) {
            return "";
        } else {
            NumberFormat numberFormat = new DecimalFormat();
            numberFormat.setGroupingUsed(false);
            numberFormat.setMinimumIntegerDigits(minDigits);
            return numberFormat.format(number);
        }
    }

    private File getSourceFile() {
        return this.sourceFile;
    }
    private void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    private List<File> getAllFiles() {
        return this.allFiles;
    }
    private void setAllFiles(List<File> allFiles) {
        this.allFiles = allFiles;
    }

    private Matcher getRegexMatcher() {
        return this.regexMatcher;
    }
    private void setRegexMatcher(Matcher regexMatcher) {
        this.regexMatcher = regexMatcher;
    }

}
