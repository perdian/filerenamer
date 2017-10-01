package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.util.List;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import de.perdian.apps.filerenamer.core.expression.ExpressionRoot;
import de.perdian.apps.filerenamer.core.expression.helpers.FileHelper;
import de.perdian.apps.filerenamer.core.expression.helpers.FormatHelper;
import de.perdian.apps.filerenamer.core.expression.helpers.RegexHelper;
import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;

class FileNameComputerImpl implements FileNameComputer {

    private List<File> files = null;
    private SourceExpression sourceExpression = null;
    private TargetExpression targetExpression = null;

    @Override
    public String computeTargetFileName(File sourceFile, int sourceFileIndex) {
        if (this.getTargetExpression().getExpression() == null) {
            return null;
        } else {
            try {
                ExpressionRoot expressionRoot = this.createExpressionRoot(sourceFile, sourceFileIndex);
                StandardEvaluationContext evaluationContext = new StandardEvaluationContext(expressionRoot);
                return this.getTargetExpression().getExpression().getValue(evaluationContext, String.class);
            } catch (EvaluationException e) {
                return null;
            }
        }
    }

    private ExpressionRoot createExpressionRoot(File sourceFile, int sourceFileIndex) {
        ExpressionRoot expressionRoot = new ExpressionRoot();
        expressionRoot.setRegex(new RegexHelper(this.getSourceExpression().getRegexPattern(), sourceFile.getName()));
        expressionRoot.setFile(new FileHelper(sourceFile, sourceFileIndex));
        expressionRoot.setFiles(this.getFiles());
        expressionRoot.setFormat(new FormatHelper(this.getFiles().size()));
        return expressionRoot;
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

}
