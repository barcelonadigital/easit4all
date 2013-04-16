/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.easit.core.controllers.contact;

import org.hibernate.validator.constraints.NotEmpty;

public class ContactForm {

    @NotEmpty
    private String firstName;

    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    private String text;

    @NotEmpty
    private String subject;

    public String getSubject() {
	return subject;
    }

    public void setSubject(String subject) {
	this.subject = subject;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
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

    public static ContactForm formulari(ContactForm data) {
	ContactForm form = new ContactForm();
	form.setFirstName(data.getFirstName());
	form.setLastName(data.getLastName());
	form.setEmail(data.getEmail());
	form.setSubject(data.getEmail());
	form.setText(data.getText());
	return form;
    }

}
