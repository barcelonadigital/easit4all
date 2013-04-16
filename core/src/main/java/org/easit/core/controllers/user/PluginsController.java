package org.easit.core.controllers.user;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.easit.core.plugins.PluginManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PluginsController {

    @Inject
    private PluginManager pluginManager;

    @RequestMapping(value = "/user/plugins", method = RequestMethod.GET)
    public String showPluginsPage(Principal currentUser, Model model) {
	model.addAttribute(new PluginsForm());
	model.addAttribute("isNews", pluginManager.isLoaded("news"));
	model.addAttribute("isFavourites", pluginManager.isLoaded("favourites"));
	model.addAttribute("isSensors", pluginManager.isLoaded("sensors"));
	return "plugins";
    }

    @RequestMapping(value = "/user/plugins", method = RequestMethod.POST)
    public String modifyPlugins(@Valid PluginsForm pluginsForm, BindingResult binding, Principal currentUser, Model model, HttpSession session, HttpServletRequest request) {
	if (binding.hasErrors()) {
	    return null;
	}
	// Favourites Plugin
	if (pluginsForm.isFavourites() && !(pluginManager.isLoaded("favourites"))) {
	    pluginManager.load("favourites");
	}
	if (!(pluginsForm.isFavourites()) && pluginManager.isLoaded("favourites")) {
	    pluginManager.unload("favourites");
	}

	// News Plugin
	if (pluginsForm.isNews() && !(pluginManager.isLoaded("news"))) {
	    pluginManager.load("news");
	}
	if (!(pluginsForm.isNews()) && (pluginManager.isLoaded("news"))) {
	    pluginManager.unload("news");
	}

	// Cosm Sensors Plugin
	if (pluginsForm.isSensors() && !(pluginManager.isLoaded("cosmSensors"))) {
	    pluginManager.load("cosmSensors");
	}
	if (!(pluginsForm.isSensors()) && pluginManager.isLoaded("cosmSensors")) {
	    pluginManager.unload("cosmSensors");
	}

	return "redirect:/user";
    }

}
