package org.easit.core.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserSettingsController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String home(Model model) {
	return "redirect:/user/account";
    }
}
