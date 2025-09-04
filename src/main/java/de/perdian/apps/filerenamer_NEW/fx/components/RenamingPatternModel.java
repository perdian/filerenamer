package de.perdian.apps.filerenamer_NEW.fx.components;

import de.perdian.apps.filerenamer_NEW.fx.support.properties.FxPropertyHelper;
import de.perdian.apps.filerenamer_NEW.fx.support.types.RenamingSourcePattern;
import de.perdian.apps.filerenamer_NEW.fx.support.types.RenamingSourcePatternConverter;
import de.perdian.apps.filerenamer_NEW.fx.support.types.RenamingTargetExpression;
import de.perdian.apps.filerenamer_NEW.fx.support.types.RenamingTargetExpressionConverter;
import javafx.beans.property.ObjectProperty;

public class RenamingPatternModel {

    private final ObjectProperty<RenamingSourcePattern> renamingSourcePattern;
    private final ObjectProperty<RenamingTargetExpression> renamingTargetExpression;

    public RenamingPatternModel() {
        this.renamingSourcePattern = FxPropertyHelper.createPersistentObjectProperty(RenamingPatternModel.class.getSimpleName() + ".renamingSourcePattern", RenamingSourcePattern::new, new RenamingSourcePatternConverter());
        this.renamingTargetExpression = FxPropertyHelper.createPersistentObjectProperty(RenamingPatternModel.class.getSimpleName() + ".renamingTargetExpression", RenamingTargetExpression::new, new RenamingTargetExpressionConverter());
    }

    public RenamingSourcePattern getRenamingSourcePattern() {
        return this.renamingSourcePattern.get();
    }
    public ObjectProperty<RenamingSourcePattern> renamingSourcePatternProperty() {
        return this.renamingSourcePattern;
    }
    public void setRenamingSourcePattern(RenamingSourcePattern renamingSourcePattern) {
        this.renamingSourcePattern.set(renamingSourcePattern);
    }

    public RenamingTargetExpression getRenamingTargetExpression() {
        return this.renamingTargetExpression.get();
    }
    public ObjectProperty<RenamingTargetExpression> renamingTargetExpressionProperty() {
        return this.renamingTargetExpression;
    }
    public void setRenamingTargetExpression(RenamingTargetExpression renamingTargetExpression) {
        this.renamingTargetExpression.set(renamingTargetExpression);
    }

}
