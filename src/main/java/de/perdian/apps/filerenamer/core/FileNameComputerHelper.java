package de.perdian.apps.filerenamer.core;

import java.io.File;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;

class FileNameComputerHelper {

    private static final Logger log = LoggerFactory.getLogger(FileNameComputerHelper.class);
    private Map<File, MetadataCacheItem> metadataCache = null;

    FileNameComputerHelper() {
        this.setMetadataCache(new HashMap<>());
    }

    Metadata loadMetadata(File sourceFile) {
        Instant fileLastModified = Instant.ofEpochMilli(sourceFile.lastModified());
        MetadataCacheItem cacheItem = this.getMetadataCache().get(sourceFile);
        if (cacheItem == null || cacheItem.getLastModified().equals(fileLastModified)) {
            cacheItem = new MetadataCacheItem(fileLastModified);
            try {
                cacheItem.setMetadata(ImageMetadataReader.readMetadata(sourceFile));
            } catch (Exception e) {
                log.warn("Cannot load metadata from file: " + sourceFile, e);
            }
            this.getMetadataCache().put(sourceFile, cacheItem);
        }
        return cacheItem.getMetadata();
    }

    static class MetadataCacheItem {

        private Instant lastModified = null;
        private Metadata metadata = null;

        MetadataCacheItem(Instant lastModified) {
            this.setLastModified(lastModified);
        }

        Instant getLastModified() {
            return this.lastModified;
        }
        void setLastModified(Instant lastModified) {
            this.lastModified = lastModified;
        }

        Metadata getMetadata() {
            return this.metadata;
        }
        void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

    }

    private Map<File, MetadataCacheItem> getMetadataCache() {
        return this.metadataCache;
    }
    private void setMetadataCache(Map<File, MetadataCacheItem> metadataCache) {
        this.metadataCache = metadataCache;
    }

}
