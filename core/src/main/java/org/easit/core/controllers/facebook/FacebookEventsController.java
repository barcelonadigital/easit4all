package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookEventsController {

    private final Facebook facebook;

    @Inject
    public FacebookEventsController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    /*
     * Show all events
     */
    @RequestMapping(value = "facebook/events", method = RequestMethod.GET)
    public String showEvents(Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	listSize = facebook.eventOperations().getInvitations().size();

	model.addAttribute("events", facebook.eventOperations().getInvitations(int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/events";
    }

    /*
     * Show event wall
     */
    @RequestMapping(value = "facebook/events/{eventId}/wall", method = RequestMethod.GET)
    public String showEventWall(@PathVariable("eventId") String eventId, Model model, String rsvpStatus, String offset, String size, String ro) {
	int real_offset = 0;
	int resultOffset = 0;
	int resultLimit = 0;
	int listSize = 0;
	int int_offset = 0;
	List<Post> postList = new ArrayList<Post>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().getFeed(eventId).size();
	}
	/*
	 * if (listSize <= int_offset+PSMetadata.FACEBOOK_LIMIT_RESULT) {
	 * resultLimit = listSize; } else { resultLimit =
	 * int_offset+PSMetadata.FACEBOOK_LIMIT_RESULT; } if (listSize <=
	 * int_offset) { resultOffset = listSize; } else { resultOffset =
	 * int_offset; } if(ro != null) { real_offset = Integer.valueOf(ro); }
	 * 
	 * int c = 0; int c2 = 0; while(c < PSMetadata.FACEBOOK_LIMIT_RESULT &&
	 * int_offset + c < listSize) { try{ Post e =
	 * facebook.feedOperations().getFeed(eventId, real_offset + c2,
	 * 1).get(0); postList.add(e); c++; } catch(IndexOutOfBoundsException e)
	 * { //Error if the event is null } finally { c2++; } }
	 */

	model.addAttribute("event", facebook.eventOperations().getEvent(eventId));
	model.addAttribute("feed", facebook.feedOperations().getFeed(eventId, int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	// model.addAttribute("realOffset", real_offset + c2);
	return "facebook/event/wall";
    }

    /*
     * Show event guests
     */
    @RequestMapping(value = "facebook/events/{eventId}/guests", method = RequestMethod.GET)
    public String showEventGuests(@PathVariable("eventId") String eventId, Model model) {
	facebook.eventOperations().getInvited(eventId); // Get invited
	facebook.eventOperations().getAttending(eventId); // Get attending
	facebook.eventOperations().getDeclined(eventId); // Get declined
	facebook.eventOperations().getMaybeAttending(eventId); // Get maybe
							       // attending
	facebook.eventOperations().getNoReplies(eventId); // Get no replies
	return "facebook/event/guests";
    }

    /*
     * Accept an invitation to an event.
     */
    @RequestMapping(value = "facebook/events/joinEvent/{eventId}", method = RequestMethod.POST)
    public String joinToEvent(@PathVariable("eventId") String eventId, String uri) {
	facebook.eventOperations().acceptInvitation(eventId);
	return "redirect:/facebook/events/{eventId}/wall";
    }

    /*
     * Decline an invitation to an event.
     */
    @RequestMapping(value = "facebook/events/declineEvent/{eventId}", method = RequestMethod.POST)
    public String declineEvent(@PathVariable("eventId") String eventId, String uri) {
	facebook.eventOperations().declineInvitation(eventId);
	return "redirect:/facebook/events/{eventId}/wall";
    }

    /*
     * RSVPs to an event with a maybe.
     */
    @RequestMapping(value = "facebook/events/maybeEvent/{eventId}", method = RequestMethod.POST)
    public String maybeEvent(@PathVariable("eventId") String eventId, String uri) {
	facebook.eventOperations().maybeInvitation(eventId);
	return "redirect:/facebook/events/{eventId}/wall";
    }

}
