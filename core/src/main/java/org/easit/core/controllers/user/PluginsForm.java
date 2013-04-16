package org.easit.core.controllers.user;

public class PluginsForm {

    private boolean sensors;
    private boolean news;
    private boolean favourites;

    public boolean isSensors() {
	return sensors;
    }

    public void setSensors(boolean sensors) {
	this.sensors = sensors;
    }

    public boolean isNews() {
	return news;
    }

    public void setNews(boolean news) {
	this.news = news;
    }

    public boolean isFavourites() {
	return favourites;
    }

    public void setFavourites(boolean favourites) {
	this.favourites = favourites;
    }
}