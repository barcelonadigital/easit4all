package org.easit.dao.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "logoperations")
public class LogOperations implements DomainObject {

    private static final long serialVersionUID = -5790197743677104766L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;

    private String providerId;

    private String operation;

    private Timestamp date;

    public LogOperations() {

    }

    public LogOperations(long userId, String providerId, String operation, Timestamp date) {
	super();
	this.userId = userId;
	this.providerId = providerId;
	this.operation = operation;
	this.date = date;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public String getOperation() {
	return operation;
    }

    public void setOperation(String operation) {
	this.operation = operation;
    }

    public String getProviderId() {
	return providerId;
    }

    public void setProviderId(String providerId) {
	this.providerId = providerId;
    }

    public Timestamp getDate() {
	return date;
    }

    public void setDate(Timestamp date) {
	this.date = date;
    }

}
