package org.easit.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userconnection")
// ,uniqueConstraints = {@UniqueConstraint (columnNames = {"userId",
// "providerId","providerUserId"})})
public class UserConnection implements DomainObject {

    private static final long serialVersionUID = 4518268963142392086L;

    @Id
    Integer id;

    @Column(nullable = false)
    String userId;

    @Column(nullable = false)
    String providerId;

    @Column(nullable = false)
    String providerUserId;

    @Column(nullable = false)
    Integer rank;

    String displayName;

    String profileUrl;

    String imageUrl;

    // String email;

    @Column(nullable = false)
    String accessToken;

    String secret;

    String refreshToken;

    Long expireTime;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getProviderId() {
	return providerId;
    }

    public void setProviderId(String providerId) {
	this.providerId = providerId;
    }

    public String getProviderUserId() {
	return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
	this.providerUserId = providerUserId;
    }

    public Integer getRank() {
	return rank;
    }

    public void setRank(Integer rank) {
	this.rank = rank;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    public String getProfileUrl() {
	return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
	this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    /*
     * public String getEmail() { return email; }
     * 
     * public void setEmail(String email) { this.email = email; }
     */
    public String getAccessToken() {
	return accessToken;
    }

    public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
    }

    public String getSecret() {
	return secret;
    }

    public void setSecret(String secret) {
	this.secret = secret;
    }

    public String getRefreshToken() {
	return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
	this.refreshToken = refreshToken;
    }

    public Long getExpireTime() {
	return expireTime;
    }

    public void setExpireTime(Long expireTime) {
	this.expireTime = expireTime;
    }

}
