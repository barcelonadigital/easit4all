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

import org.easit.dao.model.PSMetadata;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.ServerOverloadedException;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterTrendsController {

    private final Twitter twitter;

    @Inject
    private ConnectionRepository connectionRepository;

    @Inject
    public TwitterTrendsController(Twitter twitter) {
	this.twitter = twitter;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/twitter";
    }

    @ExceptionHandler(ServerOverloadedException.class)
    public String handleServerOverloadedException(Principal currentUser) {
	return "twitter/serverOverloaded";
    }

    @RequestMapping(value = "/twitter/dailyTrends", method = RequestMethod.GET)
    public String showTrends(Model model, String offset) {
	if (connectionRepository.findPrimaryConnection(Twitter.class) == null) {
	    return "redirect:/connect/twitter";
	}
	int resultLimit = 0;
	int resultOffset = 0;
	int listSize = 0;

	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	listSize = twitter.searchOperations().getDailyTrends().size();
	if (listSize <= int_offset + PSMetadata.TWITTER_LIMIT_RESULT) {
	    resultLimit = listSize;
	} else {
	    resultLimit = int_offset + PSMetadata.TWITTER_LIMIT_RESULT;
	}
	if (listSize <= int_offset) {
	    resultOffset = listSize;
	} else {
	    resultOffset = int_offset;
	}

	model.addAttribute("trends", twitter.searchOperations().getDailyTrends().subList(resultOffset, resultLimit).get(0));
	model.addAttribute("offset", resultOffset);
	model.addAttribute("pageSize", listSize);
	return "twitter/dailyTrends";
    }

}
