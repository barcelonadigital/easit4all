<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<c:url var="facebookUrl" value="/facebook" />
<c:url var="searchUrl" value="/facebook/search" />

<div class="ym-wrapper">
	<nav class="service-header">
			<div class="ym-grid">
				<div class="ym-hlist">
					<img alt="Facebook" src="<c:url value="/resources/social/facebook/facebook-word.jpg"/>" height="60px" />
					<form class="ym-searchform" action="${searchUrl}" method="get">
						 <input class="ym-searchfield" type="search" placeholder="Search in Facebook..." name="query" value="<c:out value="${param.query}"/>" style="display:inline;" required/>
						 <input class="ym-searchbutton" type="submit" value="Search" style="display:inline;"/>
					</form>
				</div>
			</div>
	</nav>
	<nav class="service-menu" id="service-menu">
		<div class="ym-hlist">
			<ul>
				<li><a href="<c:out value="${facebookUrl}"/>/feed" class="feed">Feed</a></li>
				<li><a href="<c:out value="${facebookUrl}"/>/wall" class="wall">Wall</a></li>
				<li><a href="<c:out value="${facebookUrl}"/>/albums" class="albums">Albums</a></li>
				<li><a href="<c:out value="${facebookUrl}"/>/friends" class="friends">Friends</a></li>
				<c:if test="${techSkills eq '3'}">
					<li><a href="<c:out value="${facebookUrl}"/>/events" class="events">Events</a></li>
					<li><a href="<c:out value="${facebookUrl}"/>/groups" class="groups">Groups</a></li>
				</c:if>
				<li><a href="<c:out value="${facebookUrl}"/>/notifications" class="notifications">Notifications</a></li>
				<!-- <li><a href="<c:out value="${facebookUrl}"/>/profile" class="profile">Profile</a></li> -->
			</ul>
		</div>
	</nav>
</div>

