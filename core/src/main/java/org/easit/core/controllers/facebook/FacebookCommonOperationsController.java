package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookCommonOperationsController {

    private final Facebook facebook;

    @Inject
    private FacebookCommonOperationsController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    /*
     * Publish a post on a social object
     */
    @RequestMapping(value = "facebook/post/{objectId}", method = RequestMethod.POST)
    public String post(@PathVariable("objectId") String objectId, String message, String uri) {
	facebook.feedOperations().post(objectId, message);
	return "redirect:/" + uri;
    }

    /*
     * Like an object
     */
    /*
     * @RequestMapping(value = "facebook/like/{objectId}", method =
     * RequestMethod.POST) public String likeObject(@PathVariable("objectId")
     * String objectId, String uri) { facebook.likeOperations().like(objectId);
     * return "redirect:/" + uri; }
     */

    @RequestMapping(value = "facebook/likeUnlike/{objectId}", method = RequestMethod.POST)
    public String likeUnlikeObject(@PathVariable("objectId") String objectId, String uri) {
	List<Reference> likes = facebook.likeOperations().getLikes(objectId);
	String userId = facebook.userOperations().getUserProfile().getId();
	for (Reference person : likes) {
	    if (person.getId().equals(userId)) {
		facebook.likeOperations().unlike(objectId);
		return "redirect:/" + uri;
	    }
	}
	facebook.likeOperations().like(objectId);
	return "redirect:/" + uri;
    }

    /*
     * Delete an object
     */
    @RequestMapping(value = "facebook/delete/{objectId}", method = RequestMethod.POST)
    public String deleteObject(@PathVariable("objectId") String objectId, String uri) {
	facebook.commentOperations().deleteComment(objectId);
	return "redirect:/" + uri;
    }

    /*
     * Comment a social object
     */
    @RequestMapping(value = "facebook/comment/{objectId}", method = RequestMethod.POST)
    public String commentObject(@PathVariable("objectId") String objectId, String comment, String uri) {
	facebook.commentOperations().addComment(objectId, comment);
	return "redirect:/" + uri;
    }

    @RequestMapping(value = "facebook/{groupId}/members/{userId}", method = RequestMethod.POST)
    public String deleteMember(@PathVariable("groupId") String groupId, @PathVariable("userId") String userId, String uri) {
	facebook.delete(groupId + "/members/" + userId);
	return "redirect:/" + uri;
    }

}
