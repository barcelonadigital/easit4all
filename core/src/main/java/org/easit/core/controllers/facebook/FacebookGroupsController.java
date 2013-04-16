package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.GroupMemberReference;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookGroupsController {

    private final Facebook facebook;

    @Inject
    public FacebookGroupsController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    @RequestMapping(value = "facebook/groups", method = RequestMethod.GET)
    public String showGroups(Model model, String offset) {
	int resultOffset = 0;
	int resultLimit = 0;
	int listSize = 0;
	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	// List<Group> groups = facebook.fetchConnections("me", "groups",
	// Group.class);
	listSize = facebook.groupOperations().getGroups().size();

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

	List<Group> groups = facebook.groupOperations().getGroups().subList(resultOffset, resultLimit);
	List<String> ownersId = new ArrayList<String>();
	for (Group g : groups) {
	    ownersId.add(facebook.groupOperations().getGroup(g.getId()).getOwner().getId());
	}
	model.addAttribute("groups", groups);
	model.addAttribute("offset", resultOffset);
	model.addAttribute("pageSize", listSize);
	model.addAttribute("userId", facebook.userOperations().getUserProfile().getId());
	model.addAttribute("ownersList", ownersId);
	return "facebook/groups";
    }

    /*
     * @RequestMapping(value="facebook/groups/search", method =
     * RequestMethod.GET) public String searchGroups(@RequestParam("query")
     * String query, Model model) { List<Group> groupList =
     * facebook.groupOperations().search(query); ArrayList<Integer> sizeGroup =
     * new ArrayList<Integer>(); for(Group group:groupList) {
     * sizeGroup.add(facebook
     * .groupOperations().getMembers(group.getId()).size()); }
     * model.addAttribute("groups", groupList); model.addAttribute("nMembers",
     * sizeGroup); return "facebook/groups"; }
     */


    /*
     * Show group wall
     */
    @RequestMapping(value = "facebook/groups/{groupId}/wall", method = RequestMethod.GET)
    public String showGroupWall(@PathVariable("groupId") String groupId, Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.feedOperations().getFeed(groupId).size();

	model.addAttribute("group", facebook.groupOperations().getGroup(groupId));
	model.addAttribute("feed", facebook.feedOperations().getFeed(groupId, int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/group/wall";
    }

    /*
     * Show group members
     */

    @RequestMapping(value = "facebook/groups/{groupId}/members", method = RequestMethod.GET)
    public String showGroupMembers(@PathVariable("groupId") String groupId, Model model, String offset) {
	int resultOffset = 0;
	int resultLimit = 0;
	int listSize = 0;
	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.groupOperations().getMembers(groupId).size();

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

	List<GroupMemberReference> users = facebook.groupOperations().getMembers(groupId).subList(resultOffset, resultLimit);
	model.addAttribute("users", users);
	model.addAttribute("group", facebook.groupOperations().getGroup(groupId));
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("areFriends", friendConnection(users, facebook.userOperations().getUserProfile().getId()));
	model.addAttribute("offset", resultOffset);
	model.addAttribute("pageSize", listSize);
	return "facebook/group/members";
    }

    public List<Boolean> friendConnection(List<GroupMemberReference> users, String userId) {
	List<Boolean> areFriends = new ArrayList<Boolean>();
	List<String> myFriends = facebook.friendOperations().getFriendIds();
	for (Reference user : users) {
	    Boolean isFriend = false;
	    for (String friend : myFriends) {
		if (friend.equals(user.getId()) || userId.equals(user.getId())) {
		    isFriend = true;
		    break;
		}
	    }
	    areFriends.add(isFriend);
	}
	return areFriends;
    }

}
