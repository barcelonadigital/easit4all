package org.easit.core.controllers.facebook;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Reference;
import org.springframework.social.facebook.api.Video;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookUserController {

    private final Facebook facebook;

    @Inject
    public FacebookUserController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    /*
     * Show friend's wall
     */
    @RequestMapping(value = "/facebook/friends/{userId}/wall", method = RequestMethod.GET)
    public String showFeed(@PathVariable("userId") String userId, Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.feedOperations().getFeed(userId).size();

	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	model.addAttribute("feed", facebook.feedOperations().getFeed(userId, int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/friend/wall";
    }

    /*
     * Post to friend's wall
     */
    @RequestMapping(value = "/facebook/friends/{userId}/wall", method = RequestMethod.POST)
    public String postUpdate(@PathVariable("userId") String userId, String message) {
	facebook.feedOperations().post(userId, message);
	return "redirect:/facebook/friends/{userId}/wall";
    }

    /*
     * Show friend's profile
     */
    @RequestMapping(value = "/facebook/friends/{userId}/profile", method = RequestMethod.GET)
    public String showProfile(@PathVariable("userId") String userId, Model model) {
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	return "facebook/friend/profile";
    }

    /*
     * Show friend's albums
     */
    @RequestMapping(value = "/facebook/friends/{userId}/albums", method = RequestMethod.GET)
    public String showAlbums(@PathVariable("userId") String userId, Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	List<Album> albumList = facebook.mediaOperations().getAlbums(userId);

	listSize = albumList.size();

	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	model.addAttribute("falbums", albumList);
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/friend/albums";
    }

    /*
     * Show content of album
     */
    @RequestMapping(value = "/facebook/friends/{userId}/albums/{albumId}", method = RequestMethod.GET)
    public String showAlbum(@PathVariable("userId") String userId, @PathVariable("albumId") String albumId, Model model) {
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	model.addAttribute("falbum", facebook.mediaOperations().getAlbum(albumId));
	model.addAttribute("fphotos", facebook.mediaOperations().getPhotos(albumId));
	return "facebook/friend/albums/album";
    }

    /*
     * Show friend's videos
     */
    @RequestMapping(value = "/facebook/friends/{userId}/albums/videos", method = RequestMethod.GET)
    public String showVideos(@PathVariable("userId") String userId, Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	List<Video> videoList = facebook.mediaOperations().getVideos(userId);

	listSize = videoList.size();

	model.addAttribute("fvideos", videoList);
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/friend/videos";
    }

    /*
     * Show the selected video
     */
    @RequestMapping(value = "/facebook/friends/{userId}/albums/videos/{videoId}", method = RequestMethod.GET)
    public String showVideo(@PathVariable("videoId") String videoId, @PathVariable("userId") String userId, Model model) {
	model.addAttribute(new UploadItem());
	model.addAttribute("fvideo", facebook.mediaOperations().getVideo(videoId));
	// model.addAttribute("comments",
	// facebook.commentOperations().getComments(videoId));
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	return "facebook/friend/videos/video";
    }

    /*
     * Show friends
     */
    @RequestMapping(value = "/facebook/friends/{userId}/friends", method = RequestMethod.GET)
    public String showFriends(@PathVariable("userId") String userId, Model model) {
	List<Reference> users = facebook.friendOperations().getFriends(userId);
	model.addAttribute("fuser", facebook.userOperations().getUserProfile(userId));
	model.addAttribute("users", users);
	model.addAttribute("areFriends", friendConnection(users, userId));
	return "facebook/friend/users";
    }

    private List<Boolean> friendConnection(List<Reference> users, String userId) {
	List<Boolean> areFriends = new ArrayList<Boolean>();
	List<String> myFriends = facebook.friendOperations().getFriendIds();
	for (Reference user : users) {
	    Boolean isFriend = false;
	    for (String friend : myFriends) {
		if (friend.equals(user.getId()) || (friend.equals(userId))) {
		    isFriend = true;
		    break;
		}
	    }
	    areFriends.add(isFriend);
	}
	return areFriends;
    }
}
