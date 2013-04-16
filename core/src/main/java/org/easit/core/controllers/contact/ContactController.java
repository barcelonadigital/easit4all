package org.easit.core.controllers.contact;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private MailSender mailSender;

    public void setMailSender(MailSender mailSender) {
	this.mailSender = mailSender;
    }

    @ModelAttribute("ContactForm")
    public ContactForm ma() {
	return new ContactForm();
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView showForm(String error) {
	ModelAndView modelAndView = new ModelAndView();

	if (error != null && !error.isEmpty())
	    modelAndView.addObject("param_error", error);

	return modelAndView;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String showForm2() {
	return "about";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView processForm(@Valid ContactForm data, BindingResult formBinding) {
	ModelAndView modelAndView = new ModelAndView();
	if (formBinding.hasErrors()) {
	    modelAndView.addObject("param_error", "error");
	} else {
	    if (sendMail(data)) {
		modelAndView.addObject("send_ok", "mail_enviat");
	    } else {
		modelAndView.addObject("no_send", "mail_no_enviat");
	    }
	}
	return modelAndView;
    }

    public boolean sendMail(ContactForm data) {
	SimpleMailMessage msg = new SimpleMailMessage();
	msg.setFrom(data.getEmail());
	msg.setTo("easit4all@gmail.com");
	msg.setSubject(data.getSubject());
	msg.setText("name:" + data.getFirstName() + " " + data.getLastName() + ",\n\n" + "Subject:" + data.getSubject() + "\n\n" + data.getText());

	try {
	    this.mailSender.send(msg);
	    return true;
	} catch (Exception ex) {
	    logger.error(ex.getMessage());
	}
	return false;
    }
}
