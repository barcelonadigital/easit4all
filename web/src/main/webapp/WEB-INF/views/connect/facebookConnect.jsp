<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<tiles:insertTemplate template="connectMenu.jsp"/>
<div id="serviceConnect">
	<form name="fb_connect" id="fb_connect" action="<c:url value="/connect/facebook" />" method="POST">
		<input type="hidden" name="scope" value="publish_stream, user_photos, read_stream, user_videos, manage_friendlists, read_friendlists, user_events, 
												 user_groups, manage_notifications, read_requests, friends_photos, friends_events, email, rsvp_event" />
		<div class="box warning">
			<p>Your easit4all account doesn't have created any connections with Facebook yet.</p>			
			<p>Click the button to connect easit4all with your Twitter account.</p>
			<p>(You'll be redirected to Facebook where you'll be asked to authorize the connection.)</p>
		</div>
		<p>
		  <input type="button" title="Connect to Facebook" class="button_connectFacebook" onclick="document.fb_connect.submit()"/><br/><br/>
		  <p>If you don't have any Facebook account, you can create a <a href="http://www.facebook.com" target="_blank">new</a> one.</p>
		</p>
	</form>
</div>