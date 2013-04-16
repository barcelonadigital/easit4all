package org.easit.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name = "applicationpreferences")
public class EasitApplicationPreferences implements DomainObject {

    private static final long serialVersionUID = -3969269104173515984L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;

    private String textFont;

    private String theme;

    private double textSize;

    private double lineSpacing;

    private boolean layout;

    private boolean toc;

    private boolean links;

    private boolean inputsLarger;

    private String genericTextFont;

    private double magnification;

    private String tracking;

    private boolean invertImages;

    public EasitApplicationPreferences() {
    }

    @JsonCreator
    public EasitApplicationPreferences(@JsonProperty("textFont") String textFont, @JsonProperty("theme") String theme, @JsonProperty("textSize") double textSize,
	    @JsonProperty("lineSpacing") double lineSpacing, @JsonProperty("layout") boolean layout, @JsonProperty("toc") boolean toc, @JsonProperty("links") boolean links,
	    @JsonProperty("inputsLarger") boolean inputsLarger, @JsonProperty("magnification") double magnification, @JsonProperty("invertImages") boolean invertImages,
	    @JsonProperty("tracking") String tracking) {
	this.textFont = textFont;
	this.theme = theme;
	this.textSize = textSize;
	this.lineSpacing = lineSpacing;
	this.layout = layout;
	this.toc = toc;
	this.links = links;
	this.inputsLarger = inputsLarger;
	this.magnification = magnification;
	this.invertImages = invertImages;
	this.tracking = tracking;
    }

    public EasitApplicationPreferences(long userId, String textFont, String theme, double textSize, double lineSpacing, boolean layout, boolean toc, boolean links, boolean inputsLarger,
	    double magnification, boolean invertImages, String tracking) {
	this.userId = userId;
	this.textFont = textFont;
	this.theme = theme;
	this.textSize = textSize;
	this.lineSpacing = lineSpacing;
	this.layout = layout;
	this.toc = toc;
	this.links = links;
	this.inputsLarger = inputsLarger;
	this.magnification = magnification;
	this.invertImages = invertImages;
	this.tracking = tracking;
	System.out.println("other");
    }

    @JsonIgnore
    public long getUserId() {
	return userId;
    }

    public String getTextFont() {
	return textFont;
    }

    public String getTheme() {
	return theme;
    }

    public double getTextSize() {
	return textSize;
    }

    public double getLineSpacing() {
	return lineSpacing;
    }

    public boolean isLayout() {
	return layout;
    }

    public boolean isToc() {
	return toc;
    }

    public boolean isLinks() {
	return links;
    }

    public boolean isInputsLarger() {
	return inputsLarger;
    }

    public void setTextFont(String textFont) {
	this.textFont = textFont;
    }

    public void setTheme(String theme) {
	this.theme = theme;
    }

    public void setTextSize(double textSize) {
	this.textSize = textSize;
    }

    public void setLineSpacing(double lineSpacing) {
	this.lineSpacing = lineSpacing;
    }

    public void setLayout(boolean layout) {
	this.layout = layout;
    }

    public void setToc(boolean toc) {
	this.toc = toc;
    }

    public void setLinks(boolean links) {
	this.links = links;
    }

    public void setInputsLarger(boolean inputsLarger) {
	this.inputsLarger = inputsLarger;
    }

    public String getGenericTextFont() {
	return genericTextFont;
    }

    public void setGenericTextFont(String genericTextFont) {
	this.genericTextFont = genericTextFont;
    }

    public double getMagnification() {
	return magnification;
    }

    public void setMagnification(double magnification) {
	this.magnification = magnification;
    }

    public String getTracking() {
	return tracking;
    }

    public void setTracking(String tracking) {
	this.tracking = tracking;
    }

    public boolean isInvertImages() {
	return invertImages;
    }

    public void setInvertImages(boolean invertImages) {
	this.invertImages = invertImages;
    }

}
