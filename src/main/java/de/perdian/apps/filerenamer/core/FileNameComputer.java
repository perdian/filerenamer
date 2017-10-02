package de.perdian.apps.filerenamer.core;

import java.io.File;

public interface FileNameComputer {

    String computeTargetFileName(File sourceFile) throws Exception;

}
