package org.easit.core.controllers.user;

public class PreferencesForm {

    private int textSize;

    private int textSpacing;

    private String colour;

    private int contrast;

    private int brightness;

    private int magnification;

    private String volume;

    private boolean breadCrumbs;

    public int getTextSize() {
	return textSize;
    }

    public void setTextSize(int textSize) {
	this.textSize = textSize;
    }

    public int getTextSpacing() {
	return textSpacing;
    }

    public void setTextSpacing(int textSpacing) {
	this.textSpacing = textSpacing;
    }

    public String getColour() {
	return colour;
    }

    public void setColour(String colour) {
	this.colour = colour;
    }

    public int getContrast() {
	return contrast;
    }

    public void setContrast(int contrast) {
	this.contrast = contrast;
    }

    public int getBrightness() {
	return brightness;
    }

    public void setBrightness(int brightness) {
	this.brightness = brightness;
    }

    public int getMagnification() {
	return magnification;
    }

    public void setMagnification(int magnification) {
	this.magnification = magnification;
    }

    public String getVolume() {
	return volume;
    }

    public void setVolume(String volume) {
	this.volume = volume;
    }

    public boolean isBreadCrumbs() {
	return breadCrumbs;
    }

    public void setBreadCrumbs(boolean breadCrumbs) {
	this.breadCrumbs = breadCrumbs;
    }

}
