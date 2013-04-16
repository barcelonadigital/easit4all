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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TwitterMessageController {

    private static final Logger logger = LoggerFactory.getLogger(TwitterMessageController.class);

    private final Twitter twitter;

    @Inject
    public TwitterMessageController(Twitter twitter) {
	this.twitter = twitter;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/twitter";
    }

    @RequestMapping(value = "/twitter/messages", method = RequestMethod.GET)
    public String inbox(Model model, String offset) throws Exception {
	int listSize = 0;
	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	listSize = twitter.directMessageOperations().getDirectMessagesReceived().size();
	model.addAttribute("directMessages", twitter.directMessageOperations().getDirectMessagesReceived(int_offset, PSMetadata.TWITTER_LIMIT_RESULT));
	model.addAttribute("dmListType", "Received");
	model.addAttribute("messageForm", new MessageForm());
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "twitter/messages";
    }

    @RequestMapping(value = "/twitter/messages/sent", method = RequestMethod.GET)
    public String sent(Model model, String offset) {
	int listSize = 0;
	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}
	listSize = twitter.directMessageOperations().getDirectMessagesSent().size();
	model.addAttribute("directMessages", twitter.directMessageOperations().getDirectMessagesSent(int_offset, PSMetadata.TWITTER_LIMIT_RESULT));
	model.addAttribute("dmListType", "Sent");
	model.addAttribute("messageForm", new MessageForm());
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "twitter/messages";
    }

    // Send direct messages
    @RequestMapping(value = "/twitter/messages", method = RequestMethod.POST)
    public String sent(MessageForm message) {
	logger.info("message to: " + message.getTo());
	logger.info("text: " + message.getText());
	try {
	    twitter.directMessageOperations().sendDirectMessage(message.getTo(), message.getText());
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    // e.printStackTrace();
	}
	return "redirect:/twitter/messages";
    }

}