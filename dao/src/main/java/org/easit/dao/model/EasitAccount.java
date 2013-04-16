package org.easit.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class EasitAccount implements DomainObject {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4784685911399221915L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String username;

    String password;

    String firstName;

    String lastName;

    String email;

    String userToken;

    public EasitAccount() {

    }

    public EasitAccount(String username, String password, String firstName, String lastName, String email, String userToken) {
	super();
	this.username = username;
	this.password = password;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.userToken = userToken;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getUserToken() {
	return userToken;
    }

    public void setUserToken(String userToken) {
	this.userToken = userToken;
    }

}
