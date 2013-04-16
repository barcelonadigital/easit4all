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
import java.util.List;

import javax.inject.Inject;

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FacebookFeedController {

    private final Facebook facebook;

    @Inject
    public FacebookFeedController(Facebook facebook) {
	this.facebook = facebook;

    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    /*
     * Show own wall
     */
    @RequestMapping(value = "/facebook/wall", method = RequestMethod.GET)
    public String showWall(Model model, String offset, String size, String ro) {
	int int_offset = 0;
	int listSize = 0;

	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().getFeed(0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("feed", facebook.feedOperations().getFeed(int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/wall";
    }

    /*
     * Show feed
     */
    @RequestMapping(value = "/facebook/feed", method = RequestMethod.GET)
    public String showFeed(Model model, String offset, String size, String ro, String feed) {
	List<Post> postList = new ArrayList<Post>();
	int int_offset = 0;
	int listSize = 0;
	int c = 0;
	int c2 = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	if (size != null) {
	    listSize = Integer.valueOf(size);
	} else {
	    listSize = facebook.feedOperations().getHomeFeed(0, PSMetadata.MAX_RESULTS_SEARCH).size();
	}
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	model.addAttribute("feed", facebook.feedOperations().getHomeFeed(int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/feed";
    }

    /*
     * Update the user status from 'feed'
     */
    @RequestMapping(value = "/facebook/feed", method = RequestMethod.POST)
    public String postUpdate(String message, String uri) {
	facebook.feedOperations().updateStatus(message);
	return "redirect:/" + uri;
    }
}
