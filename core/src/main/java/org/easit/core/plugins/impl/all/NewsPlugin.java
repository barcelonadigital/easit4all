package org.easit.core.plugins.impl.all;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Provider;

import org.easit.core.plugins.Plugin;
import org.easit.dao.LogOperationsDao;
import org.easit.dao.model.EasitAccount;
import org.easit.dao.model.PSMetadata;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Event;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FriendRequest;
import org.springframework.social.facebook.api.Invitation;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.api.Post.PostType;
import org.springframework.social.twitter.api.DirectMessage;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;

public class NewsPlugin implements Plugin {

    private String name;

    private boolean view;

    private boolean loaded;

    private int maxResults;

    public void register(String name, Properties props) {
	this.name = name;
	this.maxResults = Integer.parseInt(props.getProperty("maxResults").toString());
	this.view = Boolean.parseBoolean(props.getProperty("view").toString());
	this.loaded = false;

    }

    public boolean view() {
	return view;
    }

    public String getName() {
	return name;
    }

    public boolean isLoaded() {
	return loaded;
    }

    public void load() {
	this.loaded = true;

    }

    public void unload() {
	this.loaded = false;
    }

    // test!!!
    public void uploadConfiguration(Properties props) {
	register(this.name, props);
    }

    public int getMaxResults() {
	return maxResults;
    }

    // connectionRepositoryProvider, operationsRepository, modelAndView
    public Object execute(EasitAccount res, Object... args) {
	Provider<ConnectionRepository> connectionRepositoryProvider = null;
	LogOperationsDao operationsRepository = null;
	String provider = null;
	Map map = null;
	connectionRepositoryProvider = (Provider<ConnectionRepository>) args[0];
	operationsRepository = (LogOperationsDao) args[1];
	map = (Map) args[2];
	provider = (String) args[3];
	return execute(res, connectionRepositoryProvider, operationsRepository, map, provider);
    }

    @SuppressWarnings("unchecked")
    private Object execute(EasitAccount res, Provider<ConnectionRepository> connectionRepositoryProvider, LogOperationsDao operationsRepository, Map map, String provider) {
	switch (provider.toLowerCase()) {
	case PSMetadata.PROVIDER_FACEBOOK:
	    Connection<Facebook> facebook = getConnectionRepository(connectionRepositoryProvider).findPrimaryConnection(Facebook.class);
	    if (facebook != null && !facebook.hasExpired() && facebook.getApi() != null && facebook.getApi().isAuthorized()) {
		// compare to last connection dates
		Date lastDateFeed = operationsRepository.getLastDate(res.getId(), PSMetadata.PROVIDER_FACEBOOK, "feed");
		Date lastDateNots = operationsRepository.getLastDate(res.getId(), PSMetadata.PROVIDER_FACEBOOK, "notifications");
		Date lastDateEvents = operationsRepository.getLastDate(res.getId(), PSMetadata.PROVIDER_FACEBOOK, "events");

		// feed
		List<Post> feedList = facebook.getApi().feedOperations().getHomeFeed(0, PSMetadata.MAX_RESULTS_NEWS_HOME);
		List<String> videoPosts = new ArrayList<String>();
		List<String> photoPosts = new ArrayList<String>();
		List<String> statusPosts = new ArrayList<String>();
		for (Post p : feedList) {
		    if (lastDateFeed == null || p.getCreatedTime().after(lastDateFeed)) {
			if (p.getType().equals(PostType.VIDEO))
			    videoPosts.add(p.getId());
			else if (p.getType().equals(PostType.PHOTO))
			    photoPosts.add(p.getId());
			else if (p.getType().equals(PostType.STATUS))
			    statusPosts.add(p.getId());
		    }
		}

		// friendship requests
		List<FriendRequest> frList = facebook.getApi().notificationOperations().getFriendRequests();
		List<String> requests = new ArrayList<String>();
		for (FriendRequest fr : frList) {
		    if (lastDateNots == null || fr.getCreatedTime().after(lastDateNots)) {
			requests.add(fr.getFrom().getId());
		    }
		}

		// event updates
		List<Invitation> eventList = facebook.getApi().eventOperations().getInvitations();
		List<String> events = new ArrayList<String>();
		for (Invitation i : eventList) {
		    Event e = facebook.getApi().eventOperations().getEvent(i.getId());
		    if (lastDateEvents == null || e.getUpdatedTime().after(lastDateEvents)) {
			events.add(i.getId());
		    }
		}

		map.put("fb_videos", videoPosts.size());
		map.put("fb_photos", photoPosts.size());
		map.put("fb_status", statusPosts.size());
		map.put("fb_nots", requests.size());
		map.put("fb_events", events.size());
	    }
	    break;

	case PSMetadata.PROVIDER_TWITTER:
	    Connection<Twitter> twitter = getConnectionRepository(connectionRepositoryProvider).findPrimaryConnection(Twitter.class);
	    if (twitter != null && !twitter.hasExpired() && twitter.getApi() != null && twitter.getApi().isAuthorized()) {
		// compare to last connection dates
		Date lastDateTimeline = operationsRepository.getLastDate(res.getId(), PSMetadata.PROVIDER_TWITTER, "timeline");
		Date lastDateMessages = operationsRepository.getLastDate(res.getId(), PSMetadata.PROVIDER_TWITTER, "messages");

		// mentions
		List<Tweet> mentionList = twitter.getApi().timelineOperations().getMentions();
		List<Tweet> mentions = new ArrayList<Tweet>();
		for (Tweet t : mentionList) {
		    if (lastDateTimeline == null || t.getCreatedAt().after(lastDateTimeline)) {
			mentions.add(t);
		    }
		}

		// timeline
		List<Tweet> timelineList = twitter.getApi().timelineOperations().getHomeTimeline();
		List<Tweet> timeline = new ArrayList<Tweet>();
		for (Tweet t : timelineList) {
		    if (lastDateTimeline == null || t.getCreatedAt().after(lastDateTimeline))
			timeline.add(t);
		}

		// private messages
		List<DirectMessage> messageList = twitter.getApi().directMessageOperations().getDirectMessagesReceived();
		List<DirectMessage> messages = new ArrayList<DirectMessage>();
		for (DirectMessage dm : messageList) {
		    if (lastDateMessages == null || dm.getCreatedAt().after(lastDateMessages))
			messages.add(dm);
		}

		// new followers
		List<Long> followersList = twitter.getApi().friendOperations().getIncomingFriendships();

		map.put("tw_mentions", mentions.size());
		map.put("tw_timeline", timeline.size());
		map.put("tw_messages", messages.size());
		map.put("tw_requests", followersList.size());

	    }
	    break;
	default:
	    map.put("error", null);
	}
	return map;
    }

    private ConnectionRepository getConnectionRepository(Provider<ConnectionRepository> connectionRepositoryProvider) {
	return connectionRepositoryProvider.get();
    }

    public Properties getProperties() {
	// TODO Auto-generated method stub
	return null;
    }

}
