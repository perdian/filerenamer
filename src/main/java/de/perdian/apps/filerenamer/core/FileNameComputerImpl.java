package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.util.List;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import de.perdian.apps.filerenamer.core.expression.ExpressionRoot;
import de.perdian.apps.filerenamer.core.types.SourceExpression;
import de.perdian.apps.filerenamer.core.types.TargetExpression;

class FileNameComputerImpl implements FileNameComputer {

    private List<File> files = null;
    private SourceExpression sourceExpression = null;
    private TargetExpression targetExpression = null;

    @Override
    public String computeTargetFileName(File sourceFile) {
        if (this.getTargetExpression().getExpression() == null) {
            return null;
        } else {
            try {
                ExpressionRoot expressionRoot = new ExpressionRoot(sourceFile, this.getFiles(), this.getSourceExpression());
                StandardEvaluationContext evaluationContext = new StandardEvaluationContext(expressionRoot);
                return this.getTargetExpression().getExpression().getValue(evaluationContext, String.class).trim();
            } catch (EvaluationException e) {
                return null;
            }
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

}
