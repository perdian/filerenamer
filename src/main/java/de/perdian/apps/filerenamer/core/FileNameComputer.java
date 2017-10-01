package de.perdian.apps.filerenamer.core;

public interface FileNameComputer {

    String computeTargetFileName(String sourceFileName, int sourceFileIndex);

}
