package org.easit.core.controllers.facebook;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UploadItem {

    private String caption;
    private String title;
    private CommonsMultipartFile fileData;

    public String getCaption() {
	return caption;
    }

    public void setCaption(String caption) {
	this.caption = caption;
    }

    public CommonsMultipartFile getFileData() {
	return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData) {
	this.fileData = fileData;
    }

    public String getTitle() {
	// TODO Auto-generated method stub
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

}
