package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Event;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Group;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacebookSearchController {

    private final Facebook facebook;

    @Inject
    public FacebookSearchController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    /*
     * General search with 3 results for each section
     */
    @RequestMapping(value = "/facebook/search", method = RequestMethod.GET)
    public String search(@RequestParam("query") String query, Model model, String offset, String limit, HttpSession session) {
	List<Reference> users = facebook.userOperations().search(query);// Searches
									// for
									// users
	int usersLimit = 3;
	if (users.size() < usersLimit) {
	    usersLimit = users.size();
	}
	users = users.subList(0, usersLimit);
	List<Boolean> areFriends = new ArrayList<Boolean>();
	areFriends = friendConnection(users);

	// only show it if the user has very high technological skills
	if (session.getAttribute("techSkills").equals(3)) {
	    List<Group> groupList = facebook.groupOperations().search(query, 0, 3);
	    ArrayList<Integer> sizeGroup = new ArrayList<Integer>();
	    for (Group group : groupList) {
		sizeGroup.add(facebook.groupOperations().getMembers(group.getId()).size());
	    }
	    model.addAttribute("groups", groupList); // Searches for groups
	    model.addAttribute("nMembers", sizeGroup);
	    model.addAttribute("areFriends", areFriends);
	}
	model.addAttribute("users", users);
	model.addAttribute("events", facebook.eventOperations().search(query, 0, 3)); // Searches for events
	model.addAttribute("myfeed", facebook.feedOperations().searchHomeFeed(query, 0, 3)); // Searches for posts in 'Feed'
	/*
	 * model.addAttribute("publicfeed",
	 * facebook.feedOperations().searchPublicFeed(query, 0, 3)); //Searches
	 * for public feeds //Errors reading 'comments' on types LinkPost,
	 * StatusPost...
	 */
	model.addAttribute("mywall", facebook.feedOperations().searchUserFeed(query, 0, 3)); // Searches for posts in user's 'Wall'
	model.addAttribute("searchfilter", "general");
	model.addAttribute("noresult", "No results found for your search criteria");
	// model.addAttribute(facebook.feedOperations().searchUserFeed("userId",
	// query)); //Searches for posts in "userId"'s Wall.*/
	return "facebook/search";
    }

    /*
     * Search for events
     */
    @RequestMapping(value = "/facebook/search/events", method = RequestMethod.GET)
    public String searchEvents(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int int_offset = 0;
	int listSize = 0;
	List<Event> eventList = new ArrayList<Event>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.eventOperations().search(query, 0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}

	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Event e = facebook.eventOperations().search(query, real_offset + c2, 1).get(0);
		eventList.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }

	}
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	model.addAttribute("pageSize", listSize);
	model.addAttribute("searchfilter", "events");
	model.addAttribute("events", eventList);

	return "facebook/search/filter";
    }

    /*
     * Search for groups
     */
    @RequestMapping(value = "/facebook/search/groups", method = RequestMethod.GET)
    public String searchGroups(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int int_offset = 0;
	int listSize = 0;
	List<Group> groupList = new ArrayList<Group>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.groupOperations().search(query, 0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}

	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Group e = facebook.groupOperations().search(query, real_offset + c2, 1).get(0);
		groupList.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }

	}
	model.addAttribute("searchfilter", "groups");
	ArrayList<Integer> sizeGroup = new ArrayList<Integer>();
	for (Group group : groupList) {
	    sizeGroup.add(facebook.groupOperations().getMembers(group.getId()).size());
	}
	model.addAttribute("groups", groupList);
	model.addAttribute("nMembers", sizeGroup);
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	return "facebook/search/filter";
    }

    /*
     * Search for users
     */
    @RequestMapping(value = "/facebook/search/users", method = RequestMethod.GET)
    public String searchUsers(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int resultOffset = 0;
	int resultLimit = 0;
	int listSize = 0;
	int int_offset = 0;
	List<Reference> users = new ArrayList<Reference>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.userOperations().search(query).size();
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
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}

	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Reference e = (Reference) facebook.userOperations().search(query).subList(real_offset + c2, real_offset + c2 + 1).get(0);
		users.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }
	}

	model.addAttribute("searchfilter", "users");
	model.addAttribute("users", users);
	List<Boolean> areFriends = new ArrayList<Boolean>();
	areFriends = friendConnection(users);
	model.addAttribute("areFriends", areFriends);
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	return "facebook/search/filter";
    }

    /*
     * Search for posts in the user's authenticated feed.
     */
    @RequestMapping(value = "/facebook/search/myfeed", method = RequestMethod.GET)
    public String searchMyFeed(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int int_offset = 0;
	int listSize = 0;
	List<Post> postList = new ArrayList<Post>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().searchHomeFeed(query, 0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}
	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Post e = facebook.feedOperations().searchHomeFeed(query, real_offset + c2, 1).get(0);
		postList.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }

	}
	model.addAttribute("searchfilter", "myfeed");
	model.addAttribute("myfeed", postList);
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	return "facebook/search/filter";
    }

    /*
     * Search for public feeds. Errors reading 'comments' on types LinkPost,
     * StatusPost...
     */
    @RequestMapping(value = "/facebook/search/publicfeed", method = RequestMethod.GET)
    public String searchPublicFeed(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int int_offset = 0;
	int listSize = 0;
	List<Post> postList = new ArrayList<Post>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().searchPublicFeed(query, 0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}
	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Post e = facebook.feedOperations().searchPublicFeed(query, real_offset + c2, 1).get(0);
		postList.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }

	}
	model.addAttribute("searchfilter", "publicfeed");
	model.addAttribute("myfeed", postList);
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	return "facebook/search/filter";
    }

    /*
     * Search for posts in the user's authenticated wall.
     */
    @RequestMapping(value = "/facebook/search/mywall", method = RequestMethod.GET)
    public String searchMyWall(@RequestParam("query") String query, Model model, String offset, String size, String ro) {
	int real_offset = 0;
	int int_offset = 0;
	int listSize = 0;
	List<Post> postList = new ArrayList<Post>();
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().searchUserFeed(query, 0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	if (ro != null) {
	    real_offset = Integer.valueOf(ro);
	}
	int c = 0;
	int c2 = 0;
	while (c < PSMetadata.FACEBOOK_LIMIT_RESULT && int_offset + c < listSize) {
	    try {
		Post e = facebook.feedOperations().searchUserFeed(query, real_offset + c2, 1).get(0);
		postList.add(e);
		c++;
	    } catch (IndexOutOfBoundsException e) {
		// Error if the event is null
	    } finally {
		c2++;
	    }

	}
	model.addAttribute("searchfilter", "mywall");
	model.addAttribute("myfeed", postList);
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", int_offset);
	model.addAttribute("realOffset", real_offset + c2);
	return "facebook/search/filter";
    }

    /*
     * Shows which users are in the authenticated user's friend list.
     */
    private List<Boolean> friendConnection(List<Reference> users) {
	List<Boolean> areFriends = new ArrayList<Boolean>();
	List<String> myFriends = facebook.friendOperations().getFriendIds();
	for (Reference user : users) {
	    Boolean isFriend = false;
	    for (String friend : myFriends) {
		if (friend.equals(user.getId())) {
		    isFriend = true;
		    break;
		}
	    }
	    areFriends.add(isFriend);
	}
	return areFriends;
    }

}
