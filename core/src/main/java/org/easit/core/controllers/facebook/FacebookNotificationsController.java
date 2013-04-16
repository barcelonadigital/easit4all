package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FriendRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookNotificationsController {

    private final Facebook facebook;

    @Inject
    private FacebookNotificationsController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    @RequestMapping(value = "facebook/notifications", method = RequestMethod.GET)
    public String getFriendRequests(Model model, String offset) {
	int resultOffset = 0;
	int resultLimit = 0;
	int listSize = 0;
	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.notificationOperations().getFriendRequests().size();

	if (listSize <= int_offset + PSMetadata.FACEBOOK_LIMIT_RESULT) {
	    resultLimit = listSize;
	} else {
	    resultLimit = int_offset + PSMetadata.FACEBOOK_LIMIT_RESULT;
	}
	if (listSize <= int_offset) {
	    resultOffset = listSize;
	} else {
	    resultOffset = int_offset;
	}

	// List<FriendRequest> fr = facebook.fetchConnections("me",
	// "friendrequests", FriendRequest.class);
	List<FriendRequest> fr = facebook.notificationOperations().getFriendRequests().subList(resultOffset, resultLimit);
	model.addAttribute("notif", fr);
	model.addAttribute("offset", resultOffset);
	model.addAttribute("pageSize", listSize);
	return "facebook/notifications";
    }
}
