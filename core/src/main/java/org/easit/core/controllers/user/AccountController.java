package org.easit.core.controllers.user;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.easit.core.controllers.signup.SignupForm;
import org.easit.dao.EasitAccountDao;
import org.easit.dao.model.EasitAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private ConnectionRepository connectionRepository;
    private EasitAccountDao accountRepository;
    private PasswordEncoder passwordEncoder;

    @Inject
    public AccountController(EasitAccountDao accountRepository, PasswordEncoder pass) {
	this.accountRepository = accountRepository;
	this.passwordEncoder = pass;
    }

    @RequestMapping(value = "/user/account", method = RequestMethod.GET)
    public String showAccount(Principal currentUser, Model model) {
	EasitAccount res = accountRepository.findAccountByUsername(currentUser.getName());
	model.addAttribute("signupForm", new SignupForm());
	model.addAttribute("acc", res);
	return "account";
    }

    @RequestMapping(value = "/user/account", method = RequestMethod.POST)
    public String signup(@Valid SignupForm form, BindingResult formBinding, Principal currentUser, HttpServletRequest request) {
	if (formBinding.hasErrors()) {
	    return null;
	}

	EasitAccount current = (EasitAccount) request.getSession().getAttribute("user");
	EasitAccount account;
	account = updateAccount(currentUser, form, formBinding);
	account.setId(current.getId());
	if (account != null) {
	    request.getSession().removeAttribute("user");
	    request.getSession().setAttribute("user", account);
	    return "redirect:/";
	}
	return "redirect:/user/account";
    }

    // internal helpers

    private EasitAccount updateAccount(Principal currentUser, SignupForm form, BindingResult formBinding) {
	EasitAccount account = new EasitAccount(currentUser.getName(), form.getPassword(), form.getFirstName(), form.getLastName(), form.getEmail(), "");
	try {
	    accountRepository.changeAccountSettings(account, passwordEncoder);
	    return account;
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return null;

    }
}