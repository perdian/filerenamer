package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;

class FileNameComputerImpl implements FileNameComputer {

    static final Logger log = LoggerFactory.getLogger(FileNameComputerImpl.class);

    private FileNameComputerHelper helper = null;
    private List<File> files = null;
    private SourceExpression sourceExpression = null;
    private TargetExpression targetExpression = null;

    FileNameComputerImpl(FileNameComputerHelper helper) {
        this.setHelper(helper);
    }

    @Override
    public String computeTargetFileName(File sourceFile) {
        if (this.getTargetExpression().getExpression() == null) {
            return null;
        } else {
            try {
                ExpressionRoot expressionRoot = new ExpressionRoot(sourceFile, this.getSourceExpression().getRegexPattern().matcher(sourceFile.getName()));
                StandardEvaluationContext evaluationContext = new StandardEvaluationContext(expressionRoot);
                return this.getTargetExpression().getExpression().getValue(evaluationContext, String.class).trim();
            } catch (EvaluationException e) {
                return null;
            }
        }
    }

    class ExpressionRoot {

        private File sourceFile = null;
        private Matcher regexMatcher = null;
        private Random random = null;

        ExpressionRoot(File sourceFile, Matcher regexMatcher) {
            this.setSourceFile(sourceFile);
            this.setRegexMatcher(regexMatcher);
            this.setRandom(new SecureRandom());
        }

        public String regex(int groupIndex) {
            if (this.getRegexMatcher().matches()) {
                return this.getRegexMatcher().group(groupIndex);
            } else {
                return null;
            }
        }

        public String getIndex() {
            return this.index((int)Math.ceil(Math.log10(FileNameComputerImpl.this.getFiles().size())));
        }

        public String index(int minDigits) {
            int fileIndex = FileNameComputerImpl.this.getFiles().indexOf(this.getSourceFile());
            if (fileIndex < 0) {
                return "";
            } else {
                return this.formatNumber(fileIndex + 1, minDigits);
            }
        }

        public String random() {
            return this.random((int)Math.ceil(Math.log10(FileNameComputerImpl.this.getFiles().size())));
        }

        public String random(int numberOfDigits) {
            StringBuilder result = new StringBuilder();
            for (int i=0; i < numberOfDigits; i++) {
                result.append("0123456789".charAt(this.getRandom().nextInt(10)));
            }
            return result.toString();
        }

        public String getLastModified() {
            return this.lastModified(null);
        }

        public String lastModified(String pattern) {
            return this.getSourceFile().lastModified() <= 0 ? "" : this.formatDate(Instant.ofEpochMilli(this.getSourceFile().lastModified()), pattern);
        }


        public String exifDate() {
            return this.exifDate("yyyy-MM-dd");
        }

        public String exifTime() {
            return this.exifTime("HH:mm:ss");
        }

        public String exifTime(String pattern) {
            return this.exifDate(pattern);
        }

        public String exifDate(String pattern) {
            Date exifDate = this.exif(ExifDirectoryBase.TAG_DATETIME_ORIGINAL, (directory, tag) -> directory.getDate(tag));
            return exifDate == null ? null : this.formatDate(exifDate.toInstant(), pattern);
        }

        private <T> T exif(int tag, BiFunction<Directory, Integer, T> propertyFunction) {
            try {
                Metadata metadata = FileNameComputerImpl.this.getHelper().loadMetadata(this.getSourceFile());
                Directory exifDirectory = metadata == null ? null : metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
                if (exifDirectory == null) {
                    exifDirectory = metadata == null ? null : metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
                }
                if (exifDirectory != null) {
                    return propertyFunction.apply(exifDirectory, tag);
                }
            } catch (Exception e) {
                log.warn("Cannot read metadata from file: " + this.getSourceFile(), e);
            }
            return null;
        }

        public String formatDate(TemporalAccessor date, String pattern) {
            if (date == null) {
                return "";
            } else {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern == null ? "yyyyMMdd" : pattern).withZone(ZoneId.systemDefault());
                return dateTimeFormatter.format(date);
            }
        }

        public String formatNumber(Number number, int minDigits) {
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

        private Matcher getRegexMatcher() {
            return this.regexMatcher;
        }
        private void setRegexMatcher(Matcher regexMatcher) {
            this.regexMatcher = regexMatcher;
        }

        private Random getRandom() {
            return this.random;
        }
        private void setRandom(Random random) {
            this.random = random;
        }

    }

    List<File> getFiles() {
        return this.files;
    }
    void setFiles(List<File> files) {
        this.files = files;
    }

    SourceExpression getSourceExpression() {
        return this.sourceExpression;
    }
    void setSourceExpression(SourceExpression sourceExpression) {
        this.sourceExpression = sourceExpression;
    }

    TargetExpression getTargetExpression() {
        return this.targetExpression;
    }
    void setTargetExpression(TargetExpression targetExpression) {
        this.targetExpression = targetExpression;
    }

    FileNameComputerHelper getHelper() {
        return this.helper;
    }
    void setHelper(FileNameComputerHelper helper) {
        this.helper = helper;
    }

}
