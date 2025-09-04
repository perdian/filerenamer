package de.perdian.apps.filerenamer_NEW.fx.support.types;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;

import java.io.File;
import java.io.FileFilter;

public class FileFilterExpression {

    private static final Logger log = LoggerFactory.getLogger(FileFilterExpression.class);

    private String expressionString = null;
    private Expression expression = null;
    private SpelParseException parserException = null;

    public FileFilterExpression() {
        this(null);
    }

    public FileFilterExpression(String expressionString) {
        this.setExpressionString(expressionString);
        if (StringUtils.isEmpty(expressionString)) {
            this.setExpression(null);
        } else {
            try {
                SpelExpressionParser expressionParser = new SpelExpressionParser();
                Expression expression = expressionParser.parseExpression(expressionString);
                this.setExpression(expression);
            } catch (SpelParseException e) {
                this.setParserException(e);
            }
        }
    }

    public FileFilter asFileFilter() {
        if (this.getExpression() == null) {
            return _ -> this.getParserException() == null;
        } else {
            return file -> {
                SimpleEvaluationContext evaluationContext = SimpleEvaluationContext
                    .forReadOnlyDataBinding()
                    .withRootObject(new RootObject(file))
                    .build();
                return Boolean.TRUE.equals(this.getExpression().getValue(evaluationContext, Boolean.class));
            };
        }
    }

    private static class RootObject {

        private FileWrapper file = null;

        private RootObject(File file) {
            this.setFile(new FileWrapper(file));
        }

        public FileWrapper getFile() {
            return this.file;
        }
        private void setFile(FileWrapper file) {
            this.file = file;
        }

    }

    private static class FileWrapper {

        private File file = null;

        private FileWrapper(File file) {
            this.setFile(file);
        }

        public String getName() {
            return this.getFile().getName();
        }

        public String getExtension() {
            String fileName = this.getFile().getName();
            int extensionStartIndex = fileName.lastIndexOf('.');
            if (extensionStartIndex < 0) {
                return null;
            } else {
                return fileName.substring(extensionStartIndex + 1);
            }
        }

        public String getAbsolutePath() {
            return this.getFile().getAbsolutePath();
        }

        public FileWrapper getParent() {
            return new FileWrapper(this.getFile().getParentFile());
        }

        private File getFile() {
            return this.file;
        }
        private void setFile(File file) {
            this.file = file;
        }

    }

    public boolean isValid() {
        return this.getParserException() == null;
    }

    public String getExpressionString() {
        return this.expressionString;
    }
    private void setExpressionString(String expressionString) {
        this.expressionString = expressionString;
    }

    public Expression getExpression() {
        return this.expression;
    }
    private void setExpression(Expression expression) {
        this.expression = expression;
    }

    public SpelParseException getParserException() {
        return this.parserException;
    }
    private void setParserException(SpelParseException parserException) {
        this.parserException = parserException;
    }

}
