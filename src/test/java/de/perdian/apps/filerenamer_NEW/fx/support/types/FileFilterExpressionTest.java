package de.perdian.apps.filerenamer_NEW.fx.support.types;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;

public class FileFilterExpressionTest {

    @Test
    void match() {

        File file = new File("foo.test");
        FileFilterExpression fileFilterExpression = new FileFilterExpression("file.extension eq 'test'");
        MatcherAssert.assertThat(fileFilterExpression.getExpression(), IsNull.notNullValue());
        MatcherAssert.assertThat(fileFilterExpression.getParserException(), IsNull.nullValue());

        FileFilter fileFilter = fileFilterExpression.asFileFilter();
        MatcherAssert.assertThat(fileFilter.accept(file), IsEqual.equalTo(true));

    }

    @Test
    void no_match() {

        File file = new File("foo.test");
        FileFilterExpression fileFilterExpression = new FileFilterExpression("file.extension eq 'bar'");
        MatcherAssert.assertThat(fileFilterExpression.getExpression(), IsNull.notNullValue());
        MatcherAssert.assertThat(fileFilterExpression.getParserException(), IsNull.nullValue());

        FileFilter fileFilter = fileFilterExpression.asFileFilter();
        MatcherAssert.assertThat(fileFilter.accept(file), IsEqual.equalTo(false));

    }

}
