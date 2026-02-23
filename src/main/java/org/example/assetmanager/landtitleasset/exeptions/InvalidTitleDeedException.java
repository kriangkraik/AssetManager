package org.example.assetmanager.landtitleasset.exeptions;

public class InvalidTitleDeedException extends RuntimeException {
    public InvalidTitleDeedException() {
        super("Title deed cannot be null or blank");
    }
}
