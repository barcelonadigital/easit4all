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
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.easit.dao.model.PSMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Photo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class FacebookPhotosController {

    private final Facebook facebook;

    @Inject
    public FacebookPhotosController(Facebook facebook) {
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

    @RequestMapping(value = "/facebook/albums", method = RequestMethod.GET)
    public String showAlbums(Model model, String offset) {
	int int_offset = 0;
	int listSize = 0;
	if (offset != null) {
	    int_offset = Integer.valueOf(offset);
	}

	listSize = facebook.mediaOperations().getAlbums().size();

	List<Album> albums = facebook.mediaOperations().getAlbums(int_offset, PSMetadata.FACEBOOK_LIMIT_RESULT);
	model.addAttribute("offset", int_offset);
	model.addAttribute("pageSize", listSize);
	model.addAttribute("albums", albums);
	return "facebook/albums";
    }

    @RequestMapping(value = "/facebook/albums/{albumId}", method = RequestMethod.GET)
    public String showAlbum(@PathVariable("albumId") String albumId, Model model) {
	model.addAttribute("album", facebook.mediaOperations().getAlbum(albumId));
	List<Photo> photos = facebook.mediaOperations().getPhotos(albumId);
	/*
	 * List<List<Comment>> comments = new ArrayList<List<Comment>>();
	 * for(Photo photo:photos) {
	 * comments.add(facebook.feedOperations().getComments(photo.getId())); }
	 * model.addAttribute("comments", comments);
	 */
	model.addAttribute("photos", photos);
	model.addAttribute("userProf", facebook.userOperations().getUserProfile());
	return "facebook/albums/album";
    }

    /*
     * Show form to upload photo
     */
    @RequestMapping(value = "/facebook/albums/upload", method = RequestMethod.GET)
    public String uploadForm(String id, String name, Model model) {
	model.addAttribute(new UploadItem());
	model.addAttribute("album", id);
	model.addAttribute("albumName", name);
	return "facebook/albums/upload";
    }

    /*
     * Uploads a photo to an album created specifically for the application.
     * Requires "publish_stream" permission. If no album exists for the
     * application, it will be created.
     */
    @RequestMapping(value = "/facebook/albums/upload", method = RequestMethod.POST)
    public String uploadPhoto(@Valid UploadItem uploadItem, BindingResult result) {
	if (result.hasErrors()) {
	    return null;
	}
	Resource photo = getUploadResource(uploadItem.getFileData().getOriginalFilename(), uploadItem.getFileData());
	facebook.mediaOperations().postPhoto(photo, uploadItem.getCaption());
	return "redirect:/facebook/albums";
    }

    @RequestMapping(value = "/facebook/albums/upload/{albumId}", method = RequestMethod.POST)
    public String uploadPhotoToAlbum(@PathVariable("albumId") String albumId, @Valid UploadItem uploadItem, BindingResult result) {
	if (result.hasErrors()) {
	    return null;
	}
	Resource photo = getUploadResource(uploadItem.getFileData().getOriginalFilename(), uploadItem.getFileData());
	/*
	 * Uploads a photo to an album created specifically for the application.
	 * Requires "publish_stream" permission. If no album exists for the
	 * application, it will be created.
	 */
	if (albumId == null) {
	    facebook.mediaOperations().postPhoto(photo, uploadItem.getCaption());
	    return "redirect:/facebook/albums";
	}
	/*
	 * Uploads a photo to the espeficied album.
	 */
	else {
	    facebook.mediaOperations().postPhoto(albumId, photo, uploadItem.getCaption());
	    return "redirect:/facebook/albums/{albumId}";
	}
    }

}
