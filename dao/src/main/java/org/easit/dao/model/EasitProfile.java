package org.easit.dao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile")
public class EasitProfile implements DomainObject {

    private static final long serialVersionUID = 4500317332070231304L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long userId;

    int age;

    int technologySkills;

    int visualSkills;

    int hearingSkills;

    public EasitProfile() {

    }

    public EasitProfile(long userId, int age, int technologySkills, int visualSkills, int hearingSkills) {
	super();
	this.userId = userId;
	this.age = age;
	this.technologySkills = technologySkills;
	this.visualSkills = visualSkills;
	this.hearingSkills = hearingSkills;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public int getAge() {
	return age;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public int getTechnologySkills() {
	return technologySkills;
    }

    public void setTechnologySkills(int technologySkills) {
	this.technologySkills = technologySkills;
    }

    public int getVisualSkills() {
	return visualSkills;
    }

    public void setVisualSkills(int visualSkills) {
	this.visualSkills = visualSkills;
    }

    public int getHearingSkills() {
	return hearingSkills;
    }

    public void setHearingSkills(int hearingSkills) {
	this.hearingSkills = hearingSkills;
    }

}
