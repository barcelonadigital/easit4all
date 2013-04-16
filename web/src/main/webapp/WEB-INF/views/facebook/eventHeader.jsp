<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="littleFriendHeader">
	<span class="subtitle"><strong>${event.name}</strong></span>
</div>

<div id="rightNav">
	<div class="image">
		<img src="http://graph.facebook.com/<c:out value="${event.id}"/>/picture?type=large" />
	</div>
	<!-- <div class="ym-vlist">
		<ul>
			<li>
				<a href="<c:url value="/facebook/events/${event.id}/info"/>">Info</a>
				<a href="<c:url value="/facebook/events/${event.id}/wall"/>">Wall</a>
				<a href="<c:url value="/facebook/events/${event.id}/guests"/>">Guests</a>
			</li>
		</ul>  
	</div>
	
	<tiles:insertTemplate template="rsvpButtons.jsp"/> -->
</div>