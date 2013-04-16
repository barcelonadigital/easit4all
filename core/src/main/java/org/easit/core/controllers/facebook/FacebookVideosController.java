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

import javax.inject.Inject;
import javax.validation.Valid;

import org.easit.dao.model.PSMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class FacebookVideosController {

    private final Facebook facebook;

    @Inject
    public FacebookVideosController(Facebook facebook) {
	this.facebook = facebook;
    }

    @ExceptionHandler(MissingAuthorizationException.class)
    public String handleAuthorizationException(Principal currentUser) {
	return "redirect:/connect/facebook";
    }

    private Resource getUploadResource(final String filename, CommonsMultipartFile content) {
	Resource a = new ByteArrayResource(content.getBytes()) {
	    public String getFilename() throws IllegalStateException {
		return filename;
	    };
	};
	return a;
    }

    /*
     * Watch videos where the authenticated user is tagged in
     */
    @RequestMapping(value = "/facebook/albums/videos", method = RequestMethod.GET)
    public String showVideos(Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.mediaOperations().getVideos().size();

	model.addAttribute(new UploadItem());
	model.addAttribute("videos", facebook.mediaOperations().getVideos(int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT));
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	return "facebook/videos";
    }

    @RequestMapping(value = "/facebook/albums/videos/{videoId}", method = RequestMethod.GET)
    public String showAlbum(@PathVariable("videoId") String videoId, Model model) {
	model.addAttribute("video", facebook.mediaOperations().getVideo(videoId));
	// model.addAttribute("comments",
	// facebook.commentOperations().getComments(videoId));
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	return "facebook/videos/video";
    }

    @RequestMapping(value = "/facebook/albums/videos/upload", method = RequestMethod.GET)
    public String uploadForm(Model model) {
	model.addAttribute(new UploadItem());
	return "facebook/videos/videos/upload";
    }

    /*
     * Uploads a photo to an album created specifically for the application.
     * Requires "publish_stream" permission. If no album exists for the
     * application, it will be created.
     */
    @RequestMapping(value = "/facebook/albums/videos/upload", method = RequestMethod.POST)
    public String uploadVideo(@Valid UploadItem uploadItem, BindingResult result) {
	if (result.hasErrors()) {
	    return null;
	}
	Resource video = getUploadResource(uploadItem.getFileData().getOriginalFilename(), uploadItem.getFileData());
	facebook.mediaOperations().postVideo(video, uploadItem.getTitle(), uploadItem.getCaption());
	return "redirect:/facebook/albums/videos";
    }

}
