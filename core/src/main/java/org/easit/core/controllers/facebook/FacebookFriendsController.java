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
package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.easit.dao.model.PSMetadata;
import org.springframework.core.env.Environment;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookFriendsController {

    private final Facebook facebook;

    @Inject
    private Environment environment;

    @Inject
    public FacebookFriendsController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    // currently this algorithm search, for each user, the lists where he/she
    // appears. this could be done once to optimize time!
    private List<Reference> friendListsPerUser(List<Reference> friendLists, String friend) {
	// Copy friendLists values into other List and remove "All" friendList:
	List<Reference> fl = new ArrayList<Reference>();
	fl.addAll(friendLists);
	fl.remove(fl.size() - 1);

	List<Reference> lists = new ArrayList<Reference>();
	for (Reference friendList : fl) {
	    List<Reference> members = facebook.friendOperations().getFriendListMembers(friendList.getId());
	    for (Reference member : members) {
		if (member.getId().equals(friend))
		    lists.add(friendList);
	    }
	}
	return lists;
    }

    /*
     * Show friends view
     */
    @RequestMapping(value = "/facebook/friends", method = RequestMethod.GET)
    public String showFriends(Model model, String friendListId, String offset, String size, HttpSession session) {
	int resultLimit = 0;
	int resultOffset = 0;
	int listSize = 0;
	int int_offset = 0;
	List<Reference> friendList = new ArrayList<Reference>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	String currentListName = null;
	String currentListId = null;
	List<Reference> friends = null;
	List<Reference> friendLists = facebook.friendOperations().getFriendLists();
	Reference allList = new Reference("1", "All");
	friendLists.add(allList);
	if (friendListId == null || friendListId.equals("1") || friendListId.equals("All")) {
	    currentListName = "All";
	    currentListId = "1";

	    if (size != null) {
		listSize = Integer.valueOf(size);
	    } else {
		listSize = facebook.friendOperations().getFriends().size();
	    }
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
	    friends = facebook.friendOperations().getFriends().subList(resultOffset, resultLimit);

	} else {

	    currentListName = facebook.friendOperations().getFriendList(friendListId).getName();
	    currentListId = facebook.friendOperations().getFriendList(friendListId).getId();

	    if (size != null) {
		listSize = Integer.valueOf(size);
	    } else {
		listSize = facebook.friendOperations().getFriendListMembers(friendListId).size();
	    }
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
	    friends = facebook.friendOperations().getFriendListMembers(friendListId).subList(resultOffset, resultLimit);
	}
	// only show it if the user has very high technological skills
	if (session.getAttribute("techSkills").equals(3)) {
	    List<List> listsPerUser = new ArrayList<List>();
	    for (Reference friend : friends) {
		listsPerUser.add(friendListsPerUser(friendLists, friend.getId()));
	    }
	    model.addAttribute("listsPerUser", listsPerUser);
	}
	model.addAttribute("currentListName", currentListName);
	model.addAttribute("currentListId", currentListId);
	model.addAttribute("friendLists", friendLists);
	model.addAttribute("friends", friends);
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/friends";
    }

    /*
     * Show a list with friend lists. No method with offset/limit
     */
    @RequestMapping(value = "/facebook/friends/lists", method = RequestMethod.GET)
    public String showFriendLists(Model model, String offset) {
	int resultLimit = 0;
	int resultOffset = 0;
	int listSize = 0;

	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.friendOperations().getFriendLists().size();

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

	model.addAttribute("friendLists", facebook.friendOperations().getFriendLists().subList(resultOffset, resultLimit));
	model.addAttribute("offset", resultOffset);
	model.addAttribute("pageSize", listSize);
	return "facebook/friends/lists";
    }

    /*
     * Create a new custom friend list
     */
    @RequestMapping(value = "/facebook/friends/lists/create", method = RequestMethod.POST)
    public String createFriendList(String newFriendList) {
	facebook.friendOperations().createFriendList(newFriendList);
	return "redirect:/facebook/friends/lists";
    }

    /*
     * Delete a custom friend list
     */
    @RequestMapping(value = "/facebook/friends/lists/delete/{listId}", method = RequestMethod.POST)
    public String deleteFriendList(@PathVariable("listId") String listId) {
	facebook.friendOperations().deleteFriendList(listId);
	return "redirect:/facebook/friends/lists";
    }

    /*
     * add a friend to 'web_name' (aka Atei or Asit) list. First, create the
     * list if it doesn't exist.
     */
    @RequestMapping(value = "/facebook/addFriend/{userId}", method = RequestMethod.POST)
    public String addFriend(@PathVariable("userId") String userId, HttpSession session) {
	String web_name = session.getAttribute("web_name").toString();
	String listId = null;
	List<Reference> fl = facebook.friendOperations().getFriendLists();
	for (Reference fl_exists : fl) {
	    if (fl_exists.getName().equals(web_name)) {
		listId = fl_exists.getId();
		break;
	    }
	}
	if (listId == null) {
	    listId = facebook.friendOperations().createFriendList(web_name);
	}
	facebook.friendOperations().addToFriendList(listId, userId);
	return "redirect:/facebook/friends";
    }

    /*
     * Add / Remove friend from a list
     */
    @RequestMapping(value = "/facebook/manageLists/{friendId}", method = RequestMethod.POST)
    public String manageLists(@PathVariable("friendId") String friendId, String fListId[], HttpServletRequest request) {
	String[] listids = request.getParameterValues("fListId[]");
	List<Reference> currentLists = new ArrayList<Reference>();
	List<String> newListIds = new ArrayList<String>();
	newListIds = Arrays.asList(listids);
	currentLists = friendListsPerUser(facebook.friendOperations().getFriendLists(), friendId);
	for (Reference current : currentLists) {
	    if (!(newListIds.contains(current.getId())))
		facebook.friendOperations().removeFromFriendList(current.getId(), friendId);
	}
	for (String listId : newListIds) {
	    if (!(currentLists.contains(listId)))
		facebook.friendOperations().addToFriendList(listId, friendId);
	}

	return "redirect:/facebook/friends";
    }

    @RequestMapping(value = "facebook/manageRequest/{friendId}", method = RequestMethod.POST)
    public String manageFriendshipRequest(@PathVariable("friendId") String friendId, String uri, HttpServletRequest request) {

	// Builds the url to redirect the user to the friend's wall. This app is
	// now using the Referrer Url instead.
	/*
	 * StringBuilder uriBuilder = new StringBuilder();
	 * uriBuilder.append("http://");
	 * uriBuilder.append(request.getSession().getAttribute("host"));
	 * uriBuilder.append("/");
	 * uriBuilder.append(request.getSession().getAttribute("web_name"));
	 * uriBuilder.append("/facebook/friends/"); uriBuilder.append(friendId);
	 * uriBuilder.append("/wall");
	 */

	return "redirect:" + facebook.friendOperations().manageFriendshipRequest(friendId, environment.getProperty("facebook.clientId"), request.getHeader("referer"));
    }

}
