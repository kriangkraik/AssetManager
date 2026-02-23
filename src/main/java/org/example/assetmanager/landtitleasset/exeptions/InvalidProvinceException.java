package org.example.assetmanager.landtitleasset.exeptions;

public class InvalidProvinceException extends RuntimeException {
    public InvalidProvinceException() {
        super("Province cannot be null or blank");
    }
}
