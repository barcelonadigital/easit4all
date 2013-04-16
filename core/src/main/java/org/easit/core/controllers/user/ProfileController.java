package org.easit.core.controllers.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.easit.dao.EasitAccountDao;
import org.easit.dao.EasitProfileDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.EasitProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ProfileController {

    private EasitAccountDao accountRepository;
    private EasitProfileDao profileRepository;

    @Inject
    public ProfileController(EasitAccountDao accountRepository, EasitProfileDao profileRepository) {
	this.accountRepository = accountRepository;
	this.profileRepository = profileRepository;
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    public String showProfile(Model model, Principal currentUser) {
	List<String> skillLevel = new ArrayList<String>();
	skillLevel.add("none");
	skillLevel.add("poor");
	skillLevel.add("medium");
	skillLevel.add("high");
	EasitAccount res = accountRepository.findAccountByUsername(currentUser.getName());
	model.addAttribute(new ProfileForm());
	model.addAttribute("profile", profileRepository.getProfileByUserId(res.getId()));
	model.addAttribute("skillLevel", skillLevel);
	return "profile";
    }

    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public String updateProfile(@Valid ProfileForm form, BindingResult formBinding, Principal currentUser, HttpSession session) {
	if (formBinding.hasErrors()) {
	    return null;
	}
	EasitAccount res = accountRepository.findAccountByUsername(currentUser.getName());
	EasitProfile profile = new EasitProfile(res.getId(), form.getAge(), form.getTechnologySkills(), form.getVisualSkills(), form.getHearingSkills());
	profileRepository.updateProfile(profile);
	session.setAttribute("techSkills", form.getTechnologySkills());
	return "redirect:/user";
    }

}
