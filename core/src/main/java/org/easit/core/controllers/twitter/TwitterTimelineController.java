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
package org.easit.core.controllers.twitter;

import java.security.Principal;

import javax.inject.Inject;
import javax.validation.Valid;

import org.easit.core.controllers.facebook.UploadItem;
import org.easit.dao.model.PSMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterTimelineController {

    private final Twitter twitter;

    @Inject
    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterTimelineController(Twitter twitter) {
	this.twitter = twitter;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/twitter";
    }

    @RequestMapping(value = "/twitter/timeline", method = RequestMethod.GET)
    public String showTimeline(Model model, String offset) {
	if (offset == null) {
	    offset = "1";
	}
	/*
	 * Connection<Twitter> connection =
	 * connectionRepository.findPrimaryConnection(Twitter.class); if
	 * (connection == null) { return "redirect:/connect/twitter"; }
	 */
	model.addAttribute(new UploadItem());
	return showTimeline("Home", model, offset);
    }

    @RequestMapping(value = "/twitter/timeline/{timelineType}", method = RequestMethod.GET)
    public String showTimeline(@PathVariable("timelineType") String timelineType, Model model, String offset) {
	int int_page = 1;
	if (offset != null) {
	    int_page = Integer.valueOf(offset);
	}
	if (timelineType.equals("Home")) {
	    model.addAttribute("timeline", twitter.timelineOperations().getHomeTimeline(int_page, PSMetadata.TWITTER_LIMIT_RESULT));
	    model.addAttribute("pageSize", twitter.timelineOperations().getHomeTimeline().size());
	    /*
	     * //Public timeline not available due to its removal from twitter
	     * service. Fix sublists when becomes available again!! } else if
	     * (timelineType.equals("Public")) { model.addAttribute("timeline",
	     * twitter.timelineOperations() .getPublicTimeline());
	     */

	} else if (timelineType.equals("User")) {
	    model.addAttribute("timeline", twitter.timelineOperations().getUserTimeline(int_page, PSMetadata.TWITTER_LIMIT_RESULT));
	    model.addAttribute("pageSize", twitter.timelineOperations().getUserTimeline().size());
	} else if (timelineType.equals("Mentions")) {
	    model.addAttribute("timeline", twitter.timelineOperations().getMentions(int_page, PSMetadata.TWITTER_LIMIT_RESULT));
	    model.addAttribute("pageSize", twitter.timelineOperations().getMentions().size());
	} else if (timelineType.equals("Favorites")) {
	    model.addAttribute("timeline", twitter.timelineOperations().getFavorites(int_page, PSMetadata.TWITTER_LIMIT_RESULT));
	    model.addAttribute("pageSize", twitter.timelineOperations().getFavorites().size());
	}
	model.addAttribute("timelineName", timelineType);
	model.addAttribute(new UploadItem());
	model.addAttribute("offset", int_page);
	return "twitter/timeline";
    }

    @RequestMapping(value = "/twitter/tweet", method = RequestMethod.POST)
    public String postTweet(@Valid UploadItem uploadItem, BindingResult result) {

	if (result.hasErrors()) {
	    return null;
	}
	String message = uploadItem.getCaption();
	Resource photo = null;
	// StatusDetails details = null;
	if (!uploadItem.getFileData().isEmpty()) {
	    final String fileName = uploadItem.getFileData().getOriginalFilename();
	    photo = new ByteArrayResource(uploadItem.getFileData().getBytes()) {
		public String getFilename() throws IllegalStateException {
		    return fileName;
		};
	    };
	    twitter.timelineOperations().updateStatus(message, photo);
	    return "redirect:/twitter";
	} else {
	    twitter.timelineOperations().updateStatus(message);
	    return "redirect:/twitter";
	}

    }

}