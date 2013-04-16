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

import javax.inject.Inject;

import org.easit.core.controllers.facebook.UploadItem;
import org.easit.dao.model.PSMetadata;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwitterSearchController {

    private final Twitter twitter;

    @Inject
    public TwitterSearchController(Twitter twitter) {
	this.twitter = twitter;
    }

    @RequestMapping(value = "/twitter/search", method = RequestMethod.GET)
    public String showTrends(@RequestParam("query") String query, Model model, String offset, String pageSize) {
	int resultLimit = 0;
	int resultOffset = 0;
	int listSize = 0;

	int int_offset = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	if (pageSize == null) {
	    listSize = twitter.searchOperations().search(query).getTweets().size();
	} else {
	    listSize = Integer.valueOf(pageSize);
	}

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
	model.addAttribute("timeline", twitter.searchOperations().search(query).getTweets().subList(resultOffset, resultLimit));
	model.addAttribute("pageSize", listSize);
	model.addAttribute("offset", resultOffset);
	model.addAttribute(new UploadItem());

	return "twitter/timeline";
    }

}
