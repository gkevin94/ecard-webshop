package com.ecard.ecardwebshop.image;

import org.springframework.http.MediaType;

public class Image {

    private long id;
    private byte[] fileBytes;
    private MediaType mediaType;
    private String fileName;
    private long productId;

    public Image() {
    }

    public Image(long id, byte[] fileBytes, MediaType mediaType, String fileName, long productId) {
        this.id = id;
        this.fileBytes = fileBytes;
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
